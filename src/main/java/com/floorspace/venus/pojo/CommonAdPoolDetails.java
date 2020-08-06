package com.floorspace.venus.pojo;

import java.util.UUID;

import com.floorspace.venus.enums.City;
import com.floorspace.venus.enums.FloorSpaceConstructionType;
import com.floorspace.venus.enums.LatLon;
import com.floorspace.venus.enums.PropertyType;

public class CommonAdPoolDetails {

	private UUID poolID;
	private UUID lenderID;
	private PropertyType propertyType;
	private FloorSpaceConstructionType constructionType;
	private City city;
	private LatLon latlon;
	private String address;
	private String description;
	private int propertyCost;
	private int numOfPoolMembers;
	
	public CommonAdPoolDetails() {
		
	}
	
	public CommonAdPoolDetails(UUID poolID, UUID lenderID, PropertyType propertyType, FloorSpaceConstructionType constructionType,
			City city, LatLon latlon, String address, String description, int propertyCost, int numOfPoolMembers) {
		this.poolID = poolID;
		this.lenderID = lenderID;
		this.propertyType = propertyType;
		this.constructionType = constructionType;
		this.city = city;
		this.latlon = latlon;
		this.address = address;
		this.description = description;
		this.propertyCost = propertyCost;
		this.numOfPoolMembers = numOfPoolMembers;
	}
	public UUID getLenderID() {
		return lenderID;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public FloorSpaceConstructionType getConstructionType() {
		return constructionType;
	}
	public City getCity() {
		return city;
	}
	public LatLon getLatlon() {
		return latlon;
	}
	public String getAddress() {
		return address;
	}
	public String getDescription() {
		return description;
	}
	public int getPropertyCost() {
		return propertyCost;
	}
	public int getNumOfPoolMembers() {
		return numOfPoolMembers;
	}
	public UUID getPoolID() {
		return poolID;
	}

	
	
	
	
	
	
//Property photos i.e s3 path Host in cloudfront	
//Planned Amenities	
//	private PropertyOwnershipType ownerShipType;
//	private List<String> keyPropertyHighlights;
//	private List<PropertyAmenity> plannedAmenities;	
}
