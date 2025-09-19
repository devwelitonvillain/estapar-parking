package com.estapar.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "revenues", indexes = {
    @Index(name = "idx_sector_date", columnList = "sector, transaction_date"),
    @Index(name = "idx_transaction_date", columnList = "transaction_date"),
    @Index(name = "idx_transaction_timestamp", columnList = "transaction_timestamp")
})
public class Revenue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "parking_session_id", nullable = false, unique = true)
    private Long parkingSessionId;
    
    @Column(name = "sector", nullable = false)
    private String sector;
    
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    
    @Column(name = "currency", nullable = false)
    private String currency = "BRL";
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
    
    @Column(name = "transaction_timestamp", nullable = false)
    private LocalDateTime transactionTimestamp;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_session_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ParkingSession parkingSession;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector", referencedColumnName = "sector", insertable = false, updatable = false)
    private Garage garage;
    
    public Revenue() {}
    
    public Revenue(Long parkingSessionId, String sector, BigDecimal amount, LocalDate transactionDate, LocalDateTime transactionTimestamp) {
        this.parkingSessionId = parkingSessionId;
        this.sector = sector;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionTimestamp = transactionTimestamp;
        this.currency = "BRL";
    }
    
    public Revenue(Long parkingSessionId, String sector, BigDecimal amount, String currency, LocalDate transactionDate, LocalDateTime transactionTimestamp) {
        this.parkingSessionId = parkingSessionId;
        this.sector = sector;
        this.amount = amount;
        this.currency = currency != null ? currency : "BRL";
        this.transactionDate = transactionDate;
        this.transactionTimestamp = transactionTimestamp;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getParkingSessionId() {
        return parkingSessionId;
    }
    
    public void setParkingSessionId(Long parkingSessionId) {
        this.parkingSessionId = parkingSessionId;
    }
    
    public String getSector() {
        return sector;
    }
    
    public void setSector(String sector) {
        this.sector = sector;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public LocalDateTime getTransactionTimestamp() {
        return transactionTimestamp;
    }
    
    public void setTransactionTimestamp(LocalDateTime transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public ParkingSession getParkingSession() {
        return parkingSession;
    }
    
    public void setParkingSession(ParkingSession parkingSession) {
        this.parkingSession = parkingSession;
    }
    
    public Garage getGarage() {
        return garage;
    }
    
    public void setGarage(Garage garage) {
        this.garage = garage;
    }
    
    @Override
    public String toString() {
        return "Revenue{" +
                "id=" + id +
                ", parkingSessionId=" + parkingSessionId +
                ", sector='" + sector + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionTimestamp=" + transactionTimestamp +
                ", createdAt=" + createdAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Revenue revenue = (Revenue) o;
        return Objects.equals(id, revenue.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
