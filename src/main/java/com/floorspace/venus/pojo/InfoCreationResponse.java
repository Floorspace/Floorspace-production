package com.floorspace.venus.pojo;

import java.util.UUID;

public class InfoCreationResponse {
	
	private UUID uuid;
	private boolean userCreated;
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public InfoCreationResponse(UUID uuid, boolean userCreated) {
		this.uuid = uuid;
		this.userCreated = userCreated;
	}
	
	public boolean isUserCreated() {
		return userCreated;
	}
	public void setUserCreated(boolean userCreated) {
		this.userCreated = userCreated;
	}
	
	
}
