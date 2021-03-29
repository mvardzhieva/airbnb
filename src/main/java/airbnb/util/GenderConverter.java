package airbnb.util;

import airbnb.model.pojo.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.toString();
    }

    @Override
    public Gender convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        }
        try {
            return Gender.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
