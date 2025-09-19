package com.estapar.adapter.out.persistence;

import com.estapar.model.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface SpotJpaRepository extends JpaRepository<Spot, Long> {

    @Query("SELECT s FROM Spot s WHERE s.latitude = :latitude AND s.longitude = :longitude")
    Optional<Spot> findByCoordinates(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude);

}
