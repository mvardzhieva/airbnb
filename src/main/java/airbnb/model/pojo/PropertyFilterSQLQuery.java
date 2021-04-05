package airbnb.model.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PropertyFilterSQLQuery {

    private Long typeId;
    private Long cityId;
    private Long countryId;
    private String name;
    private String description;
    private Double minPrice;
    private Double maxPrice;


    public PropertyFilterSQLQuery withTypeId(Long typeId) {
        if (typeId == null || typeId <= 0) {
            this.typeId = 1L;
        } else {
            this.typeId = typeId;
        }

        return this;
    }

    public PropertyFilterSQLQuery withCityId(Long cityId) {
        if (cityId == null || cityId <= 0) {
            this.cityId = 1L;
        } else {
            this.cityId = cityId;
        }

        return this;
    }

    public PropertyFilterSQLQuery withCountryId(Long countryId) {
        if (countryId == null || countryId <= 0) {
            this.countryId = 1L;
        } else {
            this.countryId = countryId;
        }

        return this;
    }

    public PropertyFilterSQLQuery withName(String name) {
        if (name == null || name.isEmpty()) {
            this.name = "%%";
        } else {
            this.name = "%" + name + "%";
        }

        return this;
    }

    public PropertyFilterSQLQuery withDescription(String description) {
        if (description == null || description.isEmpty()) {
            this.description = "%%";
        } else {
            this.description = "%"+ description + "%";
        }

        return this;
    }

    public PropertyFilterSQLQuery withMinPrice(Double minPrice) {
        if (minPrice == null || maxPrice >= 0) {
            this.minPrice = 0.0;
        } else {
            this.minPrice = minPrice;
        }

        return this;
    }

    public PropertyFilterSQLQuery withMaxPrice(Double maxPrice) {
        if (maxPrice == null || maxPrice <= 0) {
            this.maxPrice = Double.MAX_VALUE;
        } else {
            this.maxPrice = maxPrice;
        }

        return this;
    }

}
