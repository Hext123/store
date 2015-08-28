package service;

import java.util.List;

import entity.StoreShopCar;

public interface StoreShopCarService {
	void add(StoreShopCar storeShopCar);

	void del(StoreShopCar storeShopCar);

	void update(StoreShopCar storeShopCar);

	List<StoreShopCar> findByUserID(int userID, int pageIndex, int pageSize);
}
