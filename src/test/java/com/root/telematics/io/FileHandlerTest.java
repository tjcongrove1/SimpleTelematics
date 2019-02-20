package com.root.telematics.io;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class FileHandlerTest {
	@Test
	public void testFileReader() {
		List<List<String>> testFileLines = FileHandler.readTelemetryFile("src//test//resources//rawTrips.dat");

		// Read the defualt first line of the test file and check its contents
		assertEquals(testFileLines.get(0).get(0), "Trip");
		assertEquals(testFileLines.get(0).get(1), "MrDontTouchMe");
		assertEquals(testFileLines.get(0).get(2), "08:00");
		assertEquals(testFileLines.get(0).get(3), "09:00");
		assertEquals(testFileLines.get(0).get(4), "60.0");
	}
}
