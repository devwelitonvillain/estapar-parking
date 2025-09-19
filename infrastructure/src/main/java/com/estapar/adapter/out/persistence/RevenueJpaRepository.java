package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Revenue;
import com.estapar.model.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RevenueJpaRepository extends JpaRepository<Revenue, Long> {

    @Query("SELECT r FROM Revenue r WHERE r.transactionDate = :date AND r.sector = :sector")
    List<Revenue> findByTransactionDateAndSector(@Param("date") LocalDate date, @Param("sector") String sector);

}
