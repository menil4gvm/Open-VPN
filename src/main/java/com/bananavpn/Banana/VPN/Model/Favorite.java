package com.bananavpn.Banana.VPN.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "favorite")
public class Favorite {
	@JsonProperty("userid") 
    public String getuserid() { 
		 return this.userid; } 
    public void setuserid(String userid) { 
		 this.userid = userid; } 
    String userid;
    @JsonProperty("serverid") 
    public String getserverid() { 
		 return this.serverid; } 
    public void setserverid(String serverid) { 
		 this.serverid = serverid; } 
    String serverid;
    @JsonProperty("isfavorite") 
    public Boolean getisfavorite() { 
		 return this.isfavorite; } 
    public void setisfavorite(Boolean isfavorite) { 
		 this.isfavorite = isfavorite; } 
    Boolean isfavorite=false;
}
