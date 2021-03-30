package airbnb.controller;

import airbnb.model.pojo.Media;
import airbnb.services.interfaces.MediaService;
import org.apache.tomcat.jni.Error;
import org.hibernate.internal.util.xml.ErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.logging.ErrorManager;


@RestController
public class MediaController extends AbstractController {

    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }


    //TODO MEDIA DTO

    @GetMapping(value = "users/properties/media/{id}", produces = "image/*")
    public byte[] download(@PathVariable Long id) {
        return mediaService.download(id);
    }

    @GetMapping("users/properties/{id}/media")
    public List<Media> getAllByPropertyId(@PathVariable Long id) {
        return mediaService.getAllByPropertyId(id);
    }

    @GetMapping(value = "/properties/media")
    public List<Media> getAll() {
        return mediaService.getAll();
    }

    @PutMapping("users/properties/{id}/media")
    public Media upload(@PathVariable Long id, @RequestPart MultipartFile file) {
        return mediaService.upload(id, file);
    }

    @DeleteMapping("users/properties/{propertyId}/media/{mediaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOneByMediaId(@PathVariable Long propertyId, @PathVariable Long mediaId) {
        //todo validate session
        mediaService.deleteOneByMediaId(mediaId);
    }

    @DeleteMapping("users/properties/{propertyId}/media")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAllByPropertyId(@PathVariable Long propertyId) {
        //todo validate session
        mediaService.deleteAllByPropertyId(propertyId);
    }

}
