package com.root.telematics.model;

import java.time.LocalTime;

public class Trip {
	private LocalTime startTime;
	private LocalTime endTime;
	private Double milesDriven;
	
	/**
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the milesDriven
	 */
	public Double getMilesDriven() {
		return milesDriven;
	}
	/**
	 * @param milesDriven the milesDriven to set
	 */
	public void setMilesDriven(Double milesDriven) {
		this.milesDriven = milesDriven;
	}
}
