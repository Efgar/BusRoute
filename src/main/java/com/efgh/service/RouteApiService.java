package com.efgh.service;

import com.efgh.responses.RouteResponse;

public interface RouteApiService {

	public RouteResponse hasDirectRoute(int originStationId, int destinyStationId);
	
}
