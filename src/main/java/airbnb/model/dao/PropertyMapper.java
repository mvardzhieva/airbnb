package airbnb.model.dao;

import airbnb.model.pojo.*;
import airbnb.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PropertyMapper implements RowMapper<Property> {

    private UserRepository userRepository;
    private PropertyTypeRepository propertyTypeRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private RatingRepository ratingRepository;
    private BookingRepository bookingRepository;
    private MediaRepository mediaRepository;

    @Autowired
    public PropertyMapper(UserRepository userRepository,
                          PropertyTypeRepository propertyTypeRepository,
                          CityRepository cityRepository,
                          CountryRepository countryRepository,
                          RatingRepository ratingRepository,
                          BookingRepository bookingRepository,
                          MediaRepository mediaRepository) {
        this.userRepository = userRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.ratingRepository = ratingRepository;
        this.bookingRepository = bookingRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Property mapRow(ResultSet rs, int i) throws SQLException {
        Property property = new Property();
        property.setId(rs.getLong("id"));
        property.setHost(setUser(rs.getLong("host_id")));
        property.setType(setPropertyType(rs.getLong("type_id")));
        property.setCity(setCity(rs.getLong("city_id")));
        property.setCountry(setCountry(rs.getLong("country_id")));
        property.setName(rs.getString("name"));
        property.setDescription(rs.getString("description"));
        property.setPrice(rs.getDouble("price"));
        property.setLatitude(rs.getDouble("latitude"));
        property.setLongitude(rs.getDouble("longitude"));
        property.setCreatedAt(rs.getTimestamp("created_at")
                .toLocalDateTime().toLocalDate());
        property.setRatings(setRatings(rs.getLong("id")));
        property.setMedia(setMedia(rs.getLong("id")));
        property.setRatings(setRatings(rs.getLong("id")));
        property.setBookings(setBookings(rs.getLong("id")));

        return property;
    }

    private User setUser(Long id) {
        return userRepository.findById(id.intValue()).get();
    }

    private PropertyType setPropertyType(Long id) {
        return propertyTypeRepository.findById(id).get();
    }

    private City setCity(Long id) {
        return cityRepository.findById(id).get();
    }

    private Country setCountry(Long id) {
        return countryRepository.findById(id).get();
    }

    private Set<Media> setMedia(Long id) {
        return mediaRepository.findAllByPropertyId(id)
                .stream().collect(Collectors.toSet());
    }

    private List<Rating> setRatings(Long id) {
        return ratingRepository.findAllByPropertyId(id);
    }

    private List<Booking> setBookings(Long id) {
        return bookingRepository.findAllByPropertyId(id);
    }
}
