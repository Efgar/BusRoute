package com.efgh.entities;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class RouteMapTest {

	RouteMap mapForTesting = new RouteMap();

	@Test
	public void initializeFromFile_propperFile() throws IOException {
		System.setProperty("BusRoute.input.location", "./src/test/resources/valid_file.txt");
		mapForTesting.initializeFromFile();
		Assert.assertEquals(9, mapForTesting.routeMap.edgeSet().size());
		Assert.assertEquals(7, mapForTesting.routeMap.vertexSet().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void initializeFromFile_invalidFile() throws IOException {
		System.setProperty("BusRoute.input.location", "./src/test/resources/invalid_file.txt");
		mapForTesting.initializeFromFile();
	}

	@Test(expected = IllegalArgumentException.class)
	public void initializeFromFile_invalidFilePath() throws IOException {
		System.clearProperty("BusRoute.input.location");
		System.setProperty("BusRoute.input.location", "./src/test/resources/i_dont_exist.nope");
		mapForTesting.initializeFromFile();
	}

	@Test(expected = IllegalArgumentException.class)
	public void initializeFromFile_noFile() throws IOException {
		System.clearProperty("BusRoute.input.location");
		mapForTesting.initializeFromFile();
	}

	private void setupValidMap() {
		System.setProperty("BusRoute.input.location", "./src/test/resources/valid_file.txt");
		try {
			mapForTesting = new RouteMap();
			mapForTesting.initializeFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getPath_validPoints() {
		setupValidMap();
		Assert.assertEquals("[(0 : 1), (1 : 2)]", mapForTesting.getPath(0, 2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void getPath_invalidOrigin() {
		setupValidMap();
		mapForTesting.getPath(99, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getPath_invalidDestination() {
		setupValidMap();
		mapForTesting.getPath(0, 99);
	}

	@Test
	public void addRoutePoint_newOrigin() {
		setupValidMap();
		mapForTesting.addRoutePoint(99, 1, 0);
		Assert.assertEquals(10, mapForTesting.routeMap.edgeSet().size());
		Assert.assertEquals(8, mapForTesting.routeMap.vertexSet().size());
	}

	@Test
	public void addRoutePoint_newDestination() {
		setupValidMap();
		mapForTesting.addRoutePoint(1, 99, 0);
		Assert.assertEquals(10, mapForTesting.routeMap.edgeSet().size());
		Assert.assertEquals(8, mapForTesting.routeMap.vertexSet().size());
	}

	@Test
	public void addRoutePoint_newDestinationAndOrigin() {
		setupValidMap();
		mapForTesting.addRoutePoint(99, 98, 0);
		Assert.assertEquals(10, mapForTesting.routeMap.edgeSet().size());
		Assert.assertEquals(9, mapForTesting.routeMap.vertexSet().size());
	}

	@Test
	public void addRoutePoint_newDestinationAndOriginAndRoute() {
		setupValidMap();
		mapForTesting.addRoutePoint(99, 98, 18);
		Assert.assertEquals(10, mapForTesting.routeMap.edgeSet().size());
		Assert.assertEquals(9, mapForTesting.routeMap.vertexSet().size());
	}

	@Test
	public void directRouteExist_validPointsTrue() {
		setupValidMap();
		Assert.assertTrue(mapForTesting.directRouteExist(0, 1));
	}

	@Test
	public void directRouteExist_validPointsFalse() {
		setupValidMap();
		Assert.assertFalse(mapForTesting.directRouteExist(0, 5));
	}

	@Test(expected=IllegalArgumentException.class)
	public void directRouteExist_invalidOrigin() {
		setupValidMap();
		mapForTesting.directRouteExist(99, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void directRouteExist_invalidDestination() {
		setupValidMap();
		mapForTesting.directRouteExist(0, 99);
	}
}
