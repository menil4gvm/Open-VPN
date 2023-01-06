package com.bananavpn.Banana.VPN.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.bananavpn.Banana.VPN.Model.Connector;
import com.bananavpn.Banana.VPN.Model.Favorite;
import com.bananavpn.Banana.VPN.Model.OpenVPN;
import com.bananavpn.Banana.VPN.Model.Region;
import com.bananavpn.Banana.VPN.Model.Servers;
import com.bananavpn.Banana.VPN.Repository.FavoriteRepository;
import com.bananavpn.Banana.VPN.Repository.ServersRepository;
import com.bananavpn.Banana.VPN.Response.ApiResponse;
import com.bananavpn.Banana.VPN.Response.ListResponse;
import com.bananavpn.Banana.VPN.Services.OpenVPNServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

@RestController
public class OpenVPNController {
	@Autowired
	FavoriteRepository favoriteRepository;
	@Autowired
	ServersRepository serversRepository;
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
	
	//method for add/remove server from favorite
	@PostMapping("/addtofavorite")
	public ResponseEntity<?> addToFavorite(@RequestBody Favorite favorite) {
		try {
			if(favoriteRepository.existsByUseridAndServerid(favorite.getuserid(), favorite.getserverid()))
			{
				favoriteRepository.deleteFavoriteByUseridAndServerid(favorite.getuserid(), favorite.getserverid());
			}
			favoriteRepository.save(favorite);
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Success!",true,null), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(500,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//method for generate refresh token for open vpn
	@GetMapping("/refreshaccesstoken")
	public ResponseEntity<?> RefreshAccessToken() {
		try {
			return new ResponseEntity<>(openVPNServices.refreshToken(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//function for get content from file
	private String readFromInputStream(InputStream inputStream)
			  throws IOException {
			    StringBuilder resultStringBuilder = new StringBuilder();
			    try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(inputStream))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			            resultStringBuilder.append(line).append("\n");
			        }
			    }
			    return resultStringBuilder.toString();
	}
	
	//method for add open vpn server
	@PostMapping("/createopenvpnserver")
	public ResponseEntity<ApiResponse> createOpenVPNServer(@RequestParam("serverdata") String serverdata,
			@RequestParam(value = "ovpnfile", required = false) MultipartFile ovpnfile) {
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Servers newServer = mapper.readValue(serverdata, Servers.class);
					
			String openVPNdata = readFromInputStream(ovpnfile.getInputStream());		
			newServer.setopenvpn(openVPNdata);

			serversRepository.save(newServer);
			return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Server added!",true,null), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//method for getting all server list
	@GetMapping("/getserverlist")
	public ResponseEntity<ListResponse> getServerList(@RequestParam("userid") String userid) {
		try {
			List<Servers> newlistOpenVPN = serversRepository.findAll();
			
			for(int i=0;i<newlistOpenVPN.size();i++)
			{
				newlistOpenVPN.get(i).setisfavorite(favoriteRepository.existsByUseridAndServeridAndIsfavorite(userid,newlistOpenVPN.get(i).getid(),true));
			}
			
			
//			List<OpenVPN> newlistOpenVPN = new ArrayList<OpenVPN>();
//			List<Connector> listConnectors = openVPNServices.getConnector();
//			
//			List<Region> listregion = openVPNServices.getRegions();
//			for(int i=0;i<listConnectors.size();i++)
//			{
//				for(int r=0;r<listregion.size();r++)
//				{
//					if(listregion.get(r).getId().equalsIgnoreCase(listConnectors.get(i).getVpnRegionId()))
//					{
//						OpenVPN objopenvpn = new OpenVPN();
//						
//						//System.out.println(listConnectors.get(i).getId() + " = > " + listregion.get(r).getId());
//						objopenvpn.setconnectorid(listConnectors.get(i).getId());
//						objopenvpn.setipV4Address(listConnectors.get(i).getIpV4Address());
//						objopenvpn.setisfavorite(favoriteRepository.existsByUseridAndConnectoridAndIsfavorite(userid,listConnectors.get(i).getId(),true));
//						objopenvpn.setopenvpn(openVPNServices.getOvpn(listConnectors.get(i).getId()));
//						objopenvpn.setregion(listregion.get(r));
//						
//						newlistOpenVPN.add(objopenvpn);
//					}
//				}
//			}
			return new ResponseEntity<ListResponse>(new ListResponse(200, "Get Open VPN Successfully!",true,newlistOpenVPN), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//method for get all connector list
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
