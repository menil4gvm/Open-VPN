package com.bananavpn.Banana.VPN.controller;

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
import com.bananavpn.Banana.VPN.Model.Connector;
import com.bananavpn.Banana.VPN.Model.Favorite;
import com.bananavpn.Banana.VPN.Model.OpenVPN;
import com.bananavpn.Banana.VPN.Model.Region;
import com.bananavpn.Banana.VPN.Repository.FavoriteRepository;
import com.bananavpn.Banana.VPN.Response.ApiResponse;
import com.bananavpn.Banana.VPN.Response.ListResponse;
import com.bananavpn.Banana.VPN.Services.OpenVPNServices;

@RestController
public class OpenVPNController {
	@Autowired
	FavoriteRepository favoriteRepository;
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
			if(favoriteRepository.existsByUseridAndConnectorid(favorite.getuserid(), favorite.getconnectorid()))
			{
				favoriteRepository.deleteFavoriteByUseridAndConnectorid(favorite.getuserid(), favorite.getconnectorid());
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
	
	//method for getting all server list
	@GetMapping("/getserverlist")
	public ResponseEntity<ListResponse> getServerList(@RequestParam("userid") String userid) {
		try {
			List<OpenVPN> newlistOpenVPN = new ArrayList<OpenVPN>();
			List<Connector> listConnectors = openVPNServices.getConnector();
			
			List<Region> listregion = openVPNServices.getRegions();
			for(int i=0;i<listConnectors.size();i++)
			{
				for(int r=0;r<listregion.size();r++)
				{
					if(listregion.get(r).getId().equalsIgnoreCase(listConnectors.get(i).getVpnRegionId()))
					{
						OpenVPN objopenvpn = new OpenVPN();
						
						//System.out.println(listConnectors.get(i).getId() + " = > " + listregion.get(r).getId());
						objopenvpn.setconnectorid(listConnectors.get(i).getId());
						objopenvpn.setipV4Address(listConnectors.get(i).getIpV4Address());
						objopenvpn.setisfavorite(favoriteRepository.existsByUseridAndConnectoridAndIsfavorite(userid,listConnectors.get(i).getId(),true));
						objopenvpn.setopenvpn(openVPNServices.getOvpn(listConnectors.get(i).getId()));
						objopenvpn.setregion(listregion.get(r));
						
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
