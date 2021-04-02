package airbnb.controller;

import airbnb.exceptions.AuthenticationException;
import airbnb.model.pojo.Media;
import airbnb.services.interfaces.MediaService;
import org.apache.tomcat.jni.Error;
import org.hibernate.internal.util.xml.ErrorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.ErrorManager;


@RestController
public class MediaController extends AbstractController {

    private MediaService mediaService;
    private SessionManager sessionManager;

    @Autowired
    public MediaController(MediaService mediaService,
                           SessionManager sessionManager) {
        this.mediaService = mediaService;
        this.sessionManager = sessionManager;
    }


    //TODO MEDIA DTO - VALIDATE

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

    @PutMapping("users/{userId}/properties/{propertyId}/media")
    public Media upload(@PathVariable Long userId,
                        @PathVariable Long propertyId,
                        HttpSession session,
                        @RequestPart MultipartFile file) {
        sessionManager.validate(userId, session);
//        httpResponse.headers().
        return mediaService.upload(propertyId, file);
    }

    @DeleteMapping("users/{userId}/properties/{propertyId}/media/{mediaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOneByMediaId(@PathVariable Long userId,
                                   @PathVariable Long propertyId,
                                   @PathVariable Long mediaId,
                                   HttpSession session) {
        //todo validate session
        sessionManager.validate(userId, session);
        mediaService.deleteOneByMediaId(mediaId);
    }

    @DeleteMapping("users/{userId}/properties/{propertyId}/media")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAllByPropertyId(@PathVariable Long userId,
                                      @PathVariable Long propertyId,
                                      HttpSession session) {
        sessionManager.validate(userId, session);
        mediaService.deleteAllByPropertyId(propertyId);
    }


}
