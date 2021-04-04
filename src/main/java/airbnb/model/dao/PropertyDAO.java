package airbnb.model.dao;

import airbnb.model.dto.property.FilterRequestPropertyDTO;
import airbnb.model.pojo.Property;
import airbnb.model.pojo.PropertyFilterSQLQuery;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PropertyDAO {

    private JdbcTemplate db;
    private PropertyMapper propertyMapper;
    private PropertyFilterSQLQuery query;

    //todo extra  - interface

    @Autowired
    @SneakyThrows
    public PropertyDAO(JdbcTemplate db, PropertyMapper propertyMapper,
                       PropertyFilterSQLQuery query) {
        this.db = db;
        this.propertyMapper = propertyMapper;
        this.query = query;
    }

    @SneakyThrows
    public Set<Property> filter(FilterRequestPropertyDTO propertyDTO) {

        PropertyFilterSQLQuery sqlQuery = new PropertyFilterSQLQuery();
        sqlQuery.withTypeId(propertyDTO.getTypeId())
                .withCityId(propertyDTO.getCityId())
                .withCountryId(propertyDTO.getCountryId())
                .withName(propertyDTO.getName())
                .withDescription(propertyDTO.getDescription())
                .withMinPrice(propertyDTO.getMinPrice())
                .withMaxPrice(propertyDTO.getMaxPrice());

        PreparedStatementCreator sql1 = connection -> {
            String sql = "SELECT p.* FROM properties p " +
                    "WHERE  p.type_id = ? " +
                    "AND  p.city_id = ? " +
                    "AND p.country_id = ? " +
                    "AND p.name LIKE ? " +
                    "AND p.description LIKE ? " +
                    "AND  p.price > ? " +
                    "AND p.price < ? \n";

            PreparedStatement preparedStatement = db.getDataSource()
                    .getConnection()
                    .prepareStatement(sql);

            preparedStatement.setLong(1, sqlQuery.getTypeId());
            preparedStatement.setLong(2, sqlQuery.getCityId());
            preparedStatement.setLong(3, sqlQuery.getCountryId());
            preparedStatement.setString(4, sqlQuery.getName());
            preparedStatement.setString(5, sqlQuery.getDescription());
            preparedStatement.setDouble(6, sqlQuery.getMinPrice());
            preparedStatement.setDouble(7, sqlQuery.getMaxPrice());

            return preparedStatement;
        };

        return db.query(sql1, propertyMapper).stream().collect(Collectors.toSet());

    }
}
