package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenVPN {
	@JsonProperty("connectorid") 
    public String getconnectorid() { 
		 return this.connectorid; } 
    public void setconnectorid(String connectorid) { 
		 this.connectorid = connectorid; } 
    String connectorid;
    @JsonProperty("ipV4Address") 
    public String getipV4Address() { 
		 return this.ipV4Address; } 
    public void setipV4Address(String ipV4Address) { 
		 this.ipV4Address = ipV4Address; } 
    String ipV4Address;
    @JsonProperty("isfavorite") 
    public Boolean getisfavorite() { 
		 return this.isfavorite; } 
    public void setisfavorite(Boolean isfavorite) { 
		 this.isfavorite = isfavorite; } 
    Boolean isfavorite=false;
	@JsonProperty("openvpn") 
    public String getopenvpn() { 
		 return this.openvpn; } 
    public void setopenvpn(String openvpn) { 
		 this.openvpn = openvpn; } 
    String openvpn;
    @JsonProperty("region") 
    public Region getregion() { 
		 return this.region; } 
    public void setregion(Region region) { 
		 this.region = region; } 
    Region region;
}
