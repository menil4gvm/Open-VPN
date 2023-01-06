package com.bananavpn.Banana.VPN.controller;

import java.time.Instant;
import java.util.Date;
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
		
	//method for register user
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody Users users) {
		try
		{
			users.setcreatetimestamp(System.currentTimeMillis());
			users.setupdatetimestamp(System.currentTimeMillis());
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
	
	//method for login user
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
					
			         Date today = new Date();
			         Date userdate = toDate(objUser.get().getcreatetimestamp());
			         
			         if(getDiffrenceInDays(userdate,today)>15)
			         {
			        	 objUser.get().setistrial(false);
			         }
			         
			         if(objUser.get().getissubscribe()==true)
		        	 {
		        		 objUser.get().setistrial(false);
		        	 }
			         
			         if(objUser.get().getSubscriptionDetails()!=null)
			         {
			        	 
			        	 Date subscriptiondate = toDate(objUser.get().getSubscriptionDetails().getcreatedtimestamps());
				         if(objUser.get().getSubscriptionDetails().getinterval().equalsIgnoreCase("year"))
				         {
				        	 if(getDiffrenceInYear(userdate,subscriptiondate)>=0)
					         {
					        	 objUser.get().setissubscribe(false);
					         }
				         }
				         
				         if(objUser.get().getSubscriptionDetails().getinterval().equalsIgnoreCase("month"))
				         {
				        	 if(getDiffrenceInDays(userdate,subscriptiondate)>30)
					         {
					        	 objUser.get().setissubscribe(false);
					         }
				         }
			         }
					
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
	
	//method for login/register with google
	@PostMapping("/googlesignin")
	public ResponseEntity<?> GoogleSignIn(@RequestBody Users users) {
		try
		{
			if(userRepository.existsByEmailIgnoreCaseAndIsgooglesignin(users.getEmail(),true))
			{
				Optional<Users> objUser = userRepository.findByEmailIgnoreCaseAndIsgooglesignin(users.getEmail(),true);
				
				Date today = new Date();
		         Date userdate = toDate(objUser.get().getcreatetimestamp());
		         
		         if(getDiffrenceInDays(userdate,today)>15)
		         {
		        	 objUser.get().setistrial(false);
		         }
		         
		         if(objUser.get().getissubscribe()==true)
	        	 {
	        		 objUser.get().setistrial(false);
	        	 }
		         
		         if(objUser.get().getSubscriptionDetails()!=null)
		         {
		        	 
		        	 Date subscriptiondate = toDate(objUser.get().getSubscriptionDetails().getcreatedtimestamps());
			         if(objUser.get().getSubscriptionDetails().getinterval().equalsIgnoreCase("year"))
			         {
			        	 if(getDiffrenceInYear(userdate,subscriptiondate)>=0)
				         {
				        	 objUser.get().setissubscribe(false);
				         }
			         }
			         
			         if(objUser.get().getSubscriptionDetails().getinterval().equalsIgnoreCase("month"))
			         {
			        	 if(getDiffrenceInDays(userdate,subscriptiondate)>30)
				         {
				        	 objUser.get().setissubscribe(false);
				         }
			         }
		         }
		
				
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User Sign in successfully!!",true,objUser), HttpStatus.OK);
			}
			else
			{
				users.setisgooglesignin(true);
				users.setcreatetimestamp(System.currentTimeMillis());
				users.setupdatetimestamp(System.currentTimeMillis());
				userRepository.save(users);
				Optional<Users> objUser = userRepository.findByEmailIgnoreCaseAndIsgooglesignin(users.getEmail(),true);
				
				Date today = new Date();
		         Date userdate = toDate(objUser.get().getcreatetimestamp());
		         
		         if(getDiffrenceInDays(userdate,today)>15)
		         {
		        	 objUser.get().setistrial(false);
		         }
		         
		         if(objUser.get().getissubscribe()==true)
	        	 {
	        		 objUser.get().setistrial(false);
	        	 }
		         
		         if(objUser.get().getSubscriptionDetails()!=null)
		         {
		        	 
		        	 Date subscriptiondate = toDate(objUser.get().getSubscriptionDetails().getcreatedtimestamps());
			         if(objUser.get().getSubscriptionDetails().getinterval().equalsIgnoreCase("year"))
			         {
			        	 if(getDiffrenceInYear(userdate,subscriptiondate)>=0)
				         {
				        	 objUser.get().setissubscribe(false);
				         }
			         }
			         
			         if(objUser.get().getSubscriptionDetails().getinterval().equalsIgnoreCase("month"))
			         {
			        	 if(getDiffrenceInDays(userdate,subscriptiondate)>30)
				         {
				        	 objUser.get().setissubscribe(false);
				         }
			         }
		         }
		
				
				return new ResponseEntity<ApiResponse>(new ApiResponse(200, "User Sign in successfully!", true,objUser), HttpStatus.OK);
			}
		}
		catch(Exception ex)
		{
			return new ResponseEntity<ApiResponse>(new ApiResponse(505,ex.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public long getDiffrenceInDays(Date d1,Date d2)
	{        
        long difference_In_Time = d2.getTime() - d1.getTime();

        long difference_In_Seconds = (difference_In_Time / 1000) % 60;

        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

        long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        System.out.println("Diffrence in date : " + d1 +" - " + d2 + " : " + difference_In_Days);

//        System.out.println(
//                difference_In_Years
//                + " years, "
//                + difference_In_Days
//                + " days, "
//                + difference_In_Hours
//                + " hours, "
//                + difference_In_Minutes
//                + " minutes, "
//                + difference_In_Seconds
//                + " seconds");
      
		return difference_In_Days;
	}
	
	public long getDiffrenceInYear(Date d1,Date d2)
	{        
        long difference_In_Time = d2.getTime() - d1.getTime();

        long difference_In_Seconds = (difference_In_Time / 1000) % 60;

        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

        long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        System.out.println("Diffrence in date : " + d1 +" - " + d2 + " : " + difference_In_Years);

        //System.out.println(difference_In_Years + " years");
        
		return difference_In_Years;
	}
		
	public Date toDate(long timestamp) {
        Date date = new Date(timestamp);
        return date;
    }

}
