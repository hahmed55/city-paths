package com.citypaths.service;

import com.citypaths.com.citypaths.model.CityConnections;
import com.citypaths.com.citypaths.model.CityPathsException;
import com.citypaths.util.CitiesCsvLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
public class CityPathsServiceImpl implements CityPathsService{

    private boolean initSuccessful = false;
    private static final Logger logger = Logger.getLogger(CityPathsServiceImpl.class.getName());

    @Value("${connected.cities.file}")
    private String connectedCitiesFile;

    private CityConnections graph;



    @PostConstruct
    public void init() throws IOException {
        try {
            Resource resource = new ClassPathResource(connectedCitiesFile);
            File input = resource.getFile();
            logger.info("Loading input file: " + input.getAbsolutePath());
            graph = new CitiesCsvLoader().loadCsvFile(input);
        } catch (IOException e) {
            logger.error("ERROR loading csv Input file" , e);

            // Service won't start if the CSV file is not found
            throw e;
        }
    }

    public void resetCache() throws IOException {
        try {
            Resource resource = new ClassPathResource(connectedCitiesFile);
            File input = resource.getFile();
            logger.info("Loading input file: " + input.getAbsolutePath());
            graph = new CitiesCsvLoader().loadCsvFile(input);
        } catch (IOException e) {
            logger.error("ERROR loading csv Input file during rest, cache reload unsuccessful" , e);

            // Service won't start if there is an issue with the CSV file
            throw e;
        }
    }

    @Override
    public boolean citiesConnected(String source, String destination) throws CityPathsException {
        if (source == null || source.isEmpty() || destination == null || destination.isEmpty() || source.equalsIgnoreCase(destination))
            return false;
        boolean result = false;

        try{
            result = graph.connected(source, destination);
        } catch (Exception e) {
            String errMsge = String.format("Error checking connection for %s and %s", source, destination);
            logger.error(errMsge , e);
            throw new CityPathsException(errMsge , e);
        }
        return result;
    }
}
