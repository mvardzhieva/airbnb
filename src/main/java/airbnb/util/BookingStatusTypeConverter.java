package airbnb.util;

import airbnb.model.pojo.BookingStatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BookingStatusTypeConverter implements AttributeConverter<BookingStatusType, String> {

    @Override
    public String convertToDatabaseColumn(BookingStatusType bookingStatusType) {
        if (bookingStatusType == null) {
            return null;
        }
        return bookingStatusType.toString();
    }

    @Override
    public BookingStatusType convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        }
        try {
            return BookingStatusType.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
