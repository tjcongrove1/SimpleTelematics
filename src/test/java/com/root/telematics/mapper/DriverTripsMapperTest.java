package com.root.telematics.mapper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.root.telematics.io.FileHandler;
import com.root.telematics.model.Driver;

public class DriverTripsMapperTest {
	List<List<String>> testFileLines;
	List<String> drivers = new ArrayList<String>();

	@Before
	public void setUp() throws IOException {
		// Read the test file as a pre-step
		testFileLines = FileHandler.readTelemetryFile("src//test//resources//rawTrips.dat");

		/**
		 * Do a simple lookup of all drivers in the raw file lines so we have a solid
		 * number which allows us to dynamically update the test file without breaking
		 * the tests
		 */
		for (List<String> line : testFileLines) {
			if (!drivers.contains(line.get(1))) {
				drivers.add(line.get(1));
			}
		}
	}

	@Test
	public void testDriverTripMapper() {
		// Perform actual mapping call to find drivers and assign trips
		List<Driver> testDriverTrips = DriverTripsMapper.mapFileToDriversList(testFileLines);

		// Make sure the right number of drivers are identified
		assertEquals(testDriverTrips.size(), drivers.size());
		// Make sure our default driver has his trips correctly assigned
		assertEquals(testDriverTrips.stream().filter(driver -> "mrdonttouchme".equalsIgnoreCase(driver.getDriverName()))
				.findFirst().get().getDriverTrips().size(), 2);
	}
	
	@Test
	public void testDriverTripCalculations() {
		// Perform actual mapping call to find drivers and assign trips
		List<Driver> testDriverTrips = DriverTripsMapper.mapFileToDriversList(testFileLines);

		// Perform driver-level average speed calculation from file
		for (Driver driver : testDriverTrips) {
			if (driver.getDriverName().equalsIgnoreCase("mrdonttouchme")) {
				assertEquals(driver.getAverageSpeedForAllTrips(), 60);
			}
			System.out.println(driver.getDriverName() + " " + driver.getAverageSpeedForAllTrips());
		}
		
		// Perform driver-level total mileage calculation from file
		for (Driver driver : testDriverTrips) {
			if (driver.getDriverName().equalsIgnoreCase("mrdonttouchme")) {
				assertEquals(driver.getTotalMilesForAllTrips(), 240);
			}
			System.out.println(driver.getDriverName() + " " + driver.getTotalMilesForAllTrips());
		}
	}

}
