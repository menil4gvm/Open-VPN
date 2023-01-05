package com.bananavpn.Banana.VPN.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bananavpn.Banana.VPN.Model.Favorite;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
	Boolean existsByUseridAndServerid(String userid,String serverid);
	Boolean existsByUseridAndServeridAndIsfavorite(String userid,String serverid,Boolean isfavorite);
	
	Optional<Favorite> findByUseridAndServeridAndIsfavorite(String userid,String serverid,Boolean isfavorite);
	Optional<Favorite> findByUseridAndServerid(String userid,String serverid);
	
	Long deleteFavoriteByUseridAndServerid(String userid,String connectorid);
}
