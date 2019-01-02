City Paths:
============

A java application which determines if two cities are connected. Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.
This is a spring-boot application that exposes a rest interface that takes 2 cities as parameters and returns the answer as to whether the 2 cities are connected.

Input:
======
The roads connecting cities are given as a list of pairs of cities that are connected, and are available in a csv which contains those pairs one per line, comma separated.
Note: the cities paths data is expected to be in the city.txt file under the root resources folder, the city.txt is referenced in the application.properties file under the same folder
if the file name or location changes, the value of the connected.cities.file property needs to be adjusted accordingly.
The file is required for the application and service will fail to start if this file is not found.


Usage:
=======
When the application starts, the rest endpoint will be
http://localhost:8080/connected?origin=city1&destination=city2

The service will respond with ‘yes’ if city1 is connected to city2, ’no’ if city1 is not connected to city2.

Any unexpected input should result in a ’no’ response.

Example:

city.txt content is:

Boston, New York

Philadelphia, Newark

Newark, Boston

Trenton, Albany

http://localhost:8080/connected?origin=Boston&destination=Newark

Should return yes

http://localhost:8080/connected?origin=Boston&destination=Philadelphia

Should return yes

http://localhost:8080/connected?origin=Philadelphia&destination=Albany

Should return no


Implementation:
===============
This problem is modeled as a graph problem that determines if 2 vertices on the graph are connected, it uses Breadth First Search to traverse the graph from
the origin city until it either finds the destination city or BFS is exhausted with no match


Configuration:
===============
The city.txt input file is configured via connected.cities.file property in the application.properties file, this property needs to be set up correctly in order for the service to start.

Repository structure:
======================
The base package com.citypaths contqains 4 packages:
- The web package has the spring-boot app and the controllers
- The service package has the main service interface CityPathsService and its implementation
- The model package has the main class that executes the business logic CityConnections and 2 domain classes for exception handling CityPathsException and ErrorMessage
- The util package has a utility class that loads the graph data from the csv file.

Testing:
========
The CityConnectionsTest is a unit test class to test the business logic.
The CityPathsServiceTest is an integration test to test the CityPathsController using SpringBootTest.

Future Enhancements:
====================
Expose a secured rest call /resetCache, to be accessed by privileged users to reload the city.txt file after being edited.