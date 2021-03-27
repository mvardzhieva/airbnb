package airbnb.controller;

import airbnb.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController {

    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PutMapping("/Airbnb/media/add")
    public void add() {
        mediaService.add();
    }

    @PostMapping("/Airbnb/media/filter")
    public void filter() {
        mediaService.filter();
    }

    @DeleteMapping("/Airbnb/media/delete")
    public void delete() {
        mediaService.delete();
    }
}
