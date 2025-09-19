package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalTime;

public interface GarageJpaRepository extends JpaRepository<Garage, Long> {

    @Query("""
            SELECT SUM(gg.maxCapacity) FROM Garage gg WHERE gg.openHour <= :entryTime
            """)
    Integer countTotalSpotsByOpenHour(@Param("entryTime") LocalTime entryTime);

    @Query("SELECT gg.basePrice FROM Garage gg WHERE gg.sector = :sector")
    BigDecimal findBasePriceBySector(@Param("sector") String sector);

    @Query("SELECT gg.openHour FROM Garage gg WHERE gg.sector = :sector")
    LocalTime getOpenHourBySector(@Param("sector") String sector);

    @Query("SELECT CASE WHEN COUNT(gg) > 0 THEN true ELSE false END FROM Garage gg WHERE gg.sector = :sector")
    boolean existsBySector(String sector);

}
