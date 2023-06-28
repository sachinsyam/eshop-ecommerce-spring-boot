package com.ecomm.shopping.eShop.worker;

import com.ecomm.shopping.eShop.entity.user.AuditLog;
import com.ecomm.shopping.eShop.repository.AuditLogRepository;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class GetLocation {

    @Autowired
    AuditLogRepository auditLogRepository;

    public Map<String, String> getCity(String ipAddress) {

        Map<String, String> map = new HashMap<>();
     //   ipAddress="49.37.225.145";

        if (ipAddress.equals("127.0.0.1")) {
            map.put("city", "localhost");
            map.put("country", "localhost");
            return map;
        }

        try (DatabaseReader reader = createDatabaseReader()) {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CityResponse response = reader.city(inetAddress);

            Country country = response.getCountry();
            City city = response.getCity();

            System.out.println("Geo ID:"+city.getGeoNameId());
            System.out.println("IP: " + ipAddress);
            System.out.println("Country: " + country.getName());
            System.out.println("City: " + city.getName());
            System.out.println("Geo Name: " + city.getGeoNameId());
            System.out.println();

            map.put("country", country.getName());
            map.put("city", city.getName());

        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }

        try(DatabaseReader readerAsn = createDatabaseReaderAsn()){

            InetAddress inetAddress = InetAddress.getByName(ipAddress);

            // Get ASN details
            AsnResponse asnResponse = readerAsn.asn(inetAddress);

            map.put("asn",asnResponse.getAutonomousSystemNumber().toString());
            map.put("organization",asnResponse.getAutonomousSystemOrganization());
            map.put("network",asnResponse.getNetwork().toString());

            System.out.println("ASN: " + asnResponse.getAutonomousSystemNumber());
            System.out.println("Organization: " + asnResponse.getAutonomousSystemOrganization());



        }catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    private DatabaseReader createDatabaseReader() throws IOException {
        ClassPathResource resource = new ClassPathResource("GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(resource.getInputStream()).build();
    }

    private DatabaseReader createDatabaseReaderAsn() throws IOException {
        ClassPathResource resource = new ClassPathResource("GeoLite2-ASN.mmdb");
        return new DatabaseReader.Builder(resource.getInputStream()).build();
    }

    public void setLocationDetails(){
        List<AuditLog> auditLogs = auditLogRepository.findAll();

        auditLogs.forEach( a ->{
            Map<String, String> map = this.getCity(a.getIpAddress());
            if(!(map.get("city") == null)){
                a.setCity(map.get("city"));
            }else{
                a.setCity(map.get(""));
            }
            if(!(map.get("country") == null)){
                a.setCountry(map.get("country"));
            }else{
                a.setCountry(map.get(""));
            }
            if(!(map.get("asn") == null)){
                a.setAsn(map.get("asn"));
            }else{
                a.setAsn(map.get(""));
            }
            if(!(map.get("network") == null)){
                a.setNetwork(map.get("network"));
            }else{
                a.setNetwork(map.get(""));
            }
            if(!(map.get("organization") == null)){
                a.setOrganization(map.get("organization"));
            }else{
                a.setOrganization(map.get(""));
            }
            auditLogRepository.save(a);
        });
    }
}
