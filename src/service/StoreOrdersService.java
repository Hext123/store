package service;

import entity.StoreOrders;

public interface StoreOrdersService {
	String addOrderSimple(StoreOrders storeOrders, String productID,
			int buyCount);

	String addOrder(StoreOrders storeOrders, String[] shopCarID)
			throws Exception;
}
