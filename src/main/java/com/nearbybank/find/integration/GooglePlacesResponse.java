package com.nearbybank.find.integration;

import java.util.List;

public class GooglePlacesResponse {
    private List<PlaceResult> results;

    public List<PlaceResult> getResults() {
        return results;
    }

    public void setResults(List<PlaceResult> results) {
        this.results = results;
    }

    public static class PlaceResult {
        private String name;
        private String vicinity;
        private GoogleMapsResponse.Geometry geometry;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public GoogleMapsResponse.Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(GoogleMapsResponse.Geometry geometry) {
            this.geometry = geometry;
        }
    }
}
