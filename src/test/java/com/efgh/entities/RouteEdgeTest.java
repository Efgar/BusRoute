package com.efgh.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RouteEdgeTest {

	RouteEdge testingEdge =  new RouteEdge();
	
	@Before
	public void setRoutesUp() {
		testingEdge.addRoute(0);
		testingEdge.addRoute(1);
	}

	@Test
	public void isDirectlyConectedTo_connectedEdge() {
		RouteEdge newEdge =  new RouteEdge();
		newEdge.addRoute(1);
		newEdge.addRoute(2);
		newEdge.addRoute(3);
		Assert.assertTrue(testingEdge.isDirectlyConectedTo(newEdge));
	}

	@Test
	public void isDirectlyConectedTo_disconnectedEdge() {
		RouteEdge newEdge =  new RouteEdge();
		newEdge.addRoute(2);
		newEdge.addRoute(3);
		Assert.assertFalse(testingEdge.isDirectlyConectedTo(newEdge));
	}

	@Test
	public void isDirectlyConectedTo_noConnectionsEdge() {
		RouteEdge newEdge =  new RouteEdge();
		Assert.assertFalse(testingEdge.isDirectlyConectedTo(newEdge));
	}

	@Test(expected=IllegalArgumentException.class)
	public void isDirectlyConectedTo_nullEdge() {
		RouteEdge newEdge =  null;
		testingEdge.isDirectlyConectedTo(newEdge);
	}

}
