package airbnb.services.interfaces;

public interface LocationService {

    void setLocation(String ip) throws Exception;

    Double getLatitude();

    Double getLongitude();
}