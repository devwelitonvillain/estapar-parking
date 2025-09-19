package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Spot;
import com.estapar.port.out.SpotRepository;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public class SpotRepositoryImpl implements SpotRepository {

    private final SpotJpaRepository jpaRepository;

    public SpotRepositoryImpl(SpotJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Spot> findByCoordinates(final BigDecimal latitude, final BigDecimal longitude) {
        return jpaRepository.findByCoordinates(latitude, longitude);
    }

    @Override
    @Transactional
    public Spot save(final Spot spot) {
        return jpaRepository.save(spot);
    }

    @Override
    public Optional<Spot> findById(final Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Long count() {
        return jpaRepository.count();
    }
}
