package dao;

import java.util.List;

import entity.StoreOrders;

public interface StoreOrdersDao {
	List<StoreOrders> find();

	String add(StoreOrders storeOrders);
}
