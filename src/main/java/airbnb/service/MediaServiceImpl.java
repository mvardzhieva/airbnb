package airbnb.service;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.Media;
import airbnb.model.repositories.MediaRepository;
import airbnb.model.repositories.PropertyRepository;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Primary
public class MediaServiceImpl implements MediaService{


    @Value("${file.path}")
    private String filePath;
    private MediaRepository mediaRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, PropertyRepository propertyRepository) {
        this.mediaRepository = mediaRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Media add(Long id, MultipartFile file)  {
        //TODO validate

        String filename = UUID.randomUUID().toString();
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File f = new File(filePath + File.separator + filename + ".png");

        try  {
            f.createNewFile();
            Media media = new Media();
            media.setUrl(f.getAbsolutePath());
            media.setMimeType(file.getContentType());
            media.setProperty(propertyRepository.findById(id).get());
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
    public Set<Media> getAllByPropertyId(Long id) {
        Set<Media> media = mediaRepository.getAllByPropertyId(id);
        if (media.isEmpty()) {
            throw new NotFoundException("No media for this property!");
        }

        return media;
    }

    @Override
    public void filter() {

    }

    @Override
    public void deleteById(Long id) {
        //TODO validate
        
        Optional<Media> optionalMedia = mediaRepository.findById(id);
        if (optionalMedia.isEmpty()) {
            throw new BadRequestException("Can't find media!");
        }

        try {
            Media media = optionalMedia.get();
            Files.delete(Paths.get(media.getUrl()));
            mediaRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new BadRequestException("Can't delete media!");
        }
    }

    @Override
    public void deleteByPropertyId(Long id) {
        //TODO
        Set<Media> media = mediaRepository.getAllByPropertyId(id);
        for (Media m : media) {
            deleteById(m.getId());
        }
    }




}
