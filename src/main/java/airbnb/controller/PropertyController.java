package airbnb.controller;

import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.services.interfaces.MediaService;
import airbnb.services.interfaces.PropertyService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class PropertyController extends AbstractController {

    private PropertyService propertyService;
    private SessionManager sessionManager;
    private MediaService mediaService;

    @Autowired
    public PropertyController(PropertyService PropertyService,
                              SessionManager sessionManager,
                              MediaService mediaService) {
        this.propertyService = PropertyService;
        this.sessionManager = sessionManager;
        this.mediaService = mediaService;
    }

    @GetMapping("users/properties/all")
    public Set<Property> getAll() {
        return propertyService.getAll();
    }

    @GetMapping("users/properties/{id}")
    public Property getByPropertyId(@PathVariable Long id) {
        return propertyService.getByPropertyId(id);
    }

    @GetMapping("users/{id}/properties/")
    public Set<Property> getAllByUserId(@PathVariable Long id, HttpSession session) {

        sessionManager.validate(id, session);
        return propertyService.findAllByUserId(id);
    }

    @SneakyThrows
    @GetMapping("users/properties/nearby")
    public Set<Property> nearby(@RequestParam Float proximity, HttpServletRequest request) {
        return propertyService.nearby(proximity, request);
    }

    @PostMapping("users/properties/filter")
    public Set<Property> filter(@RequestBody @Valid FilterRequestPropertyDTO filterRequestPropertyDTO) {
        return propertyService.filter(filterRequestPropertyDTO);
    }

    @PostMapping("users/{userId}/properties")
    @ResponseStatus(HttpStatus.CREATED)
    public Property add(@RequestBody @Valid AddRequestPropertyDTO addRequestPropertyDTO,
                        HttpSession session, @PathVariable Long userId) {

        sessionManager.validate(userId, session);
        return propertyService.add(userId, addRequestPropertyDTO);
    }

    @PutMapping("users/{userId}/properties/{propertyId}")
    public Property edit(@PathVariable Long userId,
                         @PathVariable Long propertyId,
                         @RequestBody @Valid EditRequestPropertyDTO editRequestPropertyDTO,
                         HttpSession session) {

        sessionManager.validate(userId, session);
        return propertyService.edit(propertyId, editRequestPropertyDTO);
    }

    @Transactional
    @DeleteMapping("users/{userId}/properties/{propertyId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteByPropertyId(@PathVariable Long userId,
                                   @PathVariable Long propertyId,
                                   HttpSession session) {

        sessionManager.validate(userId, session);
        mediaService.deleteAllByPropertyId(propertyId);
        propertyService.deleteById(propertyId);
    }
}
