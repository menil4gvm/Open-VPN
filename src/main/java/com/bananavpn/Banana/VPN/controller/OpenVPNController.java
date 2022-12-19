package com.bananavpn.Banana.VPN.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bananavpn.Banana.VPN.Model.Connector;
import com.bananavpn.Banana.VPN.Model.ConnectorResult;
import com.bananavpn.Banana.VPN.Model.OpenVPN;
import com.bananavpn.Banana.VPN.Model.Region;
import com.bananavpn.Banana.VPN.Response.ApiResponse;
import com.bananavpn.Banana.VPN.Response.ListResponse;
import com.bananavpn.Banana.VPN.Services.OpenVPNServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class OpenVPNController {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	OpenVPNServices openVPNServices;
	
	@Value("${openvpnurl}")
	private String openvpnurl;
	@Value("${clientid}")
	private String clientid;	
	@Value("${secretid}")
	private String secretid;
	
	@GetMapping("/refreshaccesstoken")
	public ResponseEntity<?> RefreshAccessToken() {
		try {
			return new ResponseEntity<>(openVPNServices.refreshToken(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getopenvpn")
	public ResponseEntity<?> getOpenVPN() {
		try {
			//System.out.println("Enter in get open VPN");
			List<OpenVPN> newlistOpenVPN = new ArrayList<OpenVPN>();
			List<Connector> listConnectors = openVPNServices.getConnector();
			
			List<Region> listregion = openVPNServices.getRegions();
			for(int i=0;i<listConnectors.size();i++)
			{
				//System.out.println("Connector Id : " + listConnectors.get(i).getVpnRegionId());
				for(int r=0;r<listregion.size();r++)
				{
					if(listregion.get(r).getId().equalsIgnoreCase(listConnectors.get(i).getVpnRegionId()))
					{
						OpenVPN objopenvpn = new OpenVPN();
						
						//System.out.println(listConnectors.get(i).getName() + " = > " + listregion.get(r).getId());
						objopenvpn.setopenvpn(openVPNServices.getOvpn(listConnectors.get(i).getId()));
						objopenvpn.setregions(listregion.get(r));
						
						newlistOpenVPN.add(objopenvpn);
					}
				}
			}
			return new ResponseEntity<ListResponse>(new ListResponse(200, "Get Open VPN Successfully!",true,newlistOpenVPN), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getconnector")
	public ResponseEntity<?> getConnector() {
		try {
				List<Connector> listcon = openVPNServices.getConnector();
				return new ResponseEntity<ListResponse>(new ListResponse(200, "Get Connectors successfully!",true,listcon), HttpStatus.OK);
			}
			catch (Exception e) {
				return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
}