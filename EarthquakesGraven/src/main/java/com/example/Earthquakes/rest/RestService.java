package com.example.Earthquakes.rest;

import com.example.Earthquakes.dto.EarthquakesDto;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        getEarthquakesByDateJSON();
    }

    public String getEarthquakesByDateJSON() {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?{format}&{starttime}&{endtime}";
        String response = this.restTemplate.getForObject(url, String.class,"geojson","2022-01-01","2022-01-03");

        System.out.println(response);
        return response;
    }
}
