package service;

import java.util.List;

import entity.StoreProducts;
import entity.StoreShopCar;

public interface StoreShopCarService {
	void add(StoreShopCar storeShopCar);

	void del(StoreShopCar storeShopCar);

	void update(StoreShopCar storeShopCar);

	int count(int userID);
	List<StoreProducts> findByUserID(int userID, int pageIndex, int pageSize);
}
