package com.nearbybank.find.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearbybank.find.model.Bank;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleMapsClient {
	
	private static final String placesBaseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private final ObjectMapper objectMapper = new ObjectMapper();

	Dotenv dotenv = Dotenv.load();
	
	String apiKey = dotenv.get("GOOGLE_MAPS_API_KEY");
 // Move to application.properties or application.yml

    private final RestTemplate restTemplate = new RestTemplate();

    public String[] getCoordinates(String zipcode) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&key=" + apiKey;

        ResponseEntity<GoogleMapsResponse> response = restTemplate.getForEntity(url, GoogleMapsResponse.class);
        GoogleMapsResponse body = response.getBody();

        if (body == null || body.getResults() == null || body.getResults().isEmpty()) {
            throw new RuntimeException("Invalid zipcode or no results found");
        }

        GoogleMapsResponse.Location location = body.getResults().get(0).getGeometry().getLocation();
        return new String[]{location.getLat(), location.getLng()};
    }

    public List<Bank> getNearbyBanks(String lat, String lng) {
        String url = String.format(
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s,%s&radius=16093&type=bank&key=%s",
            lat, lng, apiKey
        );

        ResponseEntity<GooglePlacesResponse> response = restTemplate.getForEntity(url, GooglePlacesResponse.class);
        GooglePlacesResponse body = response.getBody();

        if (body == null || body.getResults() == null) {
            return List.of();
        }

        return body.getResults().stream().map(place -> {
            String name = place.getName();
            String address = place.getVicinity();
            GoogleMapsResponse.Location location = place.getGeometry().getLocation();
            return new Bank(name, address, location.getLat(), location.getLng());
        }).collect(Collectors.toList());
    }
    
    public List<Bank> findNearbyBanks(String latitude, String longitude) throws IOException {
        String url = UriComponentsBuilder.fromUriString(placesBaseUrl)
                .queryParam("location", latitude + "," + longitude)
                .queryParam("radius", "16093") // 10 miles in meters
                .queryParam("type", "bank")
                .queryParam("key", apiKey)
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IOException("Failed to get nearby places: " + response.getStatusCode());
        }

        JsonNode results = objectMapper.readTree(response.getBody()).path("results");
        List<Bank> banks = new ArrayList<>();

        for (JsonNode result : results) {
            String name = result.path("name").asText();
            String address = result.path("vicinity").asText();
            String lat = result.path("geometry").path("location").path("lat").asText();
            String lng = result.path("geometry").path("location").path("lng").asText();

            banks.add(new Bank(name, address, lat, lng));
        }

        return banks;
    }

}
