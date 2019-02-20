package com.root.telematics.io;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class FileHandlerTest {
	@Test
	public void testFileReader() {
		List<List<String>> testFileLines = FileHandler.readTelemetryFile("src//test//resources//rawTrips.dat");

		assertEquals(testFileLines.size(),6);
		assertEquals(testFileLines.get(0).get(0), "Driver");
		assertEquals(testFileLines.get(0).get(1), "Dan");
		assertEquals(testFileLines.get(0).get(0), "Driver");
		assertEquals(testFileLines.get(0).get(1), "Dan");
		
		assertEquals(testFileLines.get(3).get(0), "Trip");
		assertEquals(testFileLines.get(3).get(4), "17.3");
	}
}
