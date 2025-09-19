package com.estapar.port.out;

import com.estapar.model.entity.Spot;

import java.math.BigDecimal;
import java.util.Optional;

public interface SpotRepository {

    Optional<Spot> findByCoordinates(BigDecimal latitude, BigDecimal longitude);
    Spot save(Spot spot);
    Optional<Spot> findById(Long id);
    Long count();

}
