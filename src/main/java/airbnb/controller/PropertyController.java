package airbnb.controller;

import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
public class PropertyController extends AbstractController {

    private PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService PropertyService) {
        this.propertyService = PropertyService;
    }


    //TODO RESPONSE STATUSES

    @PutMapping("/properties")
    public Property add(@RequestBody AddRequestPropertyDTO addRequestPropertyDTO)  {
        return propertyService.add(addRequestPropertyDTO);
    }

    @GetMapping("/properties")
    public Set<Property> getAll() {
        return propertyService.getAll();
    }

    @GetMapping("/properties/{id}")
    public Property getById(@PathVariable Long id) {
        return propertyService.getById(id);
    }

    @PostMapping("/properties/{id}")
    public Property edit(@PathVariable Long id, @RequestBody EditRequestPropertyDTO editRequestPropertyDTO) {
        return propertyService.edit(id, editRequestPropertyDTO);
    }

    //TODO proper method
    @PostMapping("/properties")
    public Set<Property> filter(@RequestBody FilterRequestPropertyDTO filterRequestPropertyDTO)  {
        return propertyService.filter(filterRequestPropertyDTO);
    }

    @DeleteMapping("/properties/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        propertyService.deleteById(id);

    }

    @DeleteMapping("/properties")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAll(){
        propertyService.deleteAll();
    }

}
