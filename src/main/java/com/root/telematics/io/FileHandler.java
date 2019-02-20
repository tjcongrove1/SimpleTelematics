package com.root.telematics.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	public static List<List<String>> readTelemetryFile(String filePath) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		List<List<String>> fileLines = new ArrayList<List<String>>();
		inputStream = new FileInputStream(filePath);
		sc = new Scanner(inputStream, "UTF-8");

		// Read each line of the space-delimited file into a list
		while (sc.hasNextLine()) {
			fileLines.add(Arrays.asList(sc.nextLine().split(" ")));
		}

		// Cleanup
		inputStream.close();
		sc.close();

		return fileLines;
	}
}
