package com.root.telematics.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.root.telematics.model.Driver;
import com.root.telematics.model.Trip;

public class DriverTripsMapper {
	public Map<Driver, List<Trip>> mapFileToDriversList(List<List<String>> fileLines) {
		Map<Driver, List<Trip>> driverData = new HashMap<Driver, List<Trip>>();
		
		for(List<String> fileLine : fileLines) {
			if (fileLine.get(0).equalsIgnoreCase("driver")) {
				Driver newEntry = new Driver(fileLine.get(0));
				if (!driverData.contains(newEntry)) {
					driverData.add(newEntry);
				}
			} else if (fileLine.get(0).equalsIgnoreCase("trip")) {
				Driver newEntry = new Driver(fileLine.get(1));
				if (!driverData.contains(newEntry)) {
					driverData.add(newEntry);
					driverData.get
				}
			}
//			if (driverData.contains(new Driver(fileLine.)) {
//				
//			}
		}
		
		return null;
	}
}
