package com.floorspace.venus.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.easypay.dao.user.pojo.Gender;
import com.floorspace.venus.dao.FloorspacesAdPoolDao;
import com.floorspace.venus.dao.UserDao;
import com.floorspace.venus.enums.Caste;
import com.floorspace.venus.enums.City;
import com.floorspace.venus.enums.FloorSpaceConstructionType;
import com.floorspace.venus.enums.Language;
import com.floorspace.venus.enums.PropertyType;
import com.floorspace.venus.enums.Religion;
import com.floorspace.venus.enums.State;
import com.floorspace.venus.misc.KnownException;
import com.floorspace.venus.pojo.AdPoolPayload;
import com.floorspace.venus.pojo.CommonAdPoolDetails;
import com.floorspace.venus.pojo.LenderBuyerMapping;
import com.floorspace.venus.pojo.UserInfo;

/**
 * 
 * @author tarun_itachi
 *
 * This class manages the ads that lenders post and what buyers see 
 */
public class FloorSpacesAdPoolManager {
	
	private FloorspacesAdPoolDao floorspacesAdPoolDao;
	
	private UserManager userManager;
	
	public FloorSpacesAdPoolManager(FloorspacesAdPoolDao floorspacesAdPoolDao, UserManager userManager) {
		this.floorspacesAdPoolDao = floorspacesAdPoolDao;
		this.userManager = userManager;
	}
	
	//From lender dashboard
	public void postFloorSpaceAd(AdPoolPayload adPoolPayload) {
		try {
		floorspacesAdPoolDao.createFloorSpaceAd(adPoolPayload);
		}
		catch(SQLException se) {
			se.printStackTrace();
			throw new KnownException(400, "SQL exception occurred");
		}
	}
	
	public  List<AdPoolPayload>   getAllAdsPostedByALender(UUID lenderID){
		return floorspacesAdPoolDao.getAllAdsPostedByLender(lenderID);
	}

	public AdPoolPayload getAdBasedOnID(UUID poolID) {
		return floorspacesAdPoolDao.getAdBasedOnID(poolID);
	}


	public List<AdPoolPayload> getfloorSpacesBasedOnFilters(City city, PropertyType propertyType,
			FloorSpaceConstructionType constructionType, Integer maxPrice, Integer minPrice, String areaName,
			Integer radius, Caste caste, Religion religion, Gender gender, Integer minAge, Integer maxAge, List<Language> filterLanguages,
			State nativeState) {
		if(maxPrice == null) {
			maxPrice = 999999999;
		}
		if(minPrice == null) {
			minPrice = 0;
		}
		if(radius == null) {
			radius = 5;
		}
		List<AdPoolPayload> adspaces =  floorspacesAdPoolDao.getFiltersBasedFloorspaces(city, propertyType, constructionType, maxPrice, minPrice);

		return filterAdSpacesBasedOnPersonalFilters(adspaces, caste, religion, gender, minAge, maxAge, filterLanguages, nativeState);
	}
	
	private List<AdPoolPayload> filterAdSpacesBasedOnPersonalFilters(List<AdPoolPayload> adspaces, Caste caste, Religion religion, 
					Gender gender, Integer minAge, Integer maxAge, List<Language> filterLanguages, State nativeState) {
		
		List<AdPoolPayload> filteredAdSpaces = new ArrayList<AdPoolPayload>();
		
		for(AdPoolPayload payload: adspaces) {
			if(!rejectPayload(payload, caste, religion, gender, minAge, maxAge, filterLanguages, nativeState)) {
				filteredAdSpaces.add(payload);
			}
		}
		
		return filteredAdSpaces;
	}

	private boolean rejectPayload(AdPoolPayload payload, Caste caste, Religion religion, 
			Gender gender, Integer minAge, Integer maxAge, List<Language> filterLanguages, State nativeState) {
		
		UUID lenderID = payload.getCommonDetails().getLenderID();
		
		UserInfo user = userManager.getUserInfo(lenderID, false);
		
		//return false if anything doesn't match with filters
		
		
		return false;
	}

	public void sendRequestToLenderFromBuyer(UUID poolID, UUID lenderID, UUID buyerID) {
		
		floorspacesAdPoolDao.sendRequestToLenderFromBuyer(poolID,  lenderID,  buyerID);
	}
	
	public void acceptRequestFromLenderToBuyer(UUID poolID, UUID lenderID, UUID buyerID) {
			
			floorspacesAdPoolDao.acceptRequestFromLenderToBuyer(poolID,  lenderID,  buyerID);
		}

	//TO-DO Return Ad pool payload  along with all the members in the pool from the mapping
	public List<AdPoolPayload> getConnectedPoolsForALender(UUID lenderID) {
		List<LenderBuyerMapping> lenderMappings =  floorspacesAdPoolDao.getConnectedPoolsForALender(lenderID);
		
		//For now returning all ads
		return getAllAdsPostedByALender(lenderID);
	}

	//TO-DO Return Ad pool payload  along with all the members in the pool from the mapping
	public List<AdPoolPayload> getConnectedPoolsForABuyer(UUID buyerID) {
		List<LenderBuyerMapping> buyerMappings =  floorspacesAdPoolDao.getConnectedPoolsForABuyer(buyerID);
		List<AdPoolPayload> connectedPools = new ArrayList<AdPoolPayload>();
		for(LenderBuyerMapping mapping: buyerMappings) {
			UUID poolID = mapping.getPoolID();
			connectedPools.add(floorspacesAdPoolDao.getAdBasedOnID(poolID));
		}
		return connectedPools;
	}
		
}
