package com.root.telematics.io;

import java.util.Comparator;
import java.util.List;

import com.root.telematics.model.Driver;

public class ReportGenerator {
	/**
	 * This report genearates total miles and average speed for each driver based on
	 * aggregated individual trip averages. The report is presented in decending
	 * order of mileage driven
	 * 
	 * @param drivers
	 */
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

	/**
	 * This report genearates total miles and average speed for each driver based on
	 * TOTAL TRIP TIME and TOTAL MILEAGE across all trips. The report is presented
	 * in decending order of mileage driven
	 * 
	 * @param drivers
	 */
	public static void generateSimpleDriverReportUsingTotalAverages(List<Driver> drivers) {
		drivers.sort(new SortByMileage());

		for (Driver driver : drivers) {
			System.out.print(driver.getDriverName() + ": " + driver.getTotalMilesForAllTrips() + " miles");
			if (driver.getTotalMilesForAllTrips() > 0) {
				System.out.print(" @ " + driver.getAverageSpeedForAllTripsBasedOnTotals() + " mph");
			}
			System.out.print(System.lineSeparator());
		}
	}
}

/**
 * This class simply changes the way list sorting behaves for the Driver class
 * 
 * @author congrt1
 *
 */
class SortByMileage implements Comparator<Driver> {
	// Used for sorting in descending order of
	// miles driven
	public int compare(Driver a, Driver b) {
		return b.getTotalMilesForAllTrips() - a.getTotalMilesForAllTrips();
	}
}