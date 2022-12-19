package com.bananavpn.Banana.VPN.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Connector {
	@JsonProperty("id") 
    public String getId() { 
		 return this.id; } 
    public void setId(String id) { 
		 this.id = id; } 
    String id;
    @JsonProperty("networkItemId") 
    public String getNetworkItemId() { 
		 return this.networkItemId; } 
    public void setNetworkItemId(String networkItemId) { 
		 this.networkItemId = networkItemId; } 
    String networkItemId;
    @JsonProperty("networkItemType") 
    public String getNetworkItemType() { 
		 return this.networkItemType; } 
    public void setNetworkItemType(String networkItemType) { 
		 this.networkItemType = networkItemType; } 
    String networkItemType;
    @JsonProperty("name") 
    public String getName() { 
		 return this.name; } 
    public void setName(String name) { 
		 this.name = name; } 
    String name;
    @JsonProperty("vpnRegionId") 
    public String getVpnRegionId() { 
		 return this.vpnRegionId; } 
    public void setVpnRegionId(String vpnRegionId) { 
		 this.vpnRegionId = vpnRegionId; } 
    String vpnRegionId;
    @JsonProperty("ipV4Address") 
    public String getIpV4Address() { 
		 return this.ipV4Address; } 
    public void setIpV4Address(String ipV4Address) { 
		 this.ipV4Address = ipV4Address; } 
    String ipV4Address;
    @JsonProperty("ipV6Address") 
    public String getIpV6Address() { 
		 return this.ipV6Address; } 
    public void setIpV6Address(String ipV6Address) { 
		 this.ipV6Address = ipV6Address; } 
    String ipV6Address;
}
