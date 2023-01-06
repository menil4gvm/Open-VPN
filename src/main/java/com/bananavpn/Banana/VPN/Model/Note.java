package com.bananavpn.Banana.VPN.Model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Note {	 
	 @JsonProperty("subject") 
	 public String getsubject() { 
	  return this.subject; } 
	 public void setsubject(String subject) { 
		 this.subject = subject; } 
	 String subject;
	 
	 @JsonProperty("content") 
	 public String getcontent() { 
	  return this.subject; } 
	 public void setcontent(String content) { 
		 this.content = content; } 
	 String content;
	 
	 @JsonProperty("data") 
	 public Map<String, String> getdata() { 
	  return this.data; } 
	 public void setdata(Map<String, String> data) { 
		 this.data = data; } 
	 Map<String, String> data;
	   
	 @JsonProperty("image") 
	 public String getimage() { 
	  return this.image; } 
	 public void setimage(String image) { 
		 this.image = image; } 
	 String image;
}
