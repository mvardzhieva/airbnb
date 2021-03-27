package airbnb.service;

import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.property.DeleteRequestPropertyDTO;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.model.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property add(AddRequestPropertyDTO addRequestPropertyDTO) throws NotFoundException {
        //TODO validate
        Property p = propertyRepository.save(new Property(addRequestPropertyDTO));
        long id = p.getId();
        Optional<Property> x = propertyRepository.findById(id);
        if (x.isPresent()) {
            return x.get();
        }
        throw new NotFoundException("");
    }

    @Override
    public void edit(EditRequestPropertyDTO editRequestPropertyDTO)  {
        //TODO
    }

    @Override
    public void filter(FilterRequestPropertyDTO filterRequestPropertyDTO) throws NotFoundException {
        //TODO
    }

    @Override
    public void delete(DeleteRequestPropertyDTO deleteRequestPropertyDTO) {
        //TODO
    }
}
