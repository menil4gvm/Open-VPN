package com.bananavpn.Banana.VPN;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
	@GetMapping("/")
	public String index() {
		return "Welcome to Banana VPN APIs!";
	}
}
