package com.root.telematics.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.root.telematics.model.Driver;

public class TestDriverCalculation {
	Driver driver;
	List<Trip> trips = new ArrayList<Trip>();

	@Before
	public void setUp() {
		driver = new Driver("TestDriver");
		trips.add(new Trip(LocalTime.parse("08:00"), LocalTime.parse("09:00"), 70.0));
		trips.add(new Trip(LocalTime.parse("08:00"), LocalTime.parse("09:00"), 50.0));
		driver.setDriverTrips(trips);
	}

	@Test
	public void testDriverTripSpeed() {
		// Perform driver-level average speed calculation
		assertEquals(driver.getAverageSpeedForAllTrips(), 60);
		System.out.println(driver.getDriverName() + " " + driver.getAverageSpeedForAllTrips());
	}
	
	@Test
	public void testDriverTripMiles() {
		// Perform driver-level total mileage calculation
		assertEquals(driver.getTotalMilesForAllTrips(), 120);
		System.out.println(driver.getDriverName() + " " + driver.getTotalMilesForAllTrips());
	}
	
	@Test
	public void testDriverTripMinutes() {
		// Perform driver-level total drive-time calculation
		assertEquals(driver.getTotalMinutesForAllTrips(), 120);
		System.out.println(driver.getDriverName() + " " + driver.getTotalMinutesForAllTrips());
	}
}
