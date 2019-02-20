package com.root.telematics.mapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.root.telematics.model.Driver;
import com.root.telematics.model.Trip;

public class DriverTripsMapper {
	public static List<Driver> mapFileToDriversList(List<List<String>> fileLines) {
		List<Driver> driverData = new ArrayList<Driver>();

		for (List<String> fileLine : fileLines) {
			/**
			 * If a line starts with the "driver" data tag,
			 * 
			 * 1. Create a new Driver entity and add it to the list, if one is not already
			 * present. If it already exists, simply move on.
			 */
			if (fileLine.get(0).equalsIgnoreCase("driver")) {
				Driver newEntry = new Driver(fileLine.get(1));
				if (!driverData.contains(newEntry)) {
					driverData.add(newEntry);
				}
			}
			/**
			 * If a line starts with the "trip" data tag,
			 * 
			 * 1. If the Driver is already present, find it in the list and add the trip to
			 * the driver's trip list
			 */
			else if (fileLine.get(0).equalsIgnoreCase("trip")) {
				Driver newEntry = new Driver(fileLine.get(1));
				Trip thisTrip = new Trip(LocalTime.parse(fileLine.get(2)), LocalTime.parse(fileLine.get(3)),
						Double.parseDouble(fileLine.get(4)));
				/**
				 * Uncomment this logic block if you want to be able to add drivers who haven't
				 * previously registered based on the name in the trip record
				 */
				// if (!driverData.contains(newEntry)) {
				// if ((5 < thisTrip.getAverageSpeed() && thisTrip.getAverageSpeed() < 100)) {
				// newEntry.addDriverTrip(thisTrip);
				// driverData.add(newEntry);
				// }
				// } else {
				if ((5 < thisTrip.getAverageSpeed() && thisTrip.getAverageSpeed() < 100)) {
					driverData.stream()
							.filter(driver -> newEntry.getDriverName().equalsIgnoreCase(driver.getDriverName()))
							.findFirst().get().addDriverTrip(thisTrip);
				}
				// }
			}
		}

		return driverData;
	}
}
