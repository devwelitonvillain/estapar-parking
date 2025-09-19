package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.ParkingSession;
import com.estapar.port.out.ParkingSessionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingSessionJpaRepository extends JpaRepository<ParkingSession, Long> {

    @Query("""
        SELECT ps FROM ParkingSession ps 
        JOIN ps.vehicle v 
        WHERE v.licensePlate = :licensePlate 
        AND ps.status IN ('ENTERED', 'PARKED')
        ORDER BY ps.entryTime DESC
        """)
    Optional<ParkingSession> findActiveByLicensePlate(@Param("licensePlate") String licensePlate);

    @Query("""
        SELECT ps FROM ParkingSession ps 
        WHERE ps.status IN ('ENTERED', 'PARKED')
        ORDER BY ps.entryTime DESC
        """)
    List<ParkingSession> findAllActiveSessions();

    @Query("SELECT COUNT(ps) FROM ParkingSession ps WHERE ps.status IN ('ENTERED', 'PARKED')")
    Integer countByStatusParkedAndEntered();

    @Query("""
        SELECT CASE WHEN COUNT(ps) > 0 THEN true ELSE false END 
        FROM ParkingSession ps 
        WHERE ps.status IN ('ENTERED') AND ps.vehicle.licensePlate = :licensePlate
        """)
    boolean existsEnteredSession(@Param("licensePlate") String licensePlate);

    @Query("SELECT ps FROM ParkingSession ps WHERE ps.vehicle.licensePlate = :licensePlate AND ps.status IN ('PARKED')")
    Optional<ParkingSession> findByLicensePlateEntered(@Param("licensePlate") String licensePlate);

    @Query("SELECT ps FROM ParkingSession ps WHERE ps.entryTime BETWEEN :startDate AND :endDate AND ps.sector = :sector")
    List<ParkingSession> findByDateAndSector(
            @Param("startDate") LocalDateTime statDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("sector") String sector
    );

}
