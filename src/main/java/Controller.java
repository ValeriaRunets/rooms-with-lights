import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.record.Country;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class Controller {
    public Controller() {
    }

    boolean checkIpCountry(Room room, String address){
        try {
            File dbFile = new File(this.getClass().getClassLoader().getResource("GeoLite2-Country.mmdb").getPath());
            DatabaseReader reader = new DatabaseReader.Builder(dbFile).build();
            InetAddress ipAddress = InetAddress.getByName("178.120.33.132");
            Country country = reader.country(ipAddress).getCountry();
            String countryName = country.getName();
            return room.getCountry().equals(countryName);
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
