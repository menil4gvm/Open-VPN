package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResult {
	@JsonProperty("paymentIntent") 
    public String getpaymentIntent() { 
		 return this.paymentIntent; } 
    public void setpaymentIntent(String paymentIntent) { 
		 this.paymentIntent = paymentIntent; } 
    String paymentIntent;
    
    @JsonProperty("ephemeralKey") 
    public String getephemeralKey() { 
		 return this.ephemeralKey; } 
    public void setephemeralKey(String ephemeralKey) { 
		 this.ephemeralKey = ephemeralKey; } 
    String ephemeralKey;
    
    @JsonProperty("customer") 
    public String getcustomer() { 
		 return this.customer; } 
    public void setcustomer(String customer) { 
		 this.customer = customer; } 
    String customer;
    
    @JsonProperty("publishableKey") 
    public String getpublishableKey() { 
		 return this.publishableKey; } 
    public void setpublishableKey(String publishableKey) { 
		 this.publishableKey = publishableKey; } 
    String publishableKey;
}
