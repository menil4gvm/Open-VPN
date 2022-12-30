package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionDetails {
	@JsonProperty("userid") 
    public String getuserid() { 
		 return this.userid; } 
    public void setuserid(String userid) { 
		 this.userid = userid; } 
    String userid;
    
    @JsonProperty("customerid") 
    public String getcustomerid() { 
		 return this.customerid; } 
    public void setcustomerid(String customerid) { 
		 this.customerid = customerid; } 
    String customerid;
    
    @JsonProperty("interval") 
    public String getinterval() { 
		 return this.interval; } 
    public void setinterval(String interval) { 
		 this.interval = interval; } 
    String interval;
    
    @JsonProperty("description") 
    public String getdescription() { 
		 return this.description; } 
    public void setdescription(String description) { 
		 this.description = description; } 
    String description;
    
    @JsonProperty("createdtimestamps") 
    public long getcreatedtimestamps() { 
		 return this.createdtimestamps; } 
    public void setcreatedtimestamps(long createdtimestamps) { 
		 this.createdtimestamps = createdtimestamps; } 
    long createdtimestamps=System.currentTimeMillis();
}
