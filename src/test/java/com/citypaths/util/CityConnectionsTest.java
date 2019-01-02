package com.citypaths.util;

import com.citypaths.com.citypaths.model.CityConnections;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
public class CityConnectionsTest {

    private static CityConnections graph;

    @BeforeClass
    public static void init() throws Exception{
        Resource resource = new ClassPathResource("/city-test.txt");
        File input = resource.getFile();
        graph = new CitiesCsvLoader().loadCsvFile(input);
    }

    @Test
    public void testVerifyListOfCities() throws Exception {
        Set<String> cities = graph.getCitiesInGraph();
        List<String> expected = Arrays.asList(new String[]{"New York","Newark","Trenton","Wilmington","Philadelphia","Boston","Albany","Old Bridge"});
        Assert.assertTrue(cities.containsAll(expected));
        Assert.assertFalse(cities.contains("New Brunswick"));
    }

    @Test
    public void testVerifySingleConnection() throws Exception {
        Set<String> connections = graph.getCityConnections("Philadelphia");
        Assert.assertTrue(connections.size() == 1);
        Assert.assertTrue(connections.contains("Newark"));
    }

    @Test
    public void testVerifyMultipleConnections() throws Exception {
        List<String> expected = Arrays.asList(new String[]{"Philadelphia","Boston","Old Bridge"});
        Set<String> connections = graph.getCityConnections("Newark");
        Assert.assertTrue(connections.size() == 3);
        Assert.assertTrue(connections.containsAll(expected));
    }

    @Test
    public void testConnected() throws Exception {
        Assert.assertTrue(graph.connected("New York" , "Old Bridge"));
        Assert.assertTrue(graph.connected("Old Bridge" , "New York"));
    }

    @Test
    public void testConnectedCityToItself() throws Exception {
        Assert.assertTrue(graph.connected("Boston" , "Boston"));
    }

    @Test
    public void testNotConnected() throws Exception {
        Assert.assertFalse(graph.connected("New York" , "Stamford"));
        Assert.assertFalse(graph.connected("Stamford" , "New York"));
    }

    @Test
    public void testNotInList() throws Exception {
        Assert.assertFalse(graph.connected("Albany" , "New Brunswick"));
        Assert.assertFalse(graph.connected("New Brunswick" , "New York"));
    }

    @Test
    public void testEmptyOrNullOrigin() throws Exception {
        Assert.assertFalse(graph.connected("" , "Old Bridge"));
        Assert.assertFalse(graph.connected(null , "New York"));
    }

    @Test
    public void testEmptyOrNullDestination() throws Exception {
        Assert.assertFalse(graph.connected("Albany" , ""));
        Assert.assertFalse(graph.connected("New York" , null));
    }

}
