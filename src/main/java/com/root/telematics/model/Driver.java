package com.root.telematics.model;

public class Driver {
	private String driverName;

	public Driver(String name) {
		this.setDriverName(name);
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
}
