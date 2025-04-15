	package com.nearbybank.find.integration;
	
	import java.util.List;
	
	public class GoogleMapsResponse {
	    private List<Result> results;
	
	    // Getters and Setters
	
	    public List<Result> getResults() {
	        return results;
	    }
	
	    public void setResults(List<Result> results) {
	        this.results = results;
	    }
	
	    public static class Result {
	        private Geometry geometry;
	
	        // Getters and Setters
	
	        public Geometry getGeometry() {
	            return geometry;
	        }
	
	        public void setGeometry(Geometry geometry) {
	            this.geometry = geometry;
	        }
	    }
	
	    public static class Geometry {
	        private Location location;
	
	        // Getters and Setters
	
	        public Location getLocation() {
	            return location;
	        }
	
	        public void setLocation(Location location) {
	            this.location = location;
	        }
	    }
	
	    public static class Location {
	        private String lat;
	        private String lng;
	
	        // Getters and Setters
	
	        public String getLat() {
	            return lat;
	        }
	
	        public void setLat(String lat) {
	            this.lat = lat;
	        }
	
	        public String getLng() {
	            return lng;
	        }
	
	        public void setLng(String lng) {
	            this.lng = lng;
	        }
	    }
	}
