package com.root.telematics.model;

import java.time.Duration;
import java.time.LocalTime;

public class Trip {
	private LocalTime startTime;
	private LocalTime endTime;
	private Double milesDriven;
	private int averageSpeed;

	public Trip(LocalTime start, LocalTime end, double miles) {
		this.startTime = start;
		this.endTime = end;
		this.milesDriven = miles;
		this.averageSpeed = calculateAverageSpeed();
	}

	private int calculateAverageSpeed() {
//		debugging statement
//		
//		System.out.println(startTime + " " + endTime + " " + milesDriven + " "
//				+ (int) Math.round(milesDriven / ((double) Duration.between(startTime, endTime).getSeconds()) * 3600));
		return (int) Math.round(milesDriven / ((double) Duration.between(startTime, endTime).getSeconds()) * 3600);
	}

	/**
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
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
	 * @param endTime
	 *            the endTime to set
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
	 * @param milesDriven
	 *            the milesDriven to set
	 */
	public void setMilesDriven(Double milesDriven) {
		this.milesDriven = milesDriven;
	}

	/**
	 * @return the averageSpeed
	 */
	public int getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * @param averageSpeed
	 *            the averageSpeed to set
	 */
	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}
}
