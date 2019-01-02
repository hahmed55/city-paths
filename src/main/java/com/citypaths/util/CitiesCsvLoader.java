package com.citypaths.util;

import com.citypaths.com.citypaths.model.CityConnections;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public class CitiesCsvLoader {

    private static final Logger logger = Logger.getLogger(CitiesCsvLoader.class.getName());

    public CityConnections loadCsvFile(File inputFile) throws IOException {
        Reader csvFileReader = new BufferedReader(new FileReader(inputFile));
        CSVReader reader = new CSVReader(csvFileReader , ',');
        List<String[]> lines = reader.readAll();
        CityConnections cityConnections = new CityConnections(lines);
        return cityConnections;
    }
}
