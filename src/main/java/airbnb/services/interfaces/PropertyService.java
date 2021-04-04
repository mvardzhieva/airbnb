package airbnb.services.interfaces;

import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface PropertyService {

    Property add(AddRequestPropertyDTO addRequestPropertyDTO);

    Set<Property> getAll();

    Property getByPropertyId(Long id);

    Property edit(Long propertyId, EditRequestPropertyDTO editRequestPropertyDTO);

    Set<Property> filter(FilterRequestPropertyDTO filterRequestPropertyDTO);

    void deleteById(Long id);

    Set<Property> nearby(Float proximity, HttpServletRequest request);

    Set<Property> findAllByUserId(Long userId);

}
