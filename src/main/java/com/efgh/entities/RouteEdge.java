package com.efgh.entities;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

public class RouteEdge extends DefaultEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6791954594238936920L;

	private Set<Integer> routeIdentifiers = new HashSet<>();

	public boolean isDirectlyConectedTo(RouteEdge routeEdge){
		if(routeEdge == null){
			throw new IllegalArgumentException("Received edge for comparisson was null");
		}
		for (Integer routeId : getRouteIdentifiers()) {
			if(routeEdge.getRouteIdentifiers().contains(routeId)){
				return true;
			}
		}
		return false;
	}
	
	public void addRoute(int route) {
		routeIdentifiers.add(route);
	}

	public Set<Integer> getRouteIdentifiers() {
		return routeIdentifiers;
	}
}
