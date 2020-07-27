package com.floorspace.venus.endpoint;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.floorspace.venus.manager.UserInfoManager;
import com.floorspace.venus.pojo.UserCreationPayload;
import com.floorspace.venus.pojo.InfoCreationResponse;
import com.floorspace.venus.pojo.PIIUserInfo;


@Component
@Path("v1.0/meta")
public class InfoEndpoint {

	private UserInfoManager infoManager;
	
	@Autowired
	 public InfoEndpoint(UserInfoManager infoManager) {
		 this.infoManager = infoManager;
	 }
		
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userID}")
	public Response getUserInfo(@PathParam("userID") UUID userID) {
		PIIUserInfo userInfo = infoManager.getUserInfo(userID);
		return Response.ok(userInfo).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public Response addUserDetails(UserCreationPayload payload) {
		InfoCreationResponse resp = infoManager.createUser(payload);
		if(resp.isUserCreated()) {
			PIIUserInfo userInfo = infoManager.getUserInfo(resp.getUuid());
			return Response.ok(userInfo).build();
		}
		return Response.notModified().build();
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public Response updateUserDetails(UserCreationPayload payload) {
		infoManager.updateUser(payload);
		PIIUserInfo userInfo = infoManager.getUserInfo(payload.getUserID());
		return Response.ok(userInfo).build();
	}
	
	
	

	
	
	
}
