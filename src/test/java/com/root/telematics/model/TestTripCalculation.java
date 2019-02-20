package com.root.telematics.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestTripCalculation {
	Driver driver;
	List<Trip> trips = new ArrayList<Trip>();

	@Before
	public void setUp() {
		driver = new Driver("TestDriver");
		trips.add(new Trip(LocalTime.parse("08:00"), LocalTime.parse("09:00"), 70.0));
		driver.setDriverTrips(trips);
	}

	@Test
	public void testTripSpeedCalculation() {
		// Perform trip-level average speed calculation
		assertEquals(trips.get(0).getAverageSpeed(), 70);
		System.out.println(driver.getDriverName() + " " + trips.get(0).getAverageSpeed());
	}

}
