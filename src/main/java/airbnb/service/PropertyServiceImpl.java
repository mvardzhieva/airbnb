package airbnb.service;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.property.DeleteRequestPropertyDTO;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.model.repositories.PropertyRepository;
import com.sun.source.tree.TryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Primary
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }


    @Override
    public Property add(AddRequestPropertyDTO addRequestPropertyDTO){
        //TODO validate
        Property property = propertyRepository.save(new Property(addRequestPropertyDTO));
        Optional<Property> x = propertyRepository.findById(property.getId());
        if (x.isPresent()) {
            return x.get();
        }
        throw new NotFoundException("");
    }

    @Override
    public Property edit(Long id, EditRequestPropertyDTO editRequestPropertyDTO) {
        //TODO validate
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isEmpty()) {
            throw new BadRequestException("Problem editing property!");
        }

        Property property = optionalProperty.get();
        property.setName(editRequestPropertyDTO.getName());
        property.setDescription(editRequestPropertyDTO.getDescription());
        property.setPrice(editRequestPropertyDTO.getPrice());
        propertyRepository.save(property);

        Optional<Property> optionalProperty1 = propertyRepository.findById(property.getId());
        if (optionalProperty1.isEmpty()) {
            throw new BadRequestException("Problem editing property!");
        }
        return optionalProperty1.get();

    }

    @Override
    public Set<Property> filter(FilterRequestPropertyDTO filterRequestPropertyDTO) throws NotFoundException {
        //TODO
        return null;
    }

    @Override
    public void delete(Long id)  {
        //TODO validate
        try {
            propertyRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new BadRequestException("Problem deleting property");
        }


    }
}
