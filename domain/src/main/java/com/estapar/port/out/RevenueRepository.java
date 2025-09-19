package com.estapar.port.out;

import com.estapar.model.entity.Revenue;

import java.time.LocalDate;
import java.util.List;

public interface RevenueRepository {

    Revenue save(Revenue revenue);

    List<Revenue> findByDateAndSector(LocalDate date, String sector);

}
