package com.efgh.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efgh.entities.RouteMap;
import com.efgh.responses.RouteResponse;


@Service
public class RouteApiServiceImpl implements RouteApiService{

	@Resource
	RouteMap routes;
	
	@Override
	public RouteResponse hasDirectRoute(int originStationId, int destinyStationId) {
		return new RouteResponse(originStationId, destinyStationId, routes.directRouteExist(originStationId, destinyStationId));
	}
    
}
