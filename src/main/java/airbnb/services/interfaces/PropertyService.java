package airbnb.services.interfaces;

import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;

import java.util.List;
import java.util.Set;

public interface PropertyService {

    Property add(AddRequestPropertyDTO addRequestPropertyDTO);

    Set<Property> getAll();

    Property getById(Long id);

    Property edit(EditRequestPropertyDTO editRequestPropertyDTO);

    Set<Property> filter(FilterRequestPropertyDTO filterRequestPropertyDTO);

    void deleteById(Long id);

    List<Property> nearby(FilterRequestPropertyDTO filterRequestPropertyDTO);

}
