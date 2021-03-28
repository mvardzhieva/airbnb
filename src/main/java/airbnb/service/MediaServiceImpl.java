package airbnb.service;

import airbnb.exceptions.BadRequestException;
import airbnb.model.pojo.Media;
import airbnb.model.repositories.MediaRepository;
import airbnb.model.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class MediaServiceImpl implements MediaService{


    @Value("${file.path}")
    private String filePath;
    private MediaRepository mediaRepository;
    private PropertyRepository propertyRepository;

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
            media.setProperty_id(id);
            media.setMime_type(file.getContentType());
            mediaRepository.save(media);
            os.close();
            Optional<Media> mediaOptional = mediaRepository.findById(media.getId());
            return mediaOptional.get();
        } catch (Exception e) {
            throw new BadRequestException("Problem uploading file!");
        }
    }

    @Override
    public void filter() {

    }

    @Override
    public void delete(Long id) {
        //TODO validate

        try {
            mediaRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new BadRequestException("Can't delete media!");
        }
    }
}
