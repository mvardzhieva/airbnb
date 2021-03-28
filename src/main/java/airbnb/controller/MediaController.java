package airbnb.controller;

import airbnb.model.pojo.Media;
import airbnb.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class MediaController extends AbstractController {

    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PutMapping("/properties/{id}/media")
    public Media add(@PathVariable Long id, @RequestPart MultipartFile file) {
        return mediaService.add(id, file);
    }

    @PostMapping("/properties/media/filter")
    public void filter() {
        mediaService.filter();
    }

    @DeleteMapping("/properties/media/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        mediaService.delete(id);
    }
}
