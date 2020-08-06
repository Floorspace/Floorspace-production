package com.floorspace.venus.pojo;

import java.util.UUID;

public class LenderBuyerMapping {

	private UUID poolID;
	private UUID lenderID;
	private UUID buyerID;
	private boolean requested;
	private boolean accepted;
	
	public LenderBuyerMapping(UUID poolID, UUID lenderID, UUID buyerID, boolean requested, boolean accepted) {
		this.poolID = poolID;
		this.lenderID = lenderID;
		this.buyerID = buyerID;
		this.requested = requested;
		this.accepted = accepted;
	}
	
	public UUID getPoolID() {
		return poolID;
	}
	public UUID getLenderID() {
		return lenderID;
	}
	public UUID getBuyerID() {
		return buyerID;
	}
	public boolean isRequested() {
		return requested;
	}
	public boolean isAccepted() {
		return accepted;
	}
	
}
