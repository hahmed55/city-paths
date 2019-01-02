package com.citypaths.service;

import com.citypaths.com.citypaths.model.CityPathsException;

public interface CityPathsService {
    public boolean citiesConnected(String source, String destination) throws CityPathsException;
}
