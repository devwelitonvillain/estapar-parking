package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Vehicle;
import com.estapar.port.out.GarageRepository;
import com.estapar.port.out.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

@Component
public class VehicleRepositoryImpl implements VehicleRepository {

    private final VehicleJpaRepository jpaRepository;

    public VehicleRepositoryImpl(final VehicleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Vehicle> findByLicensePlate(final String licensePlate) {
        return jpaRepository.findByLicensePlate(licensePlate);
    }

    @Override
    @Transactional
    public Vehicle save(final Vehicle vehicle) {
        return jpaRepository.save(vehicle);
    }
}
