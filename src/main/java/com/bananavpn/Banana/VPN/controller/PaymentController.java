package com.bananavpn.Banana.VPN.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bananavpn.Banana.VPN.Model.CustomerResult;
import com.bananavpn.Banana.VPN.Model.Users;
import com.bananavpn.Banana.VPN.Repository.UserRepository;
import com.bananavpn.Banana.VPN.Response.ApiResponse;
import com.bananavpn.Banana.VPN.Response.ListResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.net.RequestOptions;

@RestController
public class PaymentController {
	@Autowired
	UserRepository userRepository;
	
	@Value("${stripe.apikey}")
	String stripekey;
	
	@Value("${stripe.publicapikey}")
	String publicapikey;
	
	List<Price> getPricesFromStripe() throws StripeException
	{
		Stripe.apiKey = stripekey;

		Map<String, Object> params = new HashMap<>();

		PriceCollection prices = Price.list(params);
		return prices.getData();
	}
	
	@PostMapping("/createstripecustomer")
    public ResponseEntity<?> createStripeCustomer(@RequestParam("userid") String userid,
    		@RequestParam("amount") String amount,
    		@RequestParam("currency") String currency,
    		@RequestParam("description") String description) throws StripeException {
		try
		{
			Stripe.apiKey = stripekey;
			
			Optional<Users> objUser = userRepository.findById(userid);
			
			Map<String, Object> paramsCust = new HashMap<>();
			if(objUser.isPresent())
			{
				paramsCust.put("name", objUser.get().getFirstname() + " " + objUser.get().getLastname());
				paramsCust.put("email", objUser.get().getEmail());
			}
			Customer customer = Customer.create(paramsCust);
			
			Map<String, Object> paramsEphemeralKey = new HashMap<>();
			paramsEphemeralKey.put("customer",customer.getId());
			
			RequestOptions options = RequestOptions.builder().setStripeVersionOverride("2017-05-25").build();
			EphemeralKey key = EphemeralKey.create(paramsEphemeralKey, options);
			
			Map<String, Object> paramspayment = new HashMap<>();
			paramspayment.put("amount", amount);
			paramspayment.put("currency", currency);
			paramspayment.put("customer", customer.getId());
			paramspayment.put("description", description);
			
			Map<String, Object> param_automatic = new HashMap<>();
			param_automatic.put("enabled",true);
			
			paramspayment.put("automatic_payment_methods",param_automatic);

			PaymentIntent paymentIntent = PaymentIntent.create(paramspayment);
			
			Optional<CustomerResult> newCust =  Optional.of(new CustomerResult());
			
			newCust.get().setcustomer(customer.getId());
			newCust.get().setephemeralKey(key.getSecret());
			newCust.get().setpaymentIntent(paymentIntent.getClientSecret());
			newCust.get().setpublishableKey(publicapikey);
			
			return new ResponseEntity<ApiResponse>(new ApiResponse(200,"Success",true,newCust), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getprices")
    public ResponseEntity<?> getPrices() throws StripeException {
		try
		{
			return new ResponseEntity<ListResponse>(new ListResponse(200,"Success",true,getPricesFromStripe()), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/createprice")
    public ResponseEntity<?> createPrice() throws StripeException {
		try
		{
			Stripe.apiKey = stripekey;

			Map<String, Object> recurring = new HashMap<>();
			recurring.put("interval", "month");
			Map<String, Object> params = new HashMap<>();
			params.put("unit_amount", 2000);
			params.put("currency", "usd");
			params.put("recurring", recurring);
			params.put("product", "prod_N4Rjc4866IH1B3");

			Price price = Price.create(params);
			
			return new ResponseEntity<ListResponse>(new ListResponse(200,"Success",true,getPricesFromStripe()), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ListResponse>(new ListResponse(505,e.getMessage(),false,null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
