package airbnb.controller;

import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.property.DeleteRequestPropertyDTO;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController extends AbstractController {

    private PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService PropertyService) {
        this.propertyService = PropertyService;
    }

    @PutMapping("/Airbnb/properties/add")
    public Property add(@RequestBody AddRequestPropertyDTO addRequestPropertyDTO) throws NotFoundException {
        //TODO validation
        return propertyService.add(addRequestPropertyDTO);
    }

    @PostMapping("/Airbnb/properties/edit")
    public String edit(EditRequestPropertyDTO editRequestPropertyDTO) {
        //TODO validation
        propertyService.edit(editRequestPropertyDTO);
        return null;
    }

    @GetMapping("/Airbnb/properties/filter")
    public String filter(FilterRequestPropertyDTO filterRequestPropertyDTO) {
        //TODO
        propertyService.filter(filterRequestPropertyDTO);
        return "Hello!";
    }

    @DeleteMapping("Airbnb/properties/delete")
    public Boolean delete(DeleteRequestPropertyDTO deleteRequestPropertyDTO){
        //TODO
        propertyService.delete(deleteRequestPropertyDTO);
        return true;
    }




}
