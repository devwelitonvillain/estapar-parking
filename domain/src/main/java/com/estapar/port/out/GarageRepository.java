package com.estapar.port.out;

import com.estapar.model.entity.Garage;

import java.math.BigDecimal;
import java.time.LocalTime;

public interface GarageRepository {

    Integer countTotalSpotsByOpenHour(LocalTime entryTime);
    Garage save(Garage garage);
    BigDecimal findBasePriceBySector(String sector);
    LocalTime getOpenHourBySector(String sector);
    Long count();
    boolean existsBySector(String sector);

}
