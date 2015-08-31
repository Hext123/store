package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.StoreOrders;

public interface StoreOrdersDao {
	List<StoreOrders> find();

	String addOrderSimple(@Param("o") StoreOrders storeOrders,
			@Param("productID") String productID,
			@Param("buyCount") int buyCount);

	String addOrder(StoreOrders storeOrders);

	int updateOrderState(StoreOrders storeOrders);

}
