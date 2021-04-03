package airbnb.services;

import airbnb.services.interfaces.LocationService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
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

    //TODO REFACTOR
    public LocationServiceImpl() throws IOException {
        File database = new File("src/main/resources/GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public Location getLocation(String ip)
            throws IOException, GeoIp2Exception {

        if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
            ip = "185.247.56.37";
        }

        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        return response.getLocation();
    }


}
