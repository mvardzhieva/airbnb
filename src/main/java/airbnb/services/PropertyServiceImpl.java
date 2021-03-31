package airbnb.services;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.PropertyRepository;
import airbnb.model.repositories.UserRepository;
import airbnb.services.interfaces.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import javax.management.QueryExp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Primary
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private UserService userService;
    private UserRepository userRepository;


    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               UserService userService,
                               UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //TODO VALIDATE DATA
    @Override
    public Property add(AddRequestPropertyDTO addRequestPropertyDTO) {
        User user = userRepository.findById(addRequestPropertyDTO.getHostId().intValue()).get();
        Property property = new Property(addRequestPropertyDTO);
        property.setHost(user);
        return propertyRepository.save(property);
    }

    @Override
    public Property getById(Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            throw new BadRequestException("Property not found!");
        }

        return property.get();
    }

    //TODO paging and filter
    @Override
    public Set<Property> getAll() {
        Iterable<Property> properties = propertyRepository.findAll();
        if (!properties.iterator().hasNext()) {
            throw new BadRequestException("No properties found!");
        }

        return StreamSupport.stream(properties.spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public Property edit(EditRequestPropertyDTO editRequestPropertyDTO) {
        return null;
    }

    //TODO paging and filter
    @Override
    public Set<Property> filter(FilterRequestPropertyDTO filterRequestPropertyDTO) throws NotFoundException {

//        return propertyRepository.filterBy(filterRequestPropertyDTO.getTypeId(),
//                filterRequestPropertyDTO.getMinPrice(),
//                filterRequestPropertyDTO.getMaxPrice(),
//                filterRequestPropertyDTO.getCityId(),
//                filterRequestPropertyDTO.getCountryId()
//                filterRequestPropertyDTO.getDescription()
//        ).stream().collect(Collectors.toSet());
        return null;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            propertyRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestException("Problem deleting property!");
        }
    }

    @Override
    public List<Property> nearBy(FilterRequestPropertyDTO filterRequestPropertyDTO) {
        return propertyRepository.finNearBy(filterRequestPropertyDTO.getProximity()).stream().collect(Collectors.toList());
    }


}
