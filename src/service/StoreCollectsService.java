package service;

import java.util.List;

import entity.StoreCollects;
import entity.StoreProducts;

public interface StoreCollectsService {
	int add(StoreCollects storeCollects);

	List<StoreProducts> findByUserID(int userID, int pageIndex, int pageSize);

	int count(int userID);

	int checkCollectStatus(StoreCollects storeCollects);

	int del(StoreCollects storeCollects);
}
