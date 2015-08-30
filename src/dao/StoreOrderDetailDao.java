package dao;

import java.util.List;

import entity.StoreOrderDetail;

public interface StoreOrderDetailDao {
	List<StoreOrderDetail> findByOrderID(String orderID);

	void add(StoreOrderDetail storeOrderDetail);
}
