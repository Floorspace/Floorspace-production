package com.floorspace.venus.pojo;

import java.util.List;

import com.easypay.dao.user.pojo.Gender;
import com.floorspace.venus.enums.Caste;
import com.floorspace.venus.enums.Language;
import com.floorspace.venus.enums.Religion;
import com.floorspace.venus.enums.State;

public class VisibleUserInfo {

	private Caste caste;
	private Religion religion;
	private Gender gender;
	private Integer age;
	private List<Language> knownLanguages;
	private State nativeState;
	
	//TO-DO
	// Add education of the investor and past experiences in real estsate
	
	public VisibleUserInfo() {
	}
	
	
	public VisibleUserInfo(Caste caste, Religion religion, Gender gender, Integer age, List<Language> knownLanguages,
			State nativeState) {
		this.caste = caste;
		this.religion = religion;
		this.gender = gender;
		this.age = age;
		this.knownLanguages = knownLanguages;
		this.nativeState = nativeState;
	}
	
	public Caste getCaste() {
		return caste;
	}
	public Religion getReligion() {
		return religion;
	}
	public Gender getGender() {
		return gender;
	}
	public Integer getAge() {
		return age;
	}
	public List<Language> getKnownLanguages() {
		return knownLanguages;
	}
	public State getNativeState() {
		return nativeState;
	}
}
