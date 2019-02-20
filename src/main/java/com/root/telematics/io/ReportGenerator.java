package com.root.telematics.io;

import java.util.Comparator;
import java.util.List;

import com.root.telematics.model.Driver;

public class ReportGenerator {
	public static void generateSimpleDriverReport(List<Driver> drivers) {
		drivers.sort(new SortByMileage());

		for (Driver driver : drivers) {
			System.out.print(driver.getDriverName() + ": " + driver.getTotalMilesForAllTrips() + " miles");
			if (driver.getTotalMilesForAllTrips() > 0) {
				System.out.print(" @ " + driver.getAverageSpeedForAllTrips() + " mph");
			}
			System.out.print(System.lineSeparator());
		}
	}
	
	public static void generateSimpleDriverReportUsingTotalAverages(List<Driver> drivers) {
		
	}
}

class SortByMileage implements Comparator<Driver> {
	// Used for sorting in descending order of
	// miles driven
	public int compare(Driver a, Driver b) {
		return b.getTotalMilesForAllTrips() - a.getTotalMilesForAllTrips();
	}
}