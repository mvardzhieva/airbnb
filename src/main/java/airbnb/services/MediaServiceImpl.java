package airbnb.services;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.Media;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class MediaServiceImpl implements MediaService {


    @Value("${file.path}")
    private String filePath;
    private MediaRepository mediaRepository;
    private PropertyService propertyService;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository,
                            PropertyService propertyService) {
        this.mediaRepository = mediaRepository;
        this.propertyService = propertyService;
    }

    //TODO  REFACTOR
    @Override
    public Media upload(Long id, MultipartFile file)  {

        String filename = UUID.randomUUID().toString();
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File f = new File(dir.getAbsolutePath() + File.separator + filename + file.getContentType());

        try  {
            f.createNewFile();
            Media media = new Media();
            media.setUrl(f.getAbsolutePath());
            media.setMimeType(file.getContentType());
            media.setProperty(propertyService.getById(id));
            mediaRepository.save(media);
            Optional<Media> mediaOptional = mediaRepository.findById(media.getId());
            if (mediaOptional.isEmpty()) {
                throw new FileNotFoundException();
            }
            file.transferTo(Paths.get(f.getAbsolutePath()));
            return mediaOptional.get();

        } catch (Exception e) {
            f.delete();
            throw new BadRequestException("Problem uploading file!");
        }

    }

    @Override
    public byte[] download(Long id) {
       try {
           Optional<Media> optionalMedia = mediaRepository.findById(id);
           return Files.readAllBytes(Path.of(optionalMedia.get().getUrl()));
       } catch (Exception e) {
           throw new NotFoundException("Media not found!");
       }
    }

    @Override
    public List<Media> getAll() {
        return mediaRepository.findAll();
    }

    @Override
    public List<Media> getAllByPropertyId(Long id) {
       return mediaRepository.getAllByPropertyId(id);

    }

    @Override
    public void deleteOneByMediaId(Long mediaId) {
        Media media = mediaRepository.getOne(mediaId);
        if (media == null) {
            throw new NotFoundException("Media not found!");
        }

        deleteFromFileSystem(media);
    }

    @Override
    public void deleteAllByPropertyId(Long id) {
        var x = mediaRepository.getAllByPropertyId(id);
        if (!x.isEmpty()) {
            for (Media media : x) {
                deleteFromFileSystem(media);
            }
        }
    }


    private void deleteFromFileSystem(Media media) {
        try {
            Files.delete(Paths.get(media.getUrl()));
            mediaRepository.deleteById(media.getId());
        }
        catch (Exception e) {
            throw new BadRequestException("Can't delete media!");
        }
    }

}
