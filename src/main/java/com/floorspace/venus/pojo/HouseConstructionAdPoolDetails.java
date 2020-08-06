package com.floorspace.venus.pojo;

import java.util.List;
import java.util.UUID;

public class HouseConstructionAdPoolDetails  {
	
	private int existingFloors;
	//Always measured in sqft for accuracy
	private double floorSpaceArea;
	private double lengthOfFloorSpace;
	private double breadthOfFloorSpace;
	private int numOfAllowedFloorsForConstruction;
	
	public HouseConstructionAdPoolDetails() {
		
	}
	
	
	public HouseConstructionAdPoolDetails(int existingFloors, double floorSpaceArea, double lengthOfFloorSpace, double breadthOfFloorSpace,
			int numOfAllowedFloorsForConstruction) {
		this.existingFloors = existingFloors;
		this.floorSpaceArea = floorSpaceArea;
		this.lengthOfFloorSpace = lengthOfFloorSpace;
		this.breadthOfFloorSpace = breadthOfFloorSpace;
		this.numOfAllowedFloorsForConstruction = numOfAllowedFloorsForConstruction;
	}
	public int getExistingFloors() {
		return existingFloors;
	}
	public double getFloorSpaceArea() {
		return floorSpaceArea;
	}
	public double getLengthOfFloorSpace() {
		return lengthOfFloorSpace;
	}
	public double getBreadthOfFloorSpace() {
		return breadthOfFloorSpace;
	}
	public int getNumOfAllowedFloorsForConstruction() {
		return numOfAllowedFloorsForConstruction;
	}


}
