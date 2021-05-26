package airbnb.services.interfaces;

import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;


public interface PropertyService {

    List<Property> getAll();

    Property getByPropertyId(Long id);

    List<Property> findAllByUserId(Long userId);

    Property add(Long userId, AddRequestPropertyDTO addRequestPropertyDTO);

    Property edit(Long propertyId, EditRequestPropertyDTO editRequestPropertyDTO);

    List<Property> filter(Long typeId, Long cityId, Long countryId, String name, String description, BigDecimal minPrice, BigDecimal maxPrice);

    List<Property> nearby(Float proximity, HttpServletRequest request);

    void deleteById(Long userId, Long propertyId);

}