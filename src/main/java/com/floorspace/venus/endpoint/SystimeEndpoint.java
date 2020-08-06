package com.floorspace.venus.endpoint;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.floorspace.venus.manager.FloorSpacesAdPoolManager;
import com.floorspace.venus.pojo.AdPoolPayload;

@Component
@Path("systime")
public class SystimeEndpoint {
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public Response getSystime() {
		int now = (int)(System.currentTimeMillis()/1000);
		return Response.ok(now).build();
	}

}
