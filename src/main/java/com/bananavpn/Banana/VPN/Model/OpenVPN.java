package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenVPN {
	@JsonProperty("openvpn") 
    public String getopenvpn() { 
		 return this.openvpn; } 
    public void setopenvpn(String openvpn) { 
		 this.openvpn = openvpn; } 
    String openvpn;
    @JsonProperty("regions") 
    public Region getregions() { 
		 return this.regions; } 
    public void setregions(Region regions) { 
		 this.regions = regions; } 
    Region regions;
}
