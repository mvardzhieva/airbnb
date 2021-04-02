package airbnb.model.dao;

import airbnb.model.pojo.*;
import airbnb.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PropertyMapper implements RowMapper<Property> {

    private UserRepository userRepository;
    private PropertyTypeRepository propertyTypeRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private RatingRepository ratingRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public PropertyMapper(UserRepository userRepository,
                          PropertyTypeRepository propertyTypeRepository,
                          CityRepository cityRepository,
                          CountryRepository countryRepository,
                          RatingRepository ratingRepository,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.ratingRepository = ratingRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Property mapRow(ResultSet rs, int i) throws SQLException {
        Property property = new Property();
        property.setHost(setUser(rs.getLong("host_id")));
        property.setType(setPropertyType(rs.getLong("type_id")));
        property.setCity(setCity(rs.getLong("city_id")));
        property.setCountry(setCountry(rs.getLong("country_id")));
        property.setName(rs.getString("name"));
        property.setDescription(rs.getString("description"));
        property.setPrice(rs.getDouble("price"));
        property.setRatings(setRatings(property));
        property.setIsFree(setIsFree(property));
        property.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime().toLocalDate());

        return property;
    }

    private User setUser(Long id) {
        return userRepository.getOne(id.intValue());
    }

    private PropertyType setPropertyType(Long id) {
        return propertyTypeRepository.getOne(id);
    }

    private City setCity(Long id) {
        return cityRepository.getOne(id);
    }

    private Country setCountry(Long id) {
        return countryRepository.getOne(id);
    }

    private List<Rating> setRatings(Property property) {
        return ratingRepository.findAllByProperty(property);
    }

    private Rating setRating(Property property) {
        Rating rating = new Rating();

        List<Rating> ratings = ratingRepository.findAllByProperty(property);
        for (Rating r : ratings) {
            rating.setCleanliness(rating.getCleanliness() + r.getCleanliness());
            rating.setCommunication(rating.getCleanliness() + r.getCleanliness());
            rating.setCheckIn(rating.getCheckIn() + r.getCheckIn());
            rating.setAccuracy(rating.getAccuracy() + r.getAccuracy());
            rating.setLocation(rating.getLocation() + r.getLocation());
            rating.setValue(rating.getValue() + r.getValue());
        }

        rating.setCleanliness(rating.getCleanliness() / ratings.size());
        rating.setCommunication(rating.getCleanliness() / ratings.size());
        rating.setCheckIn(rating.getCheckIn() / ratings.size());
        rating.setAccuracy(rating.getAccuracy() / ratings.size());
        rating.setLocation(rating.getLocation() / ratings.size());
        rating.setValue(rating.getValue() / ratings.size());
        rating.setProperty(ratings.get(0).getProperty());

        return rating;
    }

    public Boolean setIsFree(Property property) {
//        return bookingRepository.getFirstByPropertyOrderByEndDateEndDateDesc(property).getStatusId() == 3;
        return true;
    }
}
