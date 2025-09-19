package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.ParkingSession;
import com.estapar.port.out.ParkingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingSessionRepositoryImpl implements ParkingSessionRepository {

    private final ParkingSessionJpaRepository jpaRepository;

    public ParkingSessionRepositoryImpl(final ParkingSessionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<ParkingSession> findActiveByLicensePlate(final String licensePlate) {
        return jpaRepository.findActiveByLicensePlate(licensePlate);
    }

    @Override
    public Integer totalSessionWithParkedOrEnteredStatus() {
        return jpaRepository.countByStatusParkedAndEntered();
    }

    @Override
    @Transactional
    public ParkingSession save(final ParkingSession parkingSession) {
        return jpaRepository.save(parkingSession);
    }

    @Override
    public boolean existsEnteredSession(final String licensePlate) {
        return jpaRepository.existsEnteredSession(licensePlate);
    }

    @Override
    public Optional<ParkingSession> findByLicensePlateEntered(final String licensePlate) {
        return jpaRepository.findByLicensePlateEntered(licensePlate);
    }

    @Override
    public List<ParkingSession> findByDateAndSector(LocalDate date, String sector) {
        return jpaRepository.findByDateAndSector(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX), sector);
    }
}
