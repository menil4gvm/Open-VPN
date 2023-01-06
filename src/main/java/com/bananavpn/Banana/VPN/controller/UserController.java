package com.bananavpn.Banana.VPN.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bananavpn.Banana.VPN.Model.Note;
import com.bananavpn.Banana.VPN.Model.SubscriptionDetails;
import com.bananavpn.Banana.VPN.Model.Users;
import com.bananavpn.Banana.VPN.Repository.UserRepository;
import com.bananavpn.Banana.VPN.Response.ApiResponse;
import com.bananavpn.Banana.VPN.Response.ListResponse;
import com.bananavpn.Banana.VPN.Services.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FirebaseMessagingService firebaseService;


	@RequestMapping("/send-notification")
	@ResponseBody
	public String sendNotification(@RequestBody Note note,
	              @RequestParam String topic) throws FirebaseMessagingException {
		return firebaseService.sendNotification(note, topic);
	}
	
	//method for update subscription details in user model after payment success
	@PostMapping("/updatesubscriptiondetails")
	public ResponseEntity<?> updateSubscriptionDetails(@RequestParam("userid") String userid,
			@RequestParam("customerid") String customerid,
			@RequestParam("interval") String interval,
			@RequestParam("description") String description) {
		try {
			Optional<Users> objUser = userRepository.findById(userid);
			if(objUser.isPresent())
			{
				SubscriptionDetails newSub = new SubscriptionDetails();
				newSub.setcustomerid(customerid);
				newSub.setinterval(interval);
				newSub.setdescription(description);
				newSub.setcreatedtimestamps(System.currentTimeMillis());
				
				objUser.get().setSubscriptionDetails(newSub);
				objUser.get().setissubscribe(true);
				objUser.get().setistrial(false);
				objUser.get().setcreatetimestamp(objUser.get().getcreatetimestamp());
				objUser.get().setupdatetimestamp(System.currentTimeMillis());
				
				userRepository.save(objUser.get());
				
				return new ResponseEntity<ApiResponse>(new ApiResponse(200,"Success",true,objUser), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<ApiResponse>(new ApiResponse(404,"User was not registerd!",false,null), HttpStatus.NOT_FOUND);
			}
			
		}
		catch (Exception e) {
			return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//method for gettting single user details
	@GetMapping("/getuserdetails")
	public ResponseEntity<?> getUserDetails(@RequestParam("userid") String userid) {
		try {
			Optional<Users> objUser = userRepository.findById(userid);
			if(objUser.isPresent())
			{
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
		         		         
				return new ResponseEntity<ApiResponse>(new ApiResponse(200,"Success",true,objUser), HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<ApiResponse>(new ApiResponse(404,"User was not registerd!",false,null), HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
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