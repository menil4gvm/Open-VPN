package com.bananavpn.Banana.VPN.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.bananavpn.Banana.VPN.Model.AuthToken;
import com.bananavpn.Banana.VPN.Model.Connector;
import com.bananavpn.Banana.VPN.Model.Region;

@Service
public class OpenVPNServices {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${openvpnurl}")
	private String openvpnurl;
	@Value("${clientid}")
	private String clientid;	
	@Value("${secretid}")
	private String secretid;
	
	public String refreshToken() {
		try {
			String url = openvpnurl + "/api/beta/oauth/token?client_id="+clientid+"&client_secret="+secretid+"&grant_type=client_credentials";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("user-agent", "Application");
			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(headers, headers);
			
			ResponseEntity<AuthToken> response = restTemplate.exchange(url, HttpMethod.POST, formEntity, AuthToken.class);
			return response.getBody().getAccess_token();
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public List<Connector> getConnector() {
		try {
			String url = openvpnurl+"/api/beta/connectors";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("user-agent", "Application");
			headers.add("x-api-key", clientid);	
			headers.add("Authorization", "Bearer " + refreshToken() );
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			
			ResponseEntity<List<Connector>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<List<Connector>>() {});
			List<Connector> listConnector = responseEntity.getBody();
			
			return listConnector;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList<Connector>();
		}
	}
	
	public String getOvpn(String connectorId) {
		try {
			String url = openvpnurl + "/api/beta/connectors/" + connectorId + "/profile";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("user-agent", "Application");
			headers.add("Authorization", "Bearer " + refreshToken());
			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(headers, headers);
			
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, formEntity,String.class);
			//List<Region> listConnector = responseEntity.getBody();
			//System.out.println(responseEntity.getBody());
			
			return responseEntity.getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public List<Region> getRegions() {
		try {
			String url = openvpnurl + "/api/beta/regions";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("charset", "utf-8");
			headers.add("user-agent", "Application");
			headers.add("Authorization", "Bearer " + refreshToken());
			HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(headers, headers);
			
			ResponseEntity<List<Region>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, formEntity,new ParameterizedTypeReference<List<Region>>() {});
			List<Region> listConnector = responseEntity.getBody();
			
			return listConnector;
		}
		catch (Exception e) {
			return new ArrayList<Region>();
		}
	}
}
