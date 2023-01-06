package com.bananavpn.Banana.VPN.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "servers")
public class Servers {
	@Id
    private String id;	
	@JsonProperty("id") 
    public String getid() { 
		 return this.id; } 
    
    @JsonProperty("username") 
    public String getusername() { 
		 return this.username; } 
    public void setusername(String username) { 
		 this.username = username; } 
    String username;
    
    @JsonProperty("password") 
    public String getpassword () { 
		 return this.password; } 
    public void setpassword (String password) { 
		 this.password = password; } 
    String password;
    
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
