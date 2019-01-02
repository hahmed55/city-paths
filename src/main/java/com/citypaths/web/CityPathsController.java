package com.citypaths.web;

import com.citypaths.com.citypaths.model.CityPathsException;
import com.citypaths.service.CityPathsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class CityPathsController {

    private static final Logger logger = Logger.getLogger(CityPathsController.class.getName());

    @Autowired
    private CityPathsService cityPathsService;

    @RequestMapping("/")
    public String index() {
        return "The city paths micro-service is up and running";
    }

    @RequestMapping("/connected")
    public String areConnected(@RequestParam("origin") String source , @RequestParam("destination") String destination) throws CityPathsException {
        boolean pathExists = cityPathsService.citiesConnected(source , destination);
        return pathExists? "yes" : "no";
    }
}
