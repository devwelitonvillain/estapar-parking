package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Garage;
import com.estapar.port.out.GarageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component
public class GarageRepositoryImpl implements GarageRepository {

    private final GarageJpaRepository jpaRepository;

    public GarageRepositoryImpl(final GarageJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Integer countTotalSpotsByOpenHour(final LocalTime entryTime) {
        return jpaRepository.countTotalSpotsByOpenHour(entryTime);
    }

    @Override
    @Transactional
    public Garage save(final Garage garage) {
        return jpaRepository.save(garage);
    }

    @Override
    public BigDecimal findBasePriceBySector(final String sector) {
        return jpaRepository.findBasePriceBySector(sector);
    }

    @Override
    public LocalTime getOpenHourBySector(final String sector) {
        return jpaRepository.getOpenHourBySector(sector);
    }

    @Override
    public Long count() {
        return jpaRepository.count();
    }

    @Override
    public boolean existsBySector(String sector) {
        return jpaRepository.existsBySector(sector);
    }
}
