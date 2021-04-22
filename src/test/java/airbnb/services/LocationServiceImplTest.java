package airbnb.services;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import java.io.File;
import java.net.InetAddress;


@SpringBootTest
class LocationServiceImplTest {

    private DatabaseReader dbReader;
    private Location location;

    @BeforeEach
    void setUp() throws Exception {

        File database = new File("src/main/resources/GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();

        String ip = "185.247.56.37";
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        location = response.getLocation();
    }

    @Test
    void getLatitude() {
        assertThat(location.getLatitude()).isEqualTo(42.2684);
    }

    @Test
    void getLongitude() {
        assertThat(location.getLongitude()).isEqualTo(23.1157);
    }
}