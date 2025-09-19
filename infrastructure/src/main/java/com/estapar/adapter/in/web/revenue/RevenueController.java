package com.estapar.adapter.in.web.revenue;

import com.estapar.adapter.in.web.revenue.dto.RevenueResponseDto;
import com.estapar.port.in.revenue.RevenueQueryUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/revenue")
public class RevenueController {

    private static final Logger logger = LoggerFactory.getLogger(RevenueController.class);

    private final RevenueQueryUseCase revenueQueryUseCase;

    public RevenueController(final RevenueQueryUseCase revenueQueryUseCase) {
        this.revenueQueryUseCase = revenueQueryUseCase;
    }

    @GetMapping
    public ResponseEntity<RevenueResponseDto> calculateRevenue(
            @Valid @RequestParam("date") LocalDate date, @RequestParam("sector") String sector) {
        logger.info("Received revenue request. Date: {}, Sector: {}", date, sector);
        var result = revenueQueryUseCase.execute(date, sector);
        RevenueResponseDto revenueResponseDto = new RevenueResponseDto(result);
        logger.info("Successfully processed revenue request: {}", revenueResponseDto);
        return ResponseEntity.ok(revenueResponseDto);
    }

}
