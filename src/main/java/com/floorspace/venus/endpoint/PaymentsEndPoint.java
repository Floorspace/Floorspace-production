package com.floorspace.venus.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.floorspace.venus.manager.PaymentsManager;
import com.floorspace.venus.pojo.PaymentInfo;

@Component
@Path("v1.0/payment-methods")
public class PaymentsEndPoint {
	
private PaymentsManager manager;
	
	@Autowired
	 public PaymentsEndPoint(PaymentsManager manager) {
		 this.manager = manager;
	 }
		
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPayments() {
			List<PaymentInfo> allPayments = manager.getAllPayments();
		return Response.ok(allPayments).build();
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNewPaymentMethods(List<PaymentInfo> newPaymentMethods) {
		manager.addPayments(newPaymentMethods);
		List<PaymentInfo> allPaymentMethods = manager.getAllPayments();
		return Response.ok(allPaymentMethods).build();
		}
}
