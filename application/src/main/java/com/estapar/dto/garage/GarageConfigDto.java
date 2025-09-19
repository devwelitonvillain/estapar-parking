package com.estapar.dto.garage;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class GarageConfigDto {

    @JsonProperty("garage")
    private List<GarageDto> garage;

    @JsonProperty("spots")
    private List<SpotDto> spots;

    public List<GarageDto> getGarage() { return garage; }
    public void setGarage(List<GarageDto> garage) { this.garage = garage; }

    public List<SpotDto> getSpots() { return spots; }
    public void setSpots(List<SpotDto> spots) { this.spots = spots; }


    public static class GarageDto {

        @JsonProperty("sector")
        private String sector;

        @JsonProperty("base_price")
        private BigDecimal basePrice;

        @JsonProperty("max_capacity")
        private Integer maxCapacity;

        @JsonProperty("open_hour")
        private LocalTime openHour;

        @JsonProperty("close_hour")
        private LocalTime closeHour;

        @JsonProperty("duration_limit_minutes")
        private Integer durationLimitMinutes;

        public String getSector() { return sector; }
        public void setSector(String sector) { this.sector = sector; }

        public BigDecimal getBasePrice() { return basePrice; }
        public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

        public Integer getMaxCapacity() { return maxCapacity; }
        public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

        public LocalTime getOpenHour() {
            return openHour;
        }

        public void setOpenHour(LocalTime openHour) {
            this.openHour = openHour;
        }

        public LocalTime getCloseHour() {
            return closeHour;
        }

        public void setCloseHour(LocalTime close_hour) {
            this.closeHour = close_hour;
        }

        public Integer getDurationLimitMinutes() {
            return durationLimitMinutes;
        }

        public void setDurationLimitMinutes(Integer durationLimitMinutes) {
            this.durationLimitMinutes = durationLimitMinutes;
        }
    }

    public static class SpotDto {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("sector")
        private String sector;

        @JsonProperty("lat")
        private BigDecimal lat;

        @JsonProperty("lng")
        private BigDecimal lng;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getSector() { return sector; }
        public void setSector(String sector) { this.sector = sector; }

        public BigDecimal getLat() { return lat; }
        public void setLat(BigDecimal lat) { this.lat = lat; }

        public BigDecimal getLng() { return lng; }
        public void setLng(BigDecimal lng) { this.lng = lng; }
    }

}
