package com.citypaths.com.citypaths.model;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * This the main model class, it models the cities graph the search logic.
 */
public class CityConnections {

    private final Map<String,Set<String>> graph = new HashMap<>();

    // The use of read and write locks to enable future enhancement to reset the cache
    // if the city.txt file is edited, a secured rest method can be exposed to reset the cache
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public CityConnections(){}

    public CityConnections(List<String[]> lines) {
        try {
            writeLock.lock();
            readCsvIntoGraph(lines);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * A method to be used in the future to reset the cache.
     * could be used if there is a need to expose a secured rest post call to
     * refresh the cache
     * @param lines
     */
    public void reset(List<String[]> lines) {
        try {
            writeLock.lock();
            readCsvIntoGraph(lines);
        } finally {
            writeLock.unlock();
        }
    }

    private void readCsvIntoGraph(List<String[]> lines) {
        graph.clear();
        for (String[] tokens: lines) {
            if (tokens.length == 2){
                String city1 = tokens[0].trim();
                String city2 = tokens[1].trim();
                if (! city1.isEmpty() && ! city2.isEmpty()) {
                    addConnection(city1 ,city2);
                }
            }
        }
    }

    private void addConnection(String city1, String city2) {
        if (! graph.containsKey(city1)) {
            graph.put(city1 , new HashSet<>());
        }
        Set<String> city1Adjacents = graph.get(city1);
        city1Adjacents.add(city2);

        if (! graph.containsKey(city2)) {
            graph.put(city2 , new HashSet<>());
        }
        Set<String> city2Adjacents = graph.get(city2);
        city2Adjacents.add(city1);
    }

    /**
     * Determines if 2 cities are connected via series of roads
     * @param source
     * @param destination
     * @return wheter the origin is connected to destination
     */
    public boolean connected(String source , String destination) {
        if (source == null || source.isEmpty() || destination == null || destination.isEmpty()) {
            return false;
        }

        if (source.equalsIgnoreCase(destination)) return true;

        boolean result = false;
        try {
            readLock.lock();
            if (!graph.containsKey(source) || !graph.containsKey(destination)) {
                return false;
            }
            result = bfs(source, destination);
        } finally {
            readLock.unlock();
        }
        return result;
    }

    private boolean bfs(String source , String destination) {
        if (source.equalsIgnoreCase(destination)) {
            return true;
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(source);


        while (! queue.isEmpty()) {
            String city = queue.poll();
            if (visited.contains(city)) continue;
            visited.add(city);

            Set<String> adjacents = graph.get(city);
            if (adjacents.contains(destination)) return true;
            for (String adj : adjacents) {
                if (! visited.contains(adj))queue.add(adj);
            }
        }
        return false;
    }

    // helper methods for testing

    public Set<String> getCitiesInGraph() {
        return graph.keySet();
    }

    public Set<String> getCityConnections(String city) {
        return graph.get(city);
    }

}
