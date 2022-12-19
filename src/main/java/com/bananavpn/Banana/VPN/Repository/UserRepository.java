package com.bananavpn.Banana.VPN.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bananavpn.Banana.VPN.Model.Users;

public interface UserRepository extends MongoRepository<Users, String> {
	Boolean existsByEmailIgnoreCase(String email);
	Boolean existsByEmailIgnoreCaseAndPassword(String email,String Password);
	Boolean existsByEmailIgnoreCaseAndIsgooglesignin(String email,Boolean isgooglesignin);
	
	Optional<Users> findByEmailIgnoreCaseAndPassword(String email,String Password);
	Optional<Users> findByEmailIgnoreCase(String email);
	Optional<Users> findByEmailIgnoreCaseAndIsgooglesignin(String email,Boolean isgooglesignin);
}
