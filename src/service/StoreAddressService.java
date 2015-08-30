package service;

import java.util.List;

import entity.StoreAddress;

public interface StoreAddressService {
	void add(StoreAddress storeAddress);

	void update(StoreAddress storeAddress);

	void del(int addressID);

	void updateAddressDefault(StoreAddress storeAddress);
	
	List<StoreAddress> findByUserID(int userID);
}
