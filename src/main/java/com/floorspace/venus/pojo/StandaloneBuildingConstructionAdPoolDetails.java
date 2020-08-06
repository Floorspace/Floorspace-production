package com.floorspace.venus.pojo;

import java.util.UUID;

public class StandaloneBuildingConstructionAdPoolDetails  {

	private int numOfFloors;
	//Always measured in sqft for accuracy
	private double plotArea;
	private double lengthOfPlot;
	private double breadthOfPlot;
	private int numOfAllowedFloorsForConstruction;
	
	public StandaloneBuildingConstructionAdPoolDetails() {
		
	}
	
	public StandaloneBuildingConstructionAdPoolDetails(int numOfFloors, double plotArea, double lengthOfPlot,
			double breadthOfPlot, int numOfAllowedFloorsForConstruction) {
		this.numOfFloors = numOfFloors;
		this.plotArea = plotArea;
		this.lengthOfPlot = lengthOfPlot;
		this.breadthOfPlot = breadthOfPlot;
		this.numOfAllowedFloorsForConstruction = numOfAllowedFloorsForConstruction;
	}
	public int getNumOfFloors() {
		return numOfFloors;
	}
	public double getPlotArea() {
		return plotArea;
	}
	public double getLengthOfPlot() {
		return lengthOfPlot;
	}
	public double getBreadthOfPlot() {
		return breadthOfPlot;
	}
	public int getNumOfAllowedFloorsForConstruction() {
		return numOfAllowedFloorsForConstruction;
	}
}
