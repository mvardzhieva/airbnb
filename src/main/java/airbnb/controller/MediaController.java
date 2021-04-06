package airbnb.controller;

import airbnb.model.pojo.Media;
import airbnb.services.interfaces.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.util.List;


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

    @GetMapping(value = "users/properties/media/{id}")
    public ResponseEntity<byte[]> downloadByMediaId(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.add("Content-Type", mediaService.findById(id).getMimeType());
        byte[] media = mediaService.download(id);

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

    @GetMapping("users/properties/{id}/media")
    public List<Media> getAllByPropertyId(@PathVariable Long id) {
        return mediaService.getAllByPropertyId(id);
    }

    @GetMapping(value = "users/properties/media")
    public List<Media> getAll() {
        return mediaService.getAll();
    }

    @PostMapping("users/{userId}/properties/{propertyId}/media")
    @ResponseStatus(HttpStatus.CREATED)
    public Media upload(@PathVariable Long userId,
                        @PathVariable Long propertyId,
                        HttpSession session,
                        @RequestPart MultipartFile file) {

        sessionManager.validate(userId, session);

        return mediaService.upload(propertyId, file);
    }


    @DeleteMapping("users/{userId}/properties/{propertyId}/media")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAllByPropertyId(@PathVariable Long userId,
                                      @PathVariable Long propertyId,
                                      HttpSession session) {

        sessionManager.validate(userId, session);
        mediaService.deleteAllByPropertyId(userId, propertyId);
    }

    @DeleteMapping("users/{userId}/properties/{propertyId}/media/{mediaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOneByMediaId(@PathVariable Long userId,
                                   @PathVariable Long propertyId,
                                   @PathVariable Long mediaId,
                                   HttpSession session) {

        sessionManager.validate(userId, session);
        mediaService.deleteOneByMediaId(userId, propertyId, mediaId);
    }
}

