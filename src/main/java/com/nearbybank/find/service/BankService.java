package com.nearbybank.find.service;

import com.nearbybank.find.model.Bank;
import com.nearbybank.find.integration.GoogleMapsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BankService {

    private final GoogleMapsClient googleMapsClient;

    @Autowired
    public BankService(GoogleMapsClient googleMapsClient) {
        this.googleMapsClient = googleMapsClient;
    }

    public ResponseEntity<?> findNearbyBanks(String zipcode) {
        String[] coordinates;

        try {
            coordinates = googleMapsClient.getCoordinates(zipcode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Failed to retrieve coordinates for the given zipcode.",
                            "details", e.getMessage()
                    ));
        }

        if (coordinates.length < 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", "fail",
                            "message", "Coordinates not found for the provided zipcode."
                    ));
        }

        List<Bank> banks;

        try {
            banks = googleMapsClient.findNearbyBanks(coordinates[0], coordinates[1]);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Failed to retrieve nearby banks.",
                            "details", e.getMessage()
                    ));
        }

        if (banks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", "success",
                            "message", "No banks found within 10 miles of the given zipcode.",
                            "data", List.of()
                    ));
        }

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Banks found within 10 miles of the given zipcode.",
                "data", banks
        ));
    }
}
