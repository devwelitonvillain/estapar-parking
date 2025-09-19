package com.estapar.usecase.garage;

import com.estapar.dto.garage.GarageConfigDto;
import com.estapar.model.entity.Garage;
import com.estapar.model.entity.Spot;
import com.estapar.model.exception.InvalidGarageConfigException;
import com.estapar.model.exception.SimulatorConnectionException;
import com.estapar.port.in.garage.GarageInitializationUseCase;
import com.estapar.port.out.GarageRepository;
import com.estapar.port.out.GarageSimulatorClient;
import com.estapar.port.out.SpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GarageInitializationUseCaseImpl implements GarageInitializationUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GarageInitializationUseCaseImpl.class);

    private final GarageSimulatorClient garageSimulatorClient;
    private final GarageRepository garageRepository;
    private final SpotRepository spotRepository;

    public GarageInitializationUseCaseImpl(GarageSimulatorClient garageSimulatorClient,
                                           GarageRepository garageRepository,
                                           SpotRepository spotRepository) {
        this.garageSimulatorClient = garageSimulatorClient;
        this.garageRepository = garageRepository;
        this.spotRepository = spotRepository;
    }

    @Override
    public void loadGarageConfiguration() {
        logger.info("Iniciando carregamento da configuração da garagem");

        try {
            GarageConfigDto config = (GarageConfigDto) garageSimulatorClient.getGarageConfiguration();
            validateConfiguration(config);

            for (GarageConfigDto.GarageDto garageDto : config.getGarage()) {
                saveGarage(garageDto);
            }

            for (GarageConfigDto.SpotDto spotDto : config.getSpots()) {
                saveSpot(spotDto);
            }
            logger.info("Configuração da garagem carregada: {} setores, {} vagas",
                    config.getGarage().size(), config.getSpots().size());

        } catch (Exception e) {
            logger.error("Erro ao carregar configuração da garagem: {}", e.getMessage());
            throw new SimulatorConnectionException("Falha ao carregar dados do simulador", e);
        }
    }

    @Override
    public boolean isInitialized() {
        long garageCount = garageRepository.count();
        long spotCount = spotRepository.count();

        boolean initialized = garageCount > 0 && spotCount > 0;
        logger.debug("Verificação de inicialização: {} setores, {} vagas - Inicializado: {}",
                garageCount, spotCount, initialized);

        return initialized;
    }

    private void validateConfiguration(GarageConfigDto config) {
        if (config == null) {
            throw new InvalidGarageConfigException("Configuração da garagem não pode ser nula");
        }

        if (config.getGarage() == null || config.getGarage().isEmpty()) {
            throw new InvalidGarageConfigException("Lista de setores não pode estar vazia");
        }

        if (config.getSpots() == null || config.getSpots().isEmpty()) {
            throw new InvalidGarageConfigException("Lista de vagas não pode estar vazia");
        }

        logger.debug("Configuração validada: {} setores, {} vagas", config.getGarage().size(), config.getSpots().size());
    }

    private void saveGarage(GarageConfigDto.GarageDto garageDto) {
        try {
            Garage garage = new Garage(
                    garageDto.getSector(),
                    garageDto.getBasePrice(),
                    garageDto.getMaxCapacity(),
                    garageDto.getOpenHour(),
                    garageDto.getCloseHour(),
                    garageDto.getDurationLimitMinutes()
            );
            garageRepository.save(garage);
        } catch (Exception e) {
            throw new InvalidGarageConfigException("Erro ao processar setor " + garageDto.getSector() + ": " + e.getMessage());
        }
    }

    private void saveSpot(GarageConfigDto.SpotDto spotDto) {
        try {
            Spot spot = new Spot(spotDto.getId(), spotDto.getSector(), spotDto.getLat(), spotDto.getLng());
            spotRepository.save(spot);
        } catch (Exception e) {
            throw new InvalidGarageConfigException("Erro ao processar vaga " + spotDto.getId() + ": " + e.getMessage());
        }
    }
}
