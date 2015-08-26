package dao;

import java.util.List;
import java.util.Map;

import entity.StoreCollects;
import entity.StoreProducts;

public interface StoreCollectsDao {
	int add(StoreCollects storeCollects);

	int count(int userID);

	List<StoreProducts> findByUserID(Map<String, Integer> map);

	int checkCollectStatus(StoreCollects storeCollects);
	
	int del(StoreCollects storeCollects);
}
