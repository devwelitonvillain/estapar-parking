package com.estapar.adapter.in.event;

import com.estapar.port.in.garage.GarageInitializationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

    private final GarageInitializationUseCase garageInitializationUseCase;

    public ApplicationEventListener(@Lazy GarageInitializationUseCase garageInitializationUseCase) {
        this.garageInitializationUseCase = garageInitializationUseCase;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("=== Iniciando carregamento da configuração da garagem ===");

        try {
            if (garageInitializationUseCase.isInitialized()) {
                logger.info("Configuração da garagem já existe no banco de dados");
            } else {
                logger.info("Carregando configuração da garagem do simulador...");
                garageInitializationUseCase.loadGarageConfiguration();
                logger.info("Configuração da garagem carregada com sucesso!");
            }
        } catch (Exception e) {
            logger.error("Erro ao carregar configuração da garagem: {}", e.getMessage(), e);
            throw new RuntimeException("Falha na inicialização da aplicação", e);
        }

        logger.info("=== Aplicação inicializada com sucesso ===");
    }

}
