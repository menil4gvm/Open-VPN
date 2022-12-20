package com.bananavpn.Banana.VPN.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bananavpn.Banana.VPN.Model.Favorite;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
	Boolean existsByUseridAndConnectorid(String userid,String connectorid);
	Boolean existsByUseridAndConnectoridAndIsfavorite(String userid,String connectorid,Boolean isfavorite);
	
	Optional<Favorite> findByUseridAndConnectoridAndIsfavorite(String userid,String connectorid,Boolean isfavorite);
	Optional<Favorite> findByUseridAndConnectorid(String userid,String connectorid);
	
	Long deleteFavoriteByUseridAndConnectorid(String userid,String connectorid);
}
