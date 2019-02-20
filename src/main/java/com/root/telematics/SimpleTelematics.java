package com.root.telematics;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.root.telematics.io.FileHandler;
import com.root.telematics.io.ReportGenerator;
import com.root.telematics.mapper.DriverTripsMapper;

public class SimpleTelematics {
	private static final String basePath = "./";

	public static void main(String[] args) {
		List<List<String>> fileLines = null;
		try {
			fileLines = FileHandler.readTelemetryFile(basePath + args[0]);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("File specified was not found!");
				System.out.println(
						"Please ensure the file is in the same directory as the JAR and that its name is correct!");

				System.out.println("Enter the filename once more: ");
				Scanner scanner = new Scanner(System.in);
				String filename = scanner.nextLine();

				while (null == fileLines) {
					try {
						fileLines = FileHandler.readTelemetryFile(basePath + filename);
					} catch (Exception e1) {
						if (e1 instanceof FileNotFoundException) {
							System.out.println("File specified was not found!");
							System.out.println(
									"Please ensure the file is in the same directory as the JAR and that its name is correct!");

							System.out.println("Enter the filename once more: ");
							scanner = new Scanner(System.in);
							filename = scanner.nextLine();
						} else {
							System.out.println("Uncaught IO exception occurred while reading the input file!");
							System.out.println("Please ensure that the file is correctly formatted and try again.");
							System.out.println("Error:  " + e.getMessage());
						}
					}
					scanner.close();
				}
			} else {
				System.out.println("Uncaught IO exception occurred while reading the input file!");
				System.out.println("Please ensure that the file is correctly formatted and try again.");
				System.out.println("Error:  " + e.getMessage());
			}
		}

		ReportGenerator.generateSimpleDriverReport(DriverTripsMapper.mapFileToDriversList(fileLines));
	}
}
