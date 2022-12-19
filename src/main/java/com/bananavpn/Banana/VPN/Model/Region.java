package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Region {
	@JsonProperty("id") 
    public String getId() { 
		 return this.id; } 
    public void setId(String id) { 
		 this.id = id; } 
    String id;
    @JsonProperty("continent") 
    public String getContinent() { 
		 return this.continent; } 
    public void setContinent(String continent) { 
		 this.continent = continent; } 
    String continent;
    @JsonProperty("country") 
    public String getCountry() { 
		 return this.country; } 
    public void setCountry(String country) { 
		 this.country = country; } 
    String country;
    @JsonProperty("countryIso") 
    public String getCountryIso() { 
		 return this.countryIso; } 
    public void setCountryIso(String countryIso) { 
		 this.countryIso = countryIso; } 
    String countryIso;
    @JsonProperty("regionName") 
    public String getRegionName() { 
		 return this.regionName; } 
    public void setRegionName(String regionName) { 
		 this.regionName = regionName; } 
    String regionName;
}
