package com.example.earthquakes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadJSONFile {

    ObjectMapper om = new ObjectMapper();

    public void mapToObject(String url, String country, Long days) throws Exception {

        Map<String, Object> resultMap = om.readValue(new URL(url), new TypeReference<Map<String, Object>>() {});
        List<Feature> features = om.convertValue(resultMap.get("features"), new TypeReference<List<Feature>>() {});
        List<EarthquakeDto> earthquakes = new ArrayList<>();

        for(Feature f : features) {
            // Write the feature to the console to see how it looks like
            // System.out.println(om.writeValueAsString(f));
            // Extract properties
            EarthquakeDto earthquakeDto = new EarthquakeDto();
            Map<String,Object> properties = f.getProperties();

            String place = (String) properties.get("place");
            if (place != null && place.toLowerCase().contains(country.toLowerCase())){
                earthquakeDto.setPlace(place);
                Long time = (Long) properties.get("time");
                if(time != null)
                    earthquakeDto.setTime(new Date(time));
                Object magnitude = properties.get("mag");
                if(magnitude != null)
                    earthquakeDto.setMagnitude(magnitude);
                earthquakes.add(earthquakeDto);

            }

        }
        if(earthquakes.isEmpty())
            System.out.println("No Earthquakes were recorded past %s days".formatted(days));
        else
            earthquakes.stream().forEach(i -> System.out.println(i.getPlace()+ "  " + i.getMagnitude()+ "  " +"  "+i.getTime()+ "  " + country));
    }


    public static void main(String[] args) throws Exception {
        ReadJSONFile rjf = new ReadJSONFile();
        Long days = 0L;
        String country;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Days Count: ");
        days = sc.nextLong();
        System.out.print("Enter Country: ");

        sc.nextLine(); // "dispose" of the '\n' character
        // so that it is not recorded by the next line

        country = sc.nextLine();

// print your findings
        Date date = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String endDate= simpleDateFormat.format(date);
        String startDate = simpleDateFormat.format(date.getTime() - (1000 * 60 * 60 * 24)*days);
        rjf.mapToObject("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s".formatted(startDate,endDate),country,days);
    }
}