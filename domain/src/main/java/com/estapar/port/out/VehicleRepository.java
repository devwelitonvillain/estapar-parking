package com.estapar.port.out;

import com.estapar.model.entity.Vehicle;

import java.util.Optional;

public interface VehicleRepository {

    Optional<Vehicle> findByLicensePlate(String licensePlate);
    Vehicle save(Vehicle vehicle);

}
