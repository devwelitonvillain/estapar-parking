package com.estapar.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "spots", indexes = {
    @Index(name = "idx_sector_occupied", columnList = "sector, is_occupied"),
    @Index(name = "idx_coordinates", columnList = "latitude, longitude")
})
public class Spot {
    
    @Id
    private Long id;
    
    @Column(name = "sector", nullable = false)
    private String sector;
    
    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;
    
    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;
    
    @Column(name = "is_occupied", nullable = false)
    private Boolean isOccupied = false;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector", referencedColumnName = "sector", insertable = false, updatable = false)
    private Garage garage;
    
    public Spot() {}
    
    public Spot(Long id, String sector, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.sector = sector;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOccupied = false;
    }
    
    public Spot(Long id, String sector, BigDecimal latitude, BigDecimal longitude, Boolean isOccupied) {
        this.id = id;
        this.sector = sector;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOccupied = isOccupied != null ? isOccupied : false;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSector() {
        return sector;
    }
    
    public void setSector(String sector) {
        this.sector = sector;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public Boolean getIsOccupied() {
        return isOccupied;
    }
    
    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Garage getGarage() {
        return garage;
    }
    
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
    
    @Override
    public String toString() {
        return "Spot{" +
                "id=" + id +
                ", sector='" + sector + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isOccupied=" + isOccupied +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Spot spot = (Spot) o;
        return Objects.equals(id, spot.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
