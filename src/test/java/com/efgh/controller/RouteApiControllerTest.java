package com.efgh.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration

@WebAppConfiguration
public class RouteApiControllerTest {

	@Configuration
	@ComponentScan("com.efgh")
	public static class SpringConfig {
		public SpringConfig() {
			System.setProperty("BusRoute.input.location", "./src/test/resources/valid_file.txt");
		}
	}

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void getDirectRoute_validRoute() throws Exception {
		int origin = 0;
		int destination = 1;
		mockMvc.perform(get("/api/direct?dep_sid=" + origin + "&arr_sid=" + destination)).andExpect(status().is(200));
	}

	@Test
	public void getDirectRoute_invalidRoute() throws Exception {
		int origin = 0;
		int destination = 5;
		mockMvc.perform(get("/api/direct?dep_sid=" + origin + "&arr_sid=" + destination)).andExpect(status().is(200))
				.andExpect(content().string("{\"dep_sid\":" + origin + ",\"arr_sid\":" + destination + ",\"direct_bus_route\":false}"));
	}

	@Test
	public void getDirectRoute_invalidPoints() throws Exception {
		int origin = 99;
		int destination = 1;
		mockMvc.perform(get("/api/direct?dep_sid=" + origin + "&arr_sid=" + destination)).andExpect(status().is(400));
	}

}
