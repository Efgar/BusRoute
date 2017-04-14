package com.efgh.controller;

import org.springframework.web.bind.annotation.RestController;

import com.efgh.responses.ErrorResponse;
import com.efgh.responses.RouteResponse;
import com.efgh.service.RouteApiService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value = "/api")
public class RouteApiController {

	@Resource
	RouteApiService routeApiService;

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
	public @ResponseBody ErrorResponse illegalArgumentHandler(HttpServletRequest req, Exception ex) {
		ErrorResponse response = new ErrorResponse(ex);
		System.out.println(response);
		return response;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "")
	public @ResponseBody ErrorResponse unexpectedExceptionHandler(HttpServletRequest req, Exception ex) {
		ErrorResponse response = new ErrorResponse(ex);
		return response;
	}

	@RequestMapping("/direct")
	public RouteResponse getDirectRoute(@RequestParam(name = "dep_sid") int originStationId,
			@RequestParam(name = "arr_sid") int destinyStationId) {
		return routeApiService.hasDirectRoute(originStationId, destinyStationId);
	}

}