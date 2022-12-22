package com.bananavpn.Banana.VPN.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bananavpn.Banana.VPN.Model.Users;
import com.bananavpn.Banana.VPN.Repository.UserRepository;
import com.bananavpn.Banana.VPN.Request.LoginRequest;
import com.bananavpn.Banana.VPN.Response.ApiResponse;

@RestController
public class Auth {
	@Autowired
	UserRepository userRepository;
		
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody Users users) {
		try
		{
			//users.setPassword(passwordEncoder.encode(users.getPassword()));
			if(userRepository.existsByEmailIgnoreCase(users.getEmail()))
			{
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User Already Registered with this email!",false,null), HttpStatus.OK);
			}
			else
			{
				userRepository.save(users);
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User Registered Successfully!", true,null), HttpStatus.OK);
			}
		}
		catch(Exception ex)
		{
			return new ResponseEntity<ApiResponse>(new ApiResponse(505,ex.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
		try
		{
			if(userRepository.existsByEmailIgnoreCaseAndPassword(loginRequest.getEmail(),loginRequest.getPassword()))
			{
				Optional<Users> objUser = userRepository.findByEmailIgnoreCase(loginRequest.getEmail());
				if(objUser.get().getisgooglesignin())
				{
					return new ResponseEntity<ApiResponse>(new ApiResponse(200, "You account is sign in with google!",false,null), HttpStatus.OK);
				}
				else
				{
					objUser = userRepository.findByEmailIgnoreCaseAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
					return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Login successfully!",true,objUser), HttpStatus.OK);
				}
			}
			else
			{
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "Email or password is invalid!",false,null), HttpStatus.OK);
			}
		}
		catch(Exception ex)
		{
			return new ResponseEntity<ApiResponse>(new ApiResponse(505,ex.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/googlesignin")
	public ResponseEntity<?> GoogleSignIn(@RequestBody Users users) {
		try
		{
			if(userRepository.existsByEmailIgnoreCaseAndIsgooglesignin(users.getEmail(),true))
			{
				Optional<Users> objUser = userRepository.findByEmailIgnoreCaseAndIsgooglesignin(users.getEmail(),true);
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User Sign in successfully!!",true,objUser), HttpStatus.OK);
			}
			else
			{
				users.setisgooglesignin(true);
				userRepository.save(users);
				Optional<Users> objUser = userRepository.findByEmailIgnoreCaseAndIsgooglesignin(users.getEmail(),true);
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User Sign in successfully!", true,objUser), HttpStatus.OK);
			}
		}
		catch(Exception ex)
		{
			return new ResponseEntity<ApiResponse>(new ApiResponse(505,ex.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
