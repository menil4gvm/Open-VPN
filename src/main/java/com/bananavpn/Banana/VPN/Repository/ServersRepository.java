package com.bananavpn.Banana.VPN.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bananavpn.Banana.VPN.Model.Servers;

public interface ServersRepository extends MongoRepository<Servers, String> {
	List<Servers> findByUserid(String userid);
}
