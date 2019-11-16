package com.football.demo.model;

public class Country {

	private String countryId;
	private String countryName;
	
	public Country() {
		// TODO Auto-generated constructor stub
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountry_id(String country_id) {
		this.countryId = country_id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountry_name(String country_name) {
		this.countryName = country_name;
	}

	@Override
	public String toString() {
		return "Country [country_id=" + countryId + ", country_name=" + countryName + "]";
	}
	
	
}
