package airbnb.service;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.MediaRepository;
import airbnb.model.repositories.PropertyRepository;
import airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Primary
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private MediaService mediaService;
    private UserRepository userRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               MediaRepository mediaRepository,
                               MediaService mediaService,
                               UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.mediaService = mediaService;
        this.userRepository = userRepository;
    }

    @Override
    public Property getById(Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            throw new BadRequestException("Property not found!");
        }
        return property.get();
    }

    @Override
    public Set<Property> getAll() {
        Iterable<Property> properties =  propertyRepository.findAll();
        if (!properties.iterator().hasNext()) {
            throw new BadRequestException("No properties found!");
        }
        return StreamSupport.stream(properties.spliterator(), false).collect(Collectors.toSet());
    }



    @Override
    public Property add(AddRequestPropertyDTO addRequestPropertyDTO){
        //TODO validate
        Optional<User> host = userRepository.findById( addRequestPropertyDTO.getHost_id().intValue());
        Property property = new Property(addRequestPropertyDTO);
        property.setHost(host.get());
        propertyRepository.save(property);

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

    @Transactional
    @Override
    public void deleteById(Long id)  {
        //TODO validate
        try {
            mediaService.deleteByPropertyId(id);
            propertyRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new BadRequestException("Problem deleting property!");
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        try {
            Iterable<Property> properties = propertyRepository.findAll();
            for (Property property: properties) {
                mediaService.deleteByPropertyId(property.getId());
            }
            propertyRepository.deleteAll();
            
        }
        catch (Exception e) {
            throw new BadRequestException("Problem deleting properties!");
        }

    }


}
