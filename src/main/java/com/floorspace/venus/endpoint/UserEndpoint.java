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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.floorspace.venus.manager.UserManager;
import com.floorspace.venus.pojo.UserInfo;
import com.floorspace.venus.pojo.InfoCreationResponse;
import com.floorspace.venus.pojo.PIIUserInfo;


@Component
@Path("v1.0/meta")
public class UserEndpoint {

	private UserManager userManager;
	
	@Autowired
	 public UserEndpoint(UserManager infoManager) {
		 this.userManager = infoManager;
	 }
		
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userID}")
	public Response getUserInfo(@PathParam("userID") UUID userID, @QueryParam("hidePII") boolean hidePII) {
		UserInfo userInfo = userManager.getUserInfo(userID, hidePII);
		return Response.ok(userInfo).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public Response addUserDetails(UserInfo payload) {
		InfoCreationResponse resp = userManager.createUser(payload);
		if(resp.isUserCreated()) {
			UserInfo userInfo = userManager.getUserInfo(resp.getUuid(), true);
			return Response.ok(userInfo).build();
		}
		return Response.notModified().build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public Response updateUserDetails(UserInfo payload) {
		userManager.updateUser(payload);
		UserInfo userInfo = userManager.getUserInfo(payload.getPiiUserInfo().getUserID(), true);
		return Response.ok(userInfo).build();
	}
	
}
