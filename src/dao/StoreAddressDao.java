package dao;

import java.util.List;

import entity.StoreAddress;

public interface StoreAddressDao {
	void add(StoreAddress storeAddress);

	void update(StoreAddress storeAddress);

	void del(int addressID);

	void updateAddressDefault(StoreAddress storeAddress);

	List<StoreAddress> findByUserID(int userID);

	StoreAddress findDefaultAddressByUserID(int userID);

}
