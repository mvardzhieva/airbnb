package airbnb.service;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.pojo.Media;
import airbnb.model.repositories.MediaRepository;
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

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media add(Long id, MultipartFile file)  {
        //TODO validate

        try {
            String filename = UUID.randomUUID().toString();
            File f = new File(filePath + File.separator + filename + ".png");
            OutputStream os = new FileOutputStream(f);
            os.write(file.getBytes());
            Media media = new Media();
            media.setUrl(f.getAbsolutePath());
            media.setPropertyId(id);
            media.setMimeType(file.getContentType());
            mediaRepository.save(media);
            os.close();
            Optional<Media> mediaOptional = mediaRepository.findById(media.getId());
            return mediaOptional.get();
        } catch (Exception e) {
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
