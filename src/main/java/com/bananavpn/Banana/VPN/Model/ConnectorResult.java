package com.bananavpn.Banana.VPN.Model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectorResult {
	@JsonProperty("getbody") 
    public List<Connector> getbody() { 
		 return this.getbody; } 
    public void setbody(List<Connector> getbody) { 
		 this.getbody = getbody; } 
    List<Connector> getbody;
}
