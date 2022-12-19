package com.bananavpn.Banana.VPN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bananavpn.Banana.VPN.Repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/hello")
	public String Hello() {
		return "Hello World!";
	}
	
}
