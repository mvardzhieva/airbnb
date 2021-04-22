package airbnb.services;

import airbnb.exceptions.AuthenticationException;
import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.Media;
import airbnb.model.pojo.Property;
import airbnb.model.repositories.MediaRepository;
import airbnb.services.interfaces.MediaService;
import airbnb.services.interfaces.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Primary
public class MediaServiceImpl implements MediaService {

    @Value("${file.path}")
    private String filePath;
    private MediaRepository mediaRepository;
    private PropertyService propertyService;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Autowired
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public Media upload(Long propertyId, MultipartFile file) {
        validateMultipartFile(file);

        String filename = UUID.randomUUID().toString();
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String type = file.getOriginalFilename().split("\\.")[1];
        File f = new File(dir.getAbsolutePath() + File.separator + filename + "." + type);

        try {
            f.createNewFile();
            file.transferTo(Paths.get(f.getAbsolutePath()));

            Media media = new Media();
            media.setUrl(f.getAbsolutePath());
            media.setMimeType(file.getContentType());
            media.setProperty(propertyService.getByPropertyId(propertyId));
            mediaRepository.save(media);

            return getMedia(media.getId());
        } catch (Exception e) {
            f.delete();
            throw new BadRequestException("Problem uploading file!");
        }
    }

    private void validateMultipartFile(MultipartFile file) {
        if (file == null) {
            throw new BadRequestException("Problem uploading the file!");
        }

        String mimeType = file.getContentType();

        if (mimeType == null ||
                (!file.getContentType().contains("image/") &&
                        !file.getContentType().contains("video/"))) {
            throw new BadRequestException("Media not supported!");
        }
    }

    @Override
    public byte[] download(Long id) {
        Media media = getMedia(id);

        try {
            return Files.readAllBytes(Path.of(media.getUrl()));
        } catch (Exception e) {
            throw new NotFoundException("Can't download media!");
        }
    }

    @Override
    public Media findById(Long id) {
        return getMedia(id);
    }

    @Override
    public List<Media> getAll() {
        return mediaRepository.findAll();
    }

    @Override
    public List<Media> getAllByPropertyId(Long id) {
        return mediaRepository.findAllByPropertyId(id);
    }

    @Override
    public void deleteOneByMediaId(Long userId, Long mediaId) {
        Media media = getMedia(mediaId);

        if (media.getProperty().getHost().getId() != userId) {
            throw new AuthenticationException("Action not allowed!");
        }

        deleteMedia(media);
    }

    @Override
    public void deleteAllByPropertyId(Long userId, Long propertyId) {
        Property property = propertyService.getByPropertyId(propertyId);

        if (property.getHost().getId() != userId) {
            throw new AuthenticationException("Action not allowed!");
        }

        for (Media media : property.getMedia()) {
            deleteMedia(media);
        }
    }

    private void deleteMedia(Media media) {
        try {
            Files.delete(Paths.get(media.getUrl()));
            mediaRepository.deleteById(media.getId());
        } catch (Exception e) {
            throw new BadRequestException("Can't delete media with id " + media.getId());
        }
    }

    private Media getMedia(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Media not found!"));
    }
}