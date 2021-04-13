package airbnb.services;

import airbnb.exceptions.BadRequestException;
import airbnb.exceptions.NotFoundException;
import airbnb.model.dao.PropertyDAO;
import airbnb.model.dto.property.EditRequestPropertyDTO;
import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.dto.property.AddRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.User;
import airbnb.model.repositories.PropertyRepository;
import airbnb.services.interfaces.LocationService;
import airbnb.services.interfaces.MediaService;
import airbnb.services.interfaces.PropertyService;
import airbnb.services.interfaces.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Primary
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private PropertyDAO propertyDAO;
    private UserService userService;
    private LocationService locationService;
    private MediaService mediaService;


    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               PropertyDAO propertyDAO,
                               LocationService locationService) {
        this.propertyRepository = propertyRepository;
        this.propertyDAO = propertyDAO;
        this.locationService = locationService;
    }

    @Autowired
    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Property add(Long userId, AddRequestPropertyDTO addRequestPropertyDTO) {
        User user = userService.getUserById(userId.intValue());
        Property property = new Property(addRequestPropertyDTO);
        property.setHost(user);
        return propertyRepository.save(property);
    }

    @Override
    public Set<Property> findAllByUserId(Long userId) {
        return propertyRepository.findAllByHostId(userId.intValue());
    }

    @Override
    public Property getByPropertyId(Long propertyId) {
        return findProperty(propertyId);
    }

    @Override
    public Set<Property> getAll() {
        Iterable<Property> properties = propertyRepository.findAll();

        return StreamSupport.stream(properties.spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public Property edit(Long propertyId, EditRequestPropertyDTO editRequestPropertyDTO) {
        Property property = findProperty(propertyId);

        if (editRequestPropertyDTO.getPrice() != null &&
                editRequestPropertyDTO.getPrice().doubleValue() < 0) {
            property.setPrice(editRequestPropertyDTO.getPrice());
        } 
        else {
            throw new BadRequestException("Price can't be negative!");
        }

        if (editRequestPropertyDTO.getName() != null &&
                !editRequestPropertyDTO.getName().isEmpty()) {
            property.setName(editRequestPropertyDTO.getName());
        }

        if (editRequestPropertyDTO.getDescription() != null &&
                !editRequestPropertyDTO.getDescription().isEmpty()) {
            property.setDescription(editRequestPropertyDTO.getDescription());
        }

        return propertyRepository.save(property);
    }

    @Override
    public List<Property> filter(FilterRequestPropertyDTO filterRequestPropertyDTO) throws NotFoundException {
        return propertyDAO.filter(filterRequestPropertyDTO);
    }

    @Override
    @SneakyThrows
    public List<Property> nearby(Float proximity, HttpServletRequest request) {
        locationService.setLocation(request.getRemoteAddr());

        return propertyRepository
                .findNearby(locationService.getLatitude(),
                        locationService.getLongitude(),
                        proximity)
                .stream().collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long userId, Long propertyId) {
        try {
            mediaService.deleteAllByPropertyId(userId, propertyId);
            propertyRepository.deleteById(propertyId);
        } catch (Exception e) {
            throw new BadRequestException("Problem deleting property!");
        }
    }

    private Property findProperty(Long propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NotFoundException("Property not found!"));
    }
}

