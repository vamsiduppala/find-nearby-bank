package com.nearbybank.find.integration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nearbybank.find.model.Bank;

@Component
public class GoogleMapsClient {

    @Value("${maps-service.url}")
    private String mapsServiceUrl;

    private final RestTemplate restTemplate;

    public GoogleMapsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] getCoordinatesFromZip(String zipcode) {
        String url = mapsServiceUrl + "/api/maps/coordinates?zipcode=" + zipcode;
        return restTemplate.getForObject(url, String[].class);
    }

    public List<Bank> getNearbyBanks(String latitude, String longitude) {
        String url = mapsServiceUrl + "/api/maps/banks?lat=" + latitude + "&lng=" + longitude;
        System.out.println("calling the maps service: " + url);
        try {
            Bank[] response = restTemplate.getForObject(url, Bank[].class);
            return Arrays.asList(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
