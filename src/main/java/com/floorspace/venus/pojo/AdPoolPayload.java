package com.floorspace.venus.pojo;

import java.io.IOException;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class AdPoolPayload{

	private CommonAdPoolDetails commonDetails;
	private HouseConstructionAdPoolDetails houseconstructionAdDetails;
	private StandaloneBuildingConstructionAdPoolDetails standaloneBuildingAdDetails;
	private ApartmentConstructionAdPoolDetails apartmentConstructionAdDetails;
	
	public AdPoolPayload() {
		//Empty for object mapper pojo detection
	}
	public AdPoolPayload(CommonAdPoolDetails commonDetails, HouseConstructionAdPoolDetails houseconstructionAdDetails,
			StandaloneBuildingConstructionAdPoolDetails standaloneBuildingAdDetails, ApartmentConstructionAdPoolDetails apartmentConstructionAdDetails) {
		this.commonDetails = commonDetails;
		this.houseconstructionAdDetails = houseconstructionAdDetails;
		this.standaloneBuildingAdDetails = standaloneBuildingAdDetails;
		this.apartmentConstructionAdDetails = apartmentConstructionAdDetails;
	}

	public CommonAdPoolDetails getCommonDetails() {
		return commonDetails;
	}

	public HouseConstructionAdPoolDetails getHouseconstructionAdDetails() {
		return houseconstructionAdDetails;
	}

	public StandaloneBuildingConstructionAdPoolDetails getStandaloneBuildingAdDetails() {
		return standaloneBuildingAdDetails;
	}
	public ApartmentConstructionAdPoolDetails getApartmentConstructionAdDetails() {
		return apartmentConstructionAdDetails;
	}
	
//	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
//		
//		CommonAdPoolDetails commonDetails = new CommonAdPoolDetails(UUID.randomUUID(), UUID.randomUUID(), PropertyType.RESIDENTIAL, 
//				FloorSpaceConstructionType.FLOOR_HOUSE_CONSTRUCTION, City.AMARAVATHI, new LatLon(100.25, -196.87),
//				"103, shanthanagar, 560103", "Good place in city. Near to metro", 3500000, 1);
//				
//		HouseConstructionAdPoolDetails houseconstructionAdDetails = new HouseConstructionAdPoolDetails(1, 1100, 11, 10, 2);
//		
//		AdPoolPayload payload = new AdPoolPayload(commonDetails, houseconstructionAdDetails, null);
//				
//				ObjectMapper mapper = new ObjectMapper();
//		String output = mapper.writeValueAsString(payload);
//		System.out.println(output);
//		
//	}
}
