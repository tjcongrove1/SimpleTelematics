package com.root.telematics.io;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class FileHandlerTest {
	@Test
	public void testFileReader() {
		List<List<String>> testFileLines = null;
		try {
			testFileLines = FileHandler.readTelemetryFile("src//test//resources//rawTrips.dat");
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {

			} else {
				e.printStackTrace();
			}
		}

		// Read the defualt first trip of the test file and check its contents
		assertEquals(testFileLines.get(4).get(0), "Trip");
		assertEquals(testFileLines.get(4).get(1), "MrDontTouchMe");
		assertEquals(testFileLines.get(4).get(2), "08:00");
		assertEquals(testFileLines.get(4).get(3), "09:00");
		assertEquals(testFileLines.get(4).get(4), "60.0");
	}

	// Test that proper exception is thrown when file is wrong
	@Test(expected = FileNotFoundException.class)
	public void testFileReaderError() throws IOException {
		List<List<String>> testFileLines = FileHandler.readTelemetryFile("src//test//resources//badFileName.dat");
	}
}
