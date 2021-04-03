package airbnb.services.interfaces;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.Location;

import java.io.IOException;

public interface LocationService {

    Location getLocation(String ip) throws IOException, GeoIp2Exception;
}
