package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Revenue;
import com.estapar.port.out.RevenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RevenueRepositoryImpl implements RevenueRepository {

    private final RevenueJpaRepository revenueJpaRepository;

    public RevenueRepositoryImpl(RevenueJpaRepository revenueJpaRepository) {
        this.revenueJpaRepository = revenueJpaRepository;
    }

    @Override
    @Transactional
    public Revenue save(Revenue revenue) {
        return revenueJpaRepository.save(revenue);
    }

    @Override
    public List<Revenue> findByDateAndSector(LocalDate date, String sector) {
        return revenueJpaRepository.findByTransactionDateAndSector(date, sector);
    }

}
