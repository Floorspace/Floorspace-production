package com.floorspace.venus.endpoint;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easypay.dao.user.pojo.Gender;
import com.floorspace.venus.enums.Caste;
import com.floorspace.venus.enums.City;
import com.floorspace.venus.enums.FloorSpaceConstructionType;
import com.floorspace.venus.enums.Language;
import com.floorspace.venus.enums.PropertyType;
import com.floorspace.venus.enums.Religion;
import com.floorspace.venus.enums.State;
import com.floorspace.venus.manager.FloorSpacesAdPoolManager;
import com.floorspace.venus.manager.UserManager;
import com.floorspace.venus.misc.KnownException;
import com.floorspace.venus.pojo.CommonAdPoolDetails;
import com.floorspace.venus.pojo.AdPoolPayload;
import com.floorspace.venus.pojo.InfoCreationResponse;
import com.floorspace.venus.pojo.PIIUserInfo;

@Component
@Path("v1.0/ad-info")
public class FloorspacePoolEndpoint {

	private FloorSpacesAdPoolManager adPoolManager;
	
	@Autowired
	 public FloorspacePoolEndpoint(FloorSpacesAdPoolManager adPoolManager) {
		 this.adPoolManager = adPoolManager;
	 }
		
	//Lender side
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/lender/{lenderID}")
	public Response getAllAdsPostedByALender(@PathParam("lenderID") UUID lenderID) {
		List<AdPoolPayload> allAds = adPoolManager.getAllAdsPostedByALender(lenderID);
		return Response.ok(allAds).build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pool/{poolID}")
	public Response getAdBasedOnID(@PathParam("poolID") UUID poolID) {
		AdPoolPayload ad = adPoolManager.getAdBasedOnID(poolID);
		return Response.ok(ad).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create-or-update-ad")
	public Response createOrUpdateFloorspaceAd(AdPoolPayload adPoolPayload) {
		try {
			adPoolManager.postFloorSpaceAd(adPoolPayload);
			return Response.ok().build();
		}
		catch(KnownException e) {
			return Response.notModified().build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accept-buyer/lender/{lenderID}/buyer/{buyerID}/pool/{poolID}")
	public Response acceptBuyer(@PathParam("poolID") UUID poolID, @PathParam("lenderID") UUID lenderID, @PathParam("buyerID") UUID buyerID) {
		adPoolManager.acceptRequestFromLenderToBuyer( poolID, lenderID, buyerID);
		return Response.ok("Request accepted. Now you can see each other's details..").build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/connectedPools/lender/{lenderID}")
	public Response getConnectedPoolsForALender(@PathParam("lenderID") UUID lenderID) {
		List<AdPoolPayload> connectedPools = adPoolManager.getConnectedPoolsForALender(lenderID);
		return Response.ok(connectedPools).build();
	}
	
	
	//Buyer side
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all-ads-with-filters")
	public Response getFloorspaces(@DefaultValue("BANGALORE") @QueryParam("city") City city, 
			@DefaultValue("RESIDENTIAL") @QueryParam("propertyType") PropertyType propertyType,
			@DefaultValue("FLOOR_HOUSE_CONSTRUCTION") @QueryParam("constructionType") FloorSpaceConstructionType constructionType,
			@QueryParam("maxPrice") Integer maxPrice, @QueryParam("minPrice") Integer minPrice,  @QueryParam("areaName") String areaName, 
			@QueryParam("radius") Integer radius, @QueryParam("caste") Caste caste, @QueryParam("religion") Religion religion, 
			@QueryParam("gender") Gender gender, @QueryParam("minAge") Integer minAge, @QueryParam("maxAge") Integer maxAge, 
			@QueryParam("filterLanguages") List<Language> filterLanguages, @QueryParam("nativeState") State nativeState) {
		
		List<AdPoolPayload> filterBasedAds = adPoolManager.getfloorSpacesBasedOnFilters(city, propertyType, constructionType, maxPrice, minPrice, areaName, radius,
								caste, religion, gender, minAge, maxAge,filterLanguages, nativeState );
		return Response.ok(filterBasedAds).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/send-request/lender/{lenderID}/buyer/{buyerID}/pool/{poolID}")
	public Response connectToLender(@PathParam("poolID") UUID poolID, @PathParam("lenderID") UUID lenderID, @PathParam("buyerID") UUID buyerID) {
		adPoolManager.sendRequestToLenderFromBuyer( poolID, lenderID, buyerID);
		return Response.ok("Request sent. Wait for lender to connect..").build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/connectedPools/buyer/{buyerID}")
	public Response getConnectedPoolsForABuyer(@PathParam("buyerID") UUID buyerID) {
		List<AdPoolPayload> connectedPools = adPoolManager.getConnectedPoolsForABuyer(buyerID);
		return Response.ok(connectedPools).build();
	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/save-ad")
//	public Response saveFloorspaceAd(Integer adID, @PathParam("buyerID") UUID buyerID) {
//		
//		//Map buyerID to adID in a seperate table
//		InfoCreationResponse resp = infoManager.createUser(payload);
//		if(resp.isUserCreated()) {
//			PIIUserInfo userInfo = infoManager.getUserInfo(resp.getUuid());
//			return Response.ok(userInfo).build();
//		}
//		return Response.notModified().build();
//	}
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/lock-pool")
//	public Response lockPool(@PathParam("poolID") Integer poolID, @PathParam("lenderID") UUID lenderID) {
//		
//		//in another table,create mapping of poolID, buyerID, lenderID
//		
//		InfoCreationResponse resp = infoManager.createUser(payload);
//		if(resp.isUserCreated()) {
//			PIIUserInfo userInfo = infoManager.getUserInfo(resp.getUuid());
//			return Response.ok(userInfo).build();
//		}
//		return Response.notModified().build();
//	}
	
}
