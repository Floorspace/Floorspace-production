package com.floorspace.venus.pojo;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;

import com.easypay.dao.user.pojo.Gender;
import com.floorspace.venus.enums.Caste;
import com.floorspace.venus.enums.Language;
import com.floorspace.venus.enums.Religion;
import com.floorspace.venus.enums.State;
import com.floorspace.venus.misc.SharedMemoryExpensiveConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

	private PIIUserInfo piiUserInfo;
	private VisibleUserInfo visibleUserInfo;
	
	public UserInfo() {
	}
	
	public UserInfo(PIIUserInfo piiUserInfo, VisibleUserInfo visibleUserInfo) {
		this.piiUserInfo = piiUserInfo;
		this.visibleUserInfo = visibleUserInfo;
	}
	public PIIUserInfo getPiiUserInfo() {
		return piiUserInfo;
	}
	public VisibleUserInfo getVisibleUserInfo() {
		return visibleUserInfo;
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
			UserInfo info = new UserInfo(new PIIUserInfo(UUID.randomUUID(), "Tarun kumar", 1596212151, "6281801981", "tellatarun96@gmail.com",
					"ATMPT8619H", "123456789123"), new VisibleUserInfo(Caste.Any, Religion.HINDU, Gender.MALE, 24, Arrays.asList(Language.ENGLISH, 
							Language.TELUGU), State.ANDHRA_PRADESH));
			
			String op = SharedMemoryExpensiveConstants.objectMapper.writeValueAsString(info);
			
			System.out.println(op);
	}
}
