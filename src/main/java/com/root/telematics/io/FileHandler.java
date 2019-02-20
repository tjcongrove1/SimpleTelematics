package com.root.telematics.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	public static List<List<String>> readTelemetryFile(String filePath) {
		FileInputStream inputStream = null;
		Scanner sc = null;
		List<List<String>> fileLines = new ArrayList<List<String>>();
		try {
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				fileLines.add(Arrays.asList(sc.nextLine().split(" ")));
			}

			inputStream.close();
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileLines;
	}
}
