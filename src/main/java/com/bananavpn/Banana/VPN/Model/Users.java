package com.bananavpn.Banana.VPN.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "users")
public class Users {
	@Id
    private String id;	  
    private String firstname;
	private String lastname;
	private String email;
	private String password;
	private Boolean isgooglesignin;
	public long createtimestamp=0;
	public long updatetimestamp=0;
	private Boolean isdeleted=false;
	private Boolean isactive=false;
	private Boolean issubcribe=false;
	  
	public void User(String firstname,String lastname, String email,String password,
			  Boolean isgooglesignin,long createtimestamp,long updatetimestamp)
	{
	    this.firstname = firstname;
	    this.lastname = lastname;
	    this.email = email;
	    this.password = password;
	    this.isgooglesignin = isgooglesignin;
	    this.isdeleted = false;
	    this.isactive = false;
	    this.issubcribe=false;
	    this.createtimestamp=createtimestamp;
	    this.updatetimestamp=updatetimestamp;
	 }
	
	public String getId() {
	    return id;
	}
	
	public void setId(String id) {
	    this.id = id;
	}
	
	public String getFirstname() {
	 return firstname;
	}
	
	public void setFirstname(String firstname) {
	   this.firstname = firstname;
	}
	
	public String getLastname() {
	   return lastname;
	}
	
	public void setLastname(String lastname) {
	   this.lastname = lastname;
	}
	public String getEmail() {
	    return email;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}
	
	@JsonIgnore
	public String getPassword() {
	    return password;
	}

	@JsonProperty
	public void setPassword(String password) {
	    this.password = password;
	}
	
	public Boolean getisgooglesignin() {
	    return isgooglesignin;
	}
	
	public void setisgooglesignin(Boolean isgooglesignin) {
	    this.isgooglesignin = isgooglesignin;
	}
	
	public long getcreatetimestamp() {
	    return createtimestamp;
	}
	
	public void setcreatetimestamp(long createtimestamp) {
	    this.createtimestamp = createtimestamp;
	}
	
	public long getupdatetimestamp() {
	    return updatetimestamp;
	}
	
	public void setupdatetimestamp(long updatetimestamp) {
	    this.updatetimestamp = updatetimestamp;
	}
	
	public Boolean getIsdelete() {
	    return isdeleted;
	}
	
	public void setIsdelete(Boolean isdeleted) {
	    this.isdeleted = isdeleted;
	}
	
	public Boolean getisactive() {
	    return isactive;
	}
	
	public void setisactive(Boolean isactive) {
	    this.isactive = isactive;
	}
	
	public Boolean getissubcribe() {
	    return issubcribe;
	}
	
	public void setissubcribe(Boolean issubcribe) {
	    this.issubcribe = issubcribe;
	}
}
