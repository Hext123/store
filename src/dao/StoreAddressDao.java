package dao;

import java.util.List;

import entity.StoreAddress;

public interface StoreAddressDao {
	void add(StoreAddress storeAddress);

	void update(StoreAddress storeAddress);

	void del(int addressID);

	List<StoreAddress> findByUserID(int userID);
}
