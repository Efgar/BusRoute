package com.efgh.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.efgh.responses.RouteResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RouteApiServiceImplTest {

	@Configuration
	@ComponentScan("com.efgh")
	public static class SpringConfig {
		public SpringConfig() {
			System.setProperty("BusRoute.input.location", "./src/test/resources/valid_file.txt");
		}
	}

	@Resource
	RouteApiService routeApiService;

	@Test
	public void hasDirectRoute_validRoute() {
		int origin = 0;
		int destination = 1;
		RouteResponse response = routeApiService.hasDirectRoute(origin, destination);

		Assert.assertEquals(origin, response.getDep_sid());
		Assert.assertEquals(destination, response.getArr_sid());
		Assert.assertTrue(response.isDirect_bus_route());
	}

	@Test
	public void hasDirectRoute_invalidRoute() {
		int origin = 0;
		int destination = 5;
		RouteResponse response = routeApiService.hasDirectRoute(origin, destination);

		Assert.assertEquals(origin, response.getDep_sid());
		Assert.assertEquals(destination, response.getArr_sid());
		Assert.assertFalse(response.isDirect_bus_route());
	}

	@Test(expected = IllegalArgumentException.class)
	public void hasDirectRoute_nonValidPoints() {
		int origin = 0;
		int destination = 99;
		routeApiService.hasDirectRoute(origin, destination);
	}

}
