package com.floorspace.venus.pojo;

import java.util.UUID;

public class ApartmentConstructionAdPoolDetails{

	private int numOfApartmentFloors;
	private int numOfFlatsPerFloor;
	//Always measured in sqft for accuracy
	private double apartmentArea;
	private double lengthOfApartmentPlot;
	private double breadthOfApartmentPlot;
	private int numOfAllowedFloorsForApartment;
	
	public ApartmentConstructionAdPoolDetails() {
	}

	public ApartmentConstructionAdPoolDetails(int numOfApartmentFloors, int numOfFlatsPerFloor, double apartmentArea,
			double lengthOfApartmentPlot, double breadthOfApartmentPlot, int numOfAllowedFloorsForApartment) {
		this.numOfApartmentFloors = numOfApartmentFloors;
		this.numOfFlatsPerFloor = numOfFlatsPerFloor;
		this.apartmentArea = apartmentArea;
		this.lengthOfApartmentPlot = lengthOfApartmentPlot;
		this.breadthOfApartmentPlot = breadthOfApartmentPlot;
		this.numOfAllowedFloorsForApartment = numOfAllowedFloorsForApartment;
	}

	public int getNumOfApartmentFloors() {
		return numOfApartmentFloors;
	}

	public int getNumOfFlatsPerFloor() {
		return numOfFlatsPerFloor;
	}

	public double getApartmentArea() {
		return apartmentArea;
	}

	public double getLengthOfApartmentPlot() {
		return lengthOfApartmentPlot;
	}

	public double getBreadthOfApartmentPlot() {
		return breadthOfApartmentPlot;
	}

	public int getNumOfAllowedFloorsForApartment() {
		return numOfAllowedFloorsForApartment;
	}

}
