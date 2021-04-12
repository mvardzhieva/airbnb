package airbnb.services;

import airbnb.services.interfaces.LocationService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
@Primary
public class LocationServiceImpl implements LocationService {

    private DatabaseReader dbReader;
    private Location location;

    public LocationServiceImpl() throws IOException {
        File database = new File("src/main/resources/GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public void setLocation(String ip) throws Exception {

        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
            ip = "185.247.56.37";
        }

        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        location = response.getLocation();
    }

    public Double getLatitude() {
        return location.getLatitude();
    }


    public Double getLongitude() {
        return location.getLongitude();
    }
}
