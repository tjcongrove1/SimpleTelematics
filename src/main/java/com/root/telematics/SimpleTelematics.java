package com.root.telematics;

import java.io.FileNotFoundException;
import java.util.List;

import com.root.telematics.io.FileHandler;
import com.root.telematics.io.ReportGenerator;
import com.root.telematics.mapper.DriverTripsMapper;

public class SimpleTelematics {
	private static final String basePath = "./";

	public static void main(String[] args) {
		List<List<String>> fileLines = null;
		if (null != args && !(args.length == 0) && !args[0].isEmpty()) {
			try {
				fileLines = FileHandler.readTelemetryFile(basePath + args[0]);

				// This report generates the result you asked for with your sample output.
				ReportGenerator.generateSimpleDriverReportUsingTotalAverages(
						DriverTripsMapper.mapFileToDriversList(fileLines));

				// This report leverages native calculatory methods in the trip classes
				// ReportGenerator.generateSimpleDriverReport(DriverTripsMapper.mapFileToDriversList(fileLines));
			} catch (Exception e) {
				if (e instanceof FileNotFoundException) {
					System.out.println("File specified was not found!");
					System.out.println(
							"Please ensure the file is in the same directory as the JAR and that its name is correct!");
				} else {
					System.out.println("Uncaught IO exception occurred while reading the input file!");
					System.out.println("Please ensure that the file is correctly formatted and try again.");
					System.out.println("Error:  " + e.getMessage());
				}
			}
		} else {
			System.out.println("You must pass an argument indicating the name of the target file!");
			System.out.println("Example: java -jar jarName targetFile.dat");
		}
	}
}
