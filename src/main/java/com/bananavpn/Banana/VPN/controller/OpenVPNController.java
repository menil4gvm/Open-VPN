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
import com.bananavpn.Banana.VPN.Model.Servers;
import com.bananavpn.Banana.VPN.Repository.FavoriteRepository;
import com.bananavpn.Banana.VPN.Repository.ServersRepository;
import com.bananavpn.Banana.VPN.Response.ApiResponse;
import com.bananavpn.Banana.VPN.Response.ListResponse;
import com.bananavpn.Banana.VPN.Services.OpenVPNServices;

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
	
	//method for getting all server list
	@PostMapping("/createopenvpnserver")
	public ResponseEntity<ApiResponse> createOpenVPNServer(@RequestBody Servers server) {
		try
		{
			server.setopenvpn("client\r\n"
					+ "dev tun\r\n"
					+ "proto udp\r\n"
					+ "remote 144.217.242.199 1194\r\n"
					+ "resolv-retry infinite\r\n"
					+ "nobind\r\n"
					+ "persist-key\r\n"
					+ "persist-tun\r\n"
					+ "remote-cert-tls server\r\n"
					+ "auth SHA512\r\n"
					+ "cipher AES-256-CBC\r\n"
					+ "ignore-unknown-option block-outside-dns\r\n"
					+ "verb 3\r\n"
					+ "<ca>\r\n"
					+ "-----BEGIN CERTIFICATE-----\r\n"
					+ "MIIDSzCCAjOgAwIBAgIUOtuab/Z4aHQaOjlAZdOuCbeYP8owDQYJKoZIhvcNAQEL\r\n"
					+ "BQAwFjEUMBIGA1UEAwwLRWFzeS1SU0EgQ0EwHhcNMjIxMjE0MDYxODQ4WhcNMzIx\r\n"
					+ "MjExMDYxODQ4WjAWMRQwEgYDVQQDDAtFYXN5LVJTQSBDQTCCASIwDQYJKoZIhvcN\r\n"
					+ "AQEBBQADggEPADCCAQoCggEBALJnLEXy9RNiW9WjVK1G7ZzjiJfZOxx96OvWTaka\r\n"
					+ "RLF7rdNjkP2q8dJivnmN1XN/zzfpbyQS4ejK5vk4mWSz/CyZrsCRXJt3KnDb9jLN\r\n"
					+ "BeNic+mmuXgQ/mZxs1OblIsezkumHBvYxuhiLm9OBm8q7im4x2QdHDbxFMk4sOMu\r\n"
					+ "58i/RPeLwAtyN9meButnFL4R3B5Lj15Tq57v59/ofDHvNcqHI7w7ACsFqrrNpWzw\r\n"
					+ "PCRP+9NPeYk27eAasgn/f/4X4yxXH5Yr5p03jF2UGuLAqOV1yUcqyaTA36yp/LLi\r\n"
					+ "9E5q47iG8IMJ/XVRjlXiRqFIRCCpHftDFwvPcvGvGgNpTE0CAwEAAaOBkDCBjTAM\r\n"
					+ "BgNVHRMEBTADAQH/MB0GA1UdDgQWBBR18PzMDQXYMPQAAlh9pFlH1HnHrDBRBgNV\r\n"
					+ "HSMESjBIgBR18PzMDQXYMPQAAlh9pFlH1HnHrKEapBgwFjEUMBIGA1UEAwwLRWFz\r\n"
					+ "eS1SU0EgQ0GCFDrbmm/2eGh0Gjo5QGXTrgm3mD/KMAsGA1UdDwQEAwIBBjANBgkq\r\n"
					+ "hkiG9w0BAQsFAAOCAQEAkfdDq7ft52OL4h90gmymSl0o+CzJIMYnuSpv8p0EUs8t\r\n"
					+ "Tsy7XPFJczQW2TPP0of1MGN9y6Kw7TSGEJQkuGly8S+Jf97rxoW3OKKdVkFnmT0P\r\n"
					+ "IwNUe5FttZCsCUWFUe96yH6unCLUDXR2jDfYVskpmMkop9mb7KO1QC80jfHDLbs/\r\n"
					+ "5veEM2ud/nD4eojCC9nEpNaEwvfVvhoTF9jLn6dtdzfdMWjzqameCXysfbkYHUj/\r\n"
					+ "FzTG4WfWzb/gapTo6rBVLeg7rPR3/AvF0IiL39AJehGvJ46gaE28dX+ca2QbPYLO\r\n"
					+ "eMWp13R9t9WLOGW4kXTI4sac+M+nISisp65Y2QmxLA==\r\n"
					+ "-----END CERTIFICATE-----\r\n"
					+ "</ca>\r\n"
					+ "<cert>\r\n"
					+ "-----BEGIN CERTIFICATE-----\r\n"
					+ "MIIDVjCCAj6gAwIBAgIRAJHiGY523BKWOtE1LxFUY10wDQYJKoZIhvcNAQELBQAw\r\n"
					+ "FjEUMBIGA1UEAwwLRWFzeS1SU0EgQ0EwHhcNMjIxMjE0MDYxODQ4WhcNMzIxMjEx\r\n"
					+ "MDYxODQ4WjASMRAwDgYDVQQDDAdkZXNrdG9wMIIBIjANBgkqhkiG9w0BAQEFAAOC\r\n"
					+ "AQ8AMIIBCgKCAQEAzO8LZCt1M7SbJoVJUNOtE4xhr1Ttb2fMjP0iMZiEXEGetkSJ\r\n"
					+ "iAkTUceayVvVFcTsapd9s0ysXu82OCYxsDjeM11QxM7PMPX7bIsgqOLhTX3jS6OQ\r\n"
					+ "pXtHeH9gJU8V7X7nNfXnutIMSHZOLsR54vDFPRly40EpoDYJROnY10bNeQFGuCM0\r\n"
					+ "AkY0DFzVfJCdIkhIBtVNQWubcNeW5KS1APgSbcAtUs4+2Vjgu0Ef1ezBKobg67Vi\r\n"
					+ "Gn6NYXDAz1Spb/NVpS8ewie+sKpwif3EQJANy9S7Fq8Kht7WU3xfXB2UrUb5nA0S\r\n"
					+ "eV1WCi6iWlayOaeAPSk06Vq4COcgdLiT1uDiawIDAQABo4GiMIGfMAkGA1UdEwQC\r\n"
					+ "MAAwHQYDVR0OBBYEFBCKxaBucYdlJJ1C38YzpyVzt+iwMFEGA1UdIwRKMEiAFHXw\r\n"
					+ "/MwNBdgw9AACWH2kWUfUecesoRqkGDAWMRQwEgYDVQQDDAtFYXN5LVJTQSBDQYIU\r\n"
					+ "Otuab/Z4aHQaOjlAZdOuCbeYP8owEwYDVR0lBAwwCgYIKwYBBQUHAwIwCwYDVR0P\r\n"
					+ "BAQDAgeAMA0GCSqGSIb3DQEBCwUAA4IBAQCBS9KaWuUjxS7LiAzhW7rODBFqKPn9\r\n"
					+ "8pC1IRYzEcf+d8LkrYfs7gOKb7diwDwV9e8rGYfYm3TaUGDBOr6japBFFUEJ1jsw\r\n"
					+ "tqdFwLGyaHgPiVSSZM/MOppTGs045UFYaUEMC/KMgiiNdhzerIq88FbAQOc+Q7Jh\r\n"
					+ "AXR1l0CRhAZV96XXiJh1PcN4oKAGJehZDosU9ZYPuLFlRPfwqoCqyPWyHMppFngO\r\n"
					+ "B8tny/XfXMpoXHij5Iv8KrRNK4yY9pT2nRCaaESOOhqnmh3c8nIvVOdc3l1ksw6j\r\n"
					+ "zaGt0GkbmY8Kp+jPiMiJ+lxXQ0LxpxXtXllRla/LeLi13QDQ6AgOcjQ/\r\n"
					+ "-----END CERTIFICATE-----\r\n"
					+ "</cert>\r\n"
					+ "<key>\r\n"
					+ "-----BEGIN PRIVATE KEY-----\r\n"
					+ "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDM7wtkK3UztJsm\r\n"
					+ "hUlQ060TjGGvVO1vZ8yM/SIxmIRcQZ62RImICRNRx5rJW9UVxOxql32zTKxe7zY4\r\n"
					+ "JjGwON4zXVDEzs8w9ftsiyCo4uFNfeNLo5Cle0d4f2AlTxXtfuc19ee60gxIdk4u\r\n"
					+ "xHni8MU9GXLjQSmgNglE6djXRs15AUa4IzQCRjQMXNV8kJ0iSEgG1U1Ba5tw15bk\r\n"
					+ "pLUA+BJtwC1Szj7ZWOC7QR/V7MEqhuDrtWIafo1hcMDPVKlv81WlLx7CJ76wqnCJ\r\n"
					+ "/cRAkA3L1LsWrwqG3tZTfF9cHZStRvmcDRJ5XVYKLqJaVrI5p4A9KTTpWrgI5yB0\r\n"
					+ "uJPW4OJrAgMBAAECggEBAMCGnpwHm77soImq1Drj8giYbLksPzfXOzsFIJeZYlfN\r\n"
					+ "qg4wC0jKxeShchBTfrdJ3pUxJv1IPfOeJcDQWQPmGRIGS7fpVor3vLG3bwJayzFi\r\n"
					+ "4COSUXS1gWp5t9i1GLOa8homoAJKDRISWtbi7GNAsPv6qY1IDZQez8x3KKjuMmDH\r\n"
					+ "G/PGOEVm1wi9dZbCd+E8pk2M9FlsUT25fNljLu1ktN2yk4fvXoPFmR7dDu+H5NCU\r\n"
					+ "QgZMGMbxXaspVBAkQPS3PWiSIxguKiqpDvFzbBXLQwE9hcV5SFCBqNPb9IZPPBpZ\r\n"
					+ "h+29xn8m8DPSQ62WOwO/v1nwujIzuehKeuxuAHbkXZkCgYEA89zMKcma/8PlepOV\r\n"
					+ "5909m8E0Ck85lmxsWjmXPnOa51s1lQr4Dwo0a9eWx0ooq1LzOi+dBbf1FHHCcCWU\r\n"
					+ "cENG48qUjofVP9/wrpZ4+Xk+YPHUzPbL2e5EQC+pS2b/zkMQHktofRhWbIO+Zzkp\r\n"
					+ "jFx8OPwB3J159K8Ih8yC0R/odfUCgYEA1yI7ayca7RnXWzmkn6hwalfJXG7Cnx5Y\r\n"
					+ "Qf2ofp7P6euHoAh1KNCeNbHwnagfsQj9R/s7oNDpueGLQBIq9hlK63lvRsjtCT8J\r\n"
					+ "9SIhv3FyqTP1aioFJxmqk5bGlw9u0eFLgg0huEOv0Y9PO0R2A2P8qt4T+xemhKyb\r\n"
					+ "T8s0GQ4CWt8CgYBgSMmDpkpAugQdRbBmgAAXQ01c1LMUJXMSnLKOwe5jcqvRUSdo\r\n"
					+ "KMtR2Sl7PliYbIQEcVA6NcvaaqAuz2ewe5+9yPqm3BLiyXSqOldBiRGMDW/MDZWn\r\n"
					+ "UmA4R5k8YEqYEfqHO5bh7X4SEmJXN0akWM/jX/0MMlE4vvNLSwBLuaF7EQKBgQDB\r\n"
					+ "QG/tvnASos7wlKRMA2wQKxGn5ZsQgpq3lNvh+lk8gSYQ3OgZpOUpyNEjXl4xkqir\r\n"
					+ "G4Sh3mjGYFPNV0SeU3QYeLo79MvxLV6NhH6aUZe1ZQZEusBsMkMnVx7HncZn5KEG\r\n"
					+ "jzNGKg9rCPgcflAglZXoC5wZR1J/BepyUFBZXVtCMwKBgCEOu6uVsNC/NvFprbDn\r\n"
					+ "y0PVKKsMsXX+keIxRNJpnh25XWtznvuA4AgYNOhGFJXZIzRAL8vs83J8OrlVwflg\r\n"
					+ "wPOoNJlAYu06nA+gKaOidIRey1yAvncZHYLkkZatH7MIywsjXnlb49ITFEcBZ+YN\r\n"
					+ "NIQcT78vQ3NKpvFYN0r7R14Q\r\n"
					+ "-----END PRIVATE KEY-----\r\n"
					+ "</key>\r\n"
					+ "<tls-crypt>\r\n"
					+ "-----BEGIN OpenVPN Static key V1-----\r\n"
					+ "1f23d0958639570606d05e226de9db81\r\n"
					+ "6c5933de999415467978121ef7e4d94a\r\n"
					+ "8741947de3952ed9586a36d7b4324c5d\r\n"
					+ "fb38af04a9a853db4d7cb722a70a8ecd\r\n"
					+ "d976416b781b50e8c3dfa515cd92ce5a\r\n"
					+ "430f1a513d1f7fe643e5e0f57b358640\r\n"
					+ "cc830798b0009fcc210b9bc0affdbb43\r\n"
					+ "ec6a4c0107b461987fa6f9346d49bc54\r\n"
					+ "52f7569bd7798810cb0d6c59f1cb0e87\r\n"
					+ "1b20ec9723d8b7710eb74b1c810b7a77\r\n"
					+ "952f84492500c643348a5b1001da05c5\r\n"
					+ "038bf1d9608877eb18d920f58b454803\r\n"
					+ "5e03ed60a32997afd42c1eedd1bce924\r\n"
					+ "287743784339006580c29f09e9420505\r\n"
					+ "d53f98651020fd47f2b6945348f459dd\r\n"
					+ "5b8fd0cb67b25d02576cebfbe3e01c5a\r\n"
					+ "-----END OpenVPN Static key V1-----\r\n"
					+ "</tls-crypt>\r\n"
					+ "");
			serversRepository.save(server);
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
			List<Servers> newlistOpenVPN = serversRepository.findByUserid(userid);
			
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
