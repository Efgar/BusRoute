package com.efgh.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RouteMap {

	static final Logger LOG = LoggerFactory.getLogger(RouteMap.class);

	final Graph<Integer, RouteEdge> routeMap = new SimpleGraph<>(RouteEdge.class);

	@PostConstruct
	public void initializeFromFile() throws IOException {
		String initFilePath = System.getProperty("BusRoute.input.location");
		if (StringUtils.isEmpty(initFilePath)) {
			throw new IllegalArgumentException("Invalid file location path (" + initFilePath + ") sepcified.");
		}

		try {
			FileSystemResource res = new FileSystemResource(initFilePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] route = line.split(" ");
				if (route.length > 2) {
					int routeId = Integer.parseInt(route[0]);
					for (int i = 1; i < route.length - 1; i++) {
						addRoutePoint(Integer.parseInt(route[i]), Integer.parseInt(route[i + 1]), routeId);
					}
				} else {
					LOG.warn("The route [" + line + "] will not be added due to invalid format");
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid file location path (" + initFilePath + ") sepcified.", e);
		}
	}

	public String getPath(Integer originId, Integer destinationId) {
		GraphPath<Integer, RouteEdge> path = DijkstraShortestPath.findPathBetween(routeMap, originId, destinationId);
		return path.toString();
	}

	public void addRoutePoint(Integer originId, Integer destinationId, Integer routeId) {
		routeMap.addVertex(originId);
		routeMap.addVertex(destinationId);
		RouteEdge edge = routeMap.getEdge(originId, destinationId);
		if (edge == null) {
			edge = new RouteEdge();
		}
		edge.addRoute(routeId);

		routeMap.addEdge(originId, destinationId, edge);
	}

	public boolean directRouteExist(Integer originId, Integer destinationId) {
		for (RouteEdge leavingRoutes : routeMap.edgesOf(originId)) {
			for (RouteEdge arrivingRoutes : routeMap.edgesOf(destinationId)) {
				if (leavingRoutes.isDirectlyConectedTo(arrivingRoutes)) {
					return true;
				}
			}
		}
		return false;
	}
}
