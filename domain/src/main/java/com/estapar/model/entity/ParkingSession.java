package com.estapar.model.entity;

import com.estapar.model.valueobject.SessionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "parking_sessions", indexes = {
    @Index(name = "idx_vehicle_status", columnList = "vehicle_id, status"),
    @Index(name = "idx_sector_entry_time", columnList = "sector, entry_time"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_entry_time", columnList = "entry_time"),
    @Index(name = "idx_exit_time", columnList = "exit_time")
})
public class ParkingSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;
    
    @Column(name = "spot_id")
    private Long spotId;
    
    @Column(name = "sector", nullable = false)
    private String sector;
    
    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;
    
    @Column(name = "parked_time")
    private LocalDateTime parkedTime;
    
    @Column(name = "exit_time")
    private LocalDateTime exitTime;
    
    @Column(name = "dynamic_price_rate", nullable = false)
    private BigDecimal dynamicPriceRate;
    
    @Column(name = "final_amount")
    private BigDecimal finalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SessionStatus status = SessionStatus.ENTERED;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Spot spot;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector", referencedColumnName = "sector", insertable = false, updatable = false)
    private Garage garage;
    
    public ParkingSession() {}
    
    public ParkingSession(Long vehicleId, String sector, LocalDateTime entryTime, BigDecimal dynamicPriceRate) {
        this.vehicleId = vehicleId;
        this.sector = sector;
        this.entryTime = entryTime;
        this.dynamicPriceRate = dynamicPriceRate;
        this.status = SessionStatus.ENTERED;
    }

    public ParkingSession(Long vehicleId, Long spotId, String sector, LocalDateTime entryTime, BigDecimal dynamicPriceRate) {
        this.vehicleId = vehicleId;
        this.spotId = spotId;
        this.sector = sector;
        this.entryTime = entryTime;
        this.dynamicPriceRate = dynamicPriceRate;
        this.status = SessionStatus.ENTERED;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public Long getSpotId() {
        return spotId;
    }
    
    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }
    
    public String getSector() {
        return sector;
    }
    
    public void setSector(String sector) {
        this.sector = sector;
    }
    
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
    
    public LocalDateTime getParkedTime() {
        return parkedTime;
    }
    
    public void setParkedTime(LocalDateTime parkedTime) {
        this.parkedTime = parkedTime;
    }
    
    public LocalDateTime getExitTime() {
        return exitTime;
    }
    
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
    
    public BigDecimal getDynamicPriceRate() {
        return dynamicPriceRate;
    }
    
    public void setDynamicPriceRate(BigDecimal dynamicPriceRate) {
        this.dynamicPriceRate = dynamicPriceRate;
    }
    
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }
    
    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    public SessionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SessionStatus status) {
        this.status = status;
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
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Spot getSpot() {
        return spot;
    }
    
    public void setSpot(Spot spot) {
        this.spot = spot;
    }
    
    public Garage getGarage() {
        return garage;
    }
    
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
    
    @Override
    public String toString() {
        return "ParkingSession{" +
                "id=" + id +
                ", vehicleId=" + vehicleId +
                ", spotId=" + spotId +
                ", sector='" + sector + '\'' +
                ", entryTime=" + entryTime +
                ", parkedTime=" + parkedTime +
                ", exitTime=" + exitTime +
                ", dynamicPrice=" + dynamicPriceRate +
                ", finalAmount=" + finalAmount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ParkingSession that = (ParkingSession) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
