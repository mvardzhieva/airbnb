package airbnb.controller;

import airbnb.model.pojo.Media;
import airbnb.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
public class MediaController extends AbstractController {

    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }


    //TODO MEDIA DTO

    @PutMapping("/properties/{id}/media")
    public Media upload(@PathVariable Long id, @RequestPart MultipartFile file) {
        return mediaService.upload(id, file);
    }

    @GetMapping(value = "/properties/media/{id}", produces = "image/*")
    public byte[] download(@PathVariable Long id) {
        return mediaService.download(id);
    }

    @GetMapping("/properties/{id}/media")
    public List<Media> getAllByPropertyId(@PathVariable Long id) {
        return mediaService.getAllByPropertyId(id);
    }

    @GetMapping(value = "/properties/media")
    public List<Media> getAll() {
        return mediaService.getAll();
    }

    @DeleteMapping("/properties/{propertyId}/media/{mediaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOneByMediaId(@PathVariable Long propertyId, @PathVariable Long mediaId) {
        //todo validate session
        mediaService.deleteOneByMediaId(mediaId);
    }

    @DeleteMapping("/properties/{propertyId}/media")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAllByPropertyId(@PathVariable Long propertyId) {
        //todo validate session
        mediaService.deleteAllByPropertyId(propertyId);
    }

}
