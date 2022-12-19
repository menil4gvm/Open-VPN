package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {
	@JsonProperty("access_token") 
    public String getAccess_token() { 
		 return this.access_token; } 
    public void setAccess_token(String access_token) { 
		 this.access_token = access_token; } 
    String access_token;
    @JsonProperty("token_type") 
    public String getToken_type() { 
		 return this.token_type; } 
    public void setToken_type(String token_type) { 
		 this.token_type = token_type; } 
    String token_type;
    @JsonProperty("expires_in") 
    public int getExpires_in() { 
		 return this.expires_in; } 
    public void setExpires_in(int expires_in) { 
		 this.expires_in = expires_in; } 
    int expires_in;
    @JsonProperty("scope") 
    public String getScope() { 
		 return this.scope; } 
    public void setScope(String scope) { 
		 this.scope = scope; } 
    String scope;
}
