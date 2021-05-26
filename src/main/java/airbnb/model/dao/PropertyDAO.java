package airbnb.model.dao;

import airbnb.model.pojo.Property;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyDAO {

    private JdbcTemplate db;
    private PropertyMapper propertyMapper;


    @Autowired
    @SneakyThrows
    public PropertyDAO(JdbcTemplate db, PropertyMapper propertyMapper) {
        this.db = db;
        this.propertyMapper = propertyMapper;
    }

    @SneakyThrows
    public List<Property> get(Long typeId, Long cityId, Long countryId,
                                 String name, String description, BigDecimal minPrice, BigDecimal maxPrice,
                                 Long size, Long offset) {



        PreparedStatementCreator preparedStatement = connection -> {
            boolean isPaging = false;
            String sql = "SELECT p.* FROM properties p " +
                    "WHERE  p.type_id = ? " +
                    "AND  p.city_id = ? " +
                    "AND p.country_id = ? " +
                    "AND p.name LIKE ? " +
                    "AND p.description LIKE ? " +
                    "AND  p.price BETWEEN ? AND ? ";

            if (size.intValue() > 0 && offset.intValue() >= 0) {
                sql = sql + "LIMIT ?  OFFSET ? ";
                isPaging = true;
            }

            PreparedStatement statement = db.getDataSource()
                    .getConnection()
                    .prepareStatement(sql);

            statement.setLong(1, typeId);
            statement.setLong(2, cityId);
            statement.setLong(3, countryId);
            statement.setString(4,"%" + name + "%");
            statement.setString(5,"%" + description + "%");
            statement.setDouble(6, minPrice.doubleValue());
            statement.setDouble(7, maxPrice.doubleValue());

            if (isPaging) {
                statement.setLong(8, size);
                statement.setLong(9, (size * offset));
            }

            return statement;
        };

        return db.query(preparedStatement, propertyMapper)
                .stream().collect(Collectors.toList());
    }
}
