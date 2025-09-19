package com.estapar.configuration;

import com.estapar.adapter.out.external.simulator.GarageSimulatorClientImpl;
import com.estapar.adapter.out.persistence.SpotJpaRepository;
import com.estapar.adapter.out.persistence.SpotRepositoryImpl;
import com.estapar.port.in.garage.GarageInitializationUseCase;
import com.estapar.port.in.parking.ParkingEntryEventUseCase;
import com.estapar.port.in.parking.ParkingExitEventUseCase;
import com.estapar.port.in.parking.ParkingParkedEventUseCase;
import com.estapar.port.in.revenue.RevenueQueryUseCase;
import com.estapar.port.out.*;
import com.estapar.usecase.garage.GarageInitializationUseCaseImpl;
import com.estapar.usecase.parking.ParkingEntryEventUseCaseImpl;
import com.estapar.usecase.parking.ParkingExitEventUseCaseImpl;
import com.estapar.usecase.parking.ParkingParkedEventUseCaseImpl;
import com.estapar.usecase.revenue.RevenueQueryUseCaseImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @RequestScope
    public ParkingEntryEventUseCase parkingEntryEventUseCase(
            ParkingSessionRepository parkingSessionRepository,
            GarageRepository garageRepository,
            VehicleRepository vehicleRepository) {
        return new ParkingEntryEventUseCaseImpl(parkingSessionRepository, garageRepository, vehicleRepository);
    }

    @Bean
    @RequestScope
    public ParkingParkedEventUseCase parkingParkedEventUseCase(
            ParkingSessionRepository parkingSessionRepository,
            GarageRepository garageRepository,
            SpotRepository spotRepository
    ) {
        return new ParkingParkedEventUseCaseImpl(parkingSessionRepository, garageRepository, spotRepository);
    }

    @Bean
    @RequestScope
    public ParkingExitEventUseCase parkingExitEventUseCase(
            ParkingSessionRepository parkingSessionRepository,
            GarageRepository garageRepository,
            SpotRepository spotRepository,
            RevenueRepository revenueRepository
    ) {
        return new ParkingExitEventUseCaseImpl(parkingSessionRepository, garageRepository, spotRepository, revenueRepository);
    }

    @Bean
    @RequestScope
    public RevenueQueryUseCase revenueQueryUseCase(
            RevenueRepository revenueRepository,
            GarageRepository garageRepository
    ) {
        return new RevenueQueryUseCaseImpl(revenueRepository, garageRepository);
    }

    @Bean
    @ApplicationScope
    public GarageSimulatorClient garageSimulatorClient(
            RestTemplate restTemplate,
            @Value("${simulator.base-url:http://localhost:3000}") String simulatorBaseUrl
    ) {
        return new GarageSimulatorClientImpl(restTemplate, simulatorBaseUrl);
    }

    @Bean
    @ApplicationScope
    public GarageInitializationUseCase garageInitializationUseCase(
            GarageSimulatorClient garageSimulatorClient,
            GarageRepository garageRepository,
            SpotRepository spotRepository
    ) {
        return new GarageInitializationUseCaseImpl(garageSimulatorClient, garageRepository, spotRepository);
    }

    @Bean
    public SpotRepository spotRepository(SpotJpaRepository spotJpaRepository) {
        return new SpotRepositoryImpl(spotJpaRepository);
    }

}
