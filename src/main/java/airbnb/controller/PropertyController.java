package airbnb.controller;

import airbnb.exceptions.AuthenticationException;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.services.interfaces.MediaService;
import airbnb.services.interfaces.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

@RestController
public class PropertyController extends AbstractController {

    private PropertyService propertyService;
    private SessionManager sessionManager;
    private MediaService mediaService;
    private final String EXCEPTION_MSG = "Action Unauthorized!";

    @Autowired
    public PropertyController(PropertyService PropertyService,
                              SessionManager sessionManager,
                              MediaService mediaService) {
        this.propertyService = PropertyService;
        this.sessionManager = sessionManager;
        this.mediaService = mediaService;
    }


    //TODO RESPONSE STATUSES AND URLS

    @GetMapping("users/properties")
    public Set<Property> getAll() {
        return propertyService.getAll();
    }

    @GetMapping("users/properties/{id}")
    public Property getAllByPropertyId(@PathVariable Long id) {
        return propertyService.getById(id);
    }

    @PutMapping("users/{id}/properties")
    public Property add(@RequestBody AddRequestPropertyDTO addRequestPropertyDTO,
                        HttpSession session, @PathVariable Long id)  {
        addRequestPropertyDTO.setHostId(id);
        if (sessionManager.getLoggedUser(session).getId() == id) {
            return propertyService.add(addRequestPropertyDTO);
        }

        throw new AuthenticationException(EXCEPTION_MSG);
    }

    @PostMapping("users/{userId}/properties/{propertyId}")
    public Property edit(@PathVariable Long userId,
                         @PathVariable Long propertyId,
                         @RequestBody EditRequestPropertyDTO editRequestPropertyDTO,
                         HttpSession session) {


        if (sessionManager.getLoggedUser(session).getId() == userId) {
            return propertyService.edit(editRequestPropertyDTO);
        }
        throw new AuthenticationException(EXCEPTION_MSG);
    }

    //TODO proper method
    @PostMapping("users/properties")
    public Set<Property> filter(@RequestBody FilterRequestPropertyDTO filterRequestPropertyDTO)  {
        return propertyService.filter(filterRequestPropertyDTO);
    }

    //TODO proper method
    @PostMapping("users/properties/s")
    public List<Property> nearBy(@RequestBody FilterRequestPropertyDTO filterRequestPropertyDTO)  {
        return propertyService.nearBy(filterRequestPropertyDTO);
    }



    @Transactional
    @DeleteMapping("users/{userId}/properties/{propertyId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteByPropertyId(@PathVariable Long userId,
                                   @PathVariable Long propertyId,
                                   HttpSession session){
        if (sessionManager.getLoggedUser(session).getId() == userId) {
            mediaService.deleteAllByPropertyId(propertyId);
            propertyService.deleteById(propertyId);
        }
        else {
            throw new AuthenticationException(EXCEPTION_MSG);
        }
    }

}
