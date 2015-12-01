package io.gof.tender.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Location {
    private Result[] results;
    private String status;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Result {
        private AddressComponent[] address_components;
        private String formatted_address;
        private Geometry geometry;
        private Boolean partial_match;
        private String place_id;
        private String[] types;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class AddressComponent {
            private String long_name;
            private String short_name;
            private String[] types;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Geometry {
            private Map<String, Map<String, Double>> bounds;
            private Map<String, Double> location;
            private String location_type;
            private Map<String, Map<String, Double>> viewport;
        }
    }

    public static Location fromJson(String json) {
        try {
            return new ObjectMapper().readerFor(Location.class).readValue(json);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
