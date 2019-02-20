package com.root.telematics.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	/**
	 * This class will return a list of file lines, each of which is a list of
	 * Strings. It will only add non-empty lines to the list
	 * 
	 * @param filePath
	 * @return A list of String lists, each representing a line of the input file
	 * @throws IOException
	 */
	public static List<List<String>> readTelemetryFile(String filePath) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		List<List<String>> fileLines = new ArrayList<List<String>>();
		inputStream = new FileInputStream(filePath);
		sc = new Scanner(inputStream, "UTF-8");
		String line = null;

		// Read each line of the space-delimited file into a list
		while (sc.hasNextLine()) {
			// Relatively inefficient, but cleanest way to ignore empty lines in the file
			if(!(line = sc.nextLine()).trim().isEmpty()) {
				fileLines.add(Arrays.asList(line.split(" ")));
			}
		}

		// Cleanup
		inputStream.close();
		sc.close();

		return fileLines;
	}
}
