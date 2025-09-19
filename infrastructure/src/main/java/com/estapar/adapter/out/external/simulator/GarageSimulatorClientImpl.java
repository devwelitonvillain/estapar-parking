package com.estapar.adapter.out.external.simulator;

import com.estapar.dto.garage.GarageConfigDto;
import com.estapar.model.exception.SimulatorConnectionException;
import com.estapar.port.out.GarageSimulatorClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class GarageSimulatorClientImpl implements GarageSimulatorClient {

    private final RestTemplate restTemplate;
    private final String simulatorBaseUrl;

    public GarageSimulatorClientImpl(RestTemplate restTemplate, String simulatorBaseUrl) {
        this.restTemplate = restTemplate;
        this.simulatorBaseUrl = simulatorBaseUrl;
    }


    @Override
    public GarageConfigDto getGarageConfiguration() {
        String url = simulatorBaseUrl + "/garage";

        try {
            GarageConfigDto config = restTemplate.getForObject(url, GarageConfigDto.class);

            if (config == null) {
                throw new SimulatorConnectionException("Empty response from simulator");
            }

            return config;
        } catch (RestClientException e) {
            throw new SimulatorConnectionException("Simulator connection error: " + e.getMessage(), e);
        }
    }
}
