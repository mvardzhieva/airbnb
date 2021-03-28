package airbnb.service;

import airbnb.model.dto.property.DeleteRequestPropertyDTO;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;

import java.util.Set;

public interface PropertyService {

    Property add(AddRequestPropertyDTO addRequestPropertyDTO);

    Property edit(Long id, EditRequestPropertyDTO editRequestPropertyDTO);

    Set<Property> filter(FilterRequestPropertyDTO filterRequestPropertyDTO);

    void deleteById(Long id);

    Property getById(Long id);

    Set<Property> getAll();

    void deleteAll();
}
