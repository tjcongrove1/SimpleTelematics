package com.root.telematics.model;

import java.util.ArrayList;
import java.util.List;

public class Driver {
	private String driverName;
	private List<Trip> driverTrips;

	public Driver(String name) {
		this.setDriverName(name);
	}

	/**
	 * This method calculates the average speed across all child trips associated
	 * with a Driver entity based on the precalculated average speed of each trip
	 * and the number of trips taken
	 * 
	 * @return the average speed for all trips taken
	 */
	public int getAverageSpeedForAllTrips() {
		if (null != this.driverTrips) {
			int totalSpeedValues = 0;

			for (Trip trip : this.driverTrips) {
				totalSpeedValues += trip.getAverageSpeed();
			}

			return totalSpeedValues / this.driverTrips.size();
		} else {
			return 0;
		}
	}

	/**
	 * This method calculates the total number of miles driven across all child
	 * trips associated with a Driver entity
	 * 
	 * @return the total number of miles driven for all trips taken
	 */
	public int getTotalMilesForAllTrips() {
		if (null != this.driverTrips) {
			int totalMiles = 0;

			for (Trip trip : this.driverTrips) {
				totalMiles += Math.round(trip.getMilesDriven());
			}

			return totalMiles;
		} else {
			return 0;
		}
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName
	 *            the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the driverTrips
	 */
	public List<Trip> getDriverTrips() {
		return driverTrips;
	}

	/**
	 * @param driverTrips
	 *            the driverTrips to set
	 */
	public void setDriverTrips(List<Trip> driverTrips) {
		this.driverTrips = driverTrips;
	}

	/**
	 * @param driverTrip
	 *            the driverTrip to add to the list
	 * 
	 *            If there are no trips present in the list, initialize it first
	 */
	public void addDriverTrip(Trip driverTrip) {
		if (null != this.driverTrips) {
			this.driverTrips.add(driverTrip);
		} else {
			this.setDriverTrips(new ArrayList<Trip>());
			this.driverTrips.add(driverTrip);
		}
	}

	/**
	 * Override the default "equals" method on this object so that it only compares
	 * driver data and ignores trips that can be added to or removed from the object
	 * model during processing.
	 * 
	 * This method can be modified to include more driver-identifying fields as more
	 * details are made available to ensure complete driver matching.
	 */
	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Driver) {
			return ((Driver) object).getDriverName().equalsIgnoreCase(this.getDriverName());
		} else {
			return false;
		}
	}
}
