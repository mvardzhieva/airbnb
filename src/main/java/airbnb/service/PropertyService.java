package airbnb.service;

import airbnb.model.dto.property.DeleteRequestPropertyDTO;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;

public interface PropertyService {

    Property add(AddRequestPropertyDTO addRequestPropertyDTO);

    void edit(EditRequestPropertyDTO editRequestPropertyDTO);

    void filter(FilterRequestPropertyDTO filterRequestPropertyDTO);

    void delete(DeleteRequestPropertyDTO deleteRequestPropertyDTO);
}
