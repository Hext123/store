package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreOrdersDao;
import dao.StoreShopCarDao;

import entity.StoreOrders;
import service.StoreOrdersService;

@Service
@Transactional
public class StoreOrdersServiceImpl implements StoreOrdersService {
	@Autowired
	private StoreShopCarDao storeShopCarDao;
	@Autowired
	private StoreOrdersDao storeOrdersDao;

	public String addOrder(StoreOrders storeOrders, String[] shopCarID)
			throws Exception {

		storeShopCarDao.updateShopCarNotSelectedAll(storeOrders.getUserID());
		for (String id : shopCarID) {
			int n = storeShopCarDao.updateShopCarSelected(id);
			if (n == 0) {
				storeShopCarDao.updateShopCarNotSelectedAll(storeOrders
						.getUserID());
				throw new Exception("购物车商品异常!");
			}
		}
		String orderID = storeOrdersDao.addOrder(storeOrders);

		return orderID;
	}

	public String addOrderSimple(StoreOrders storeOrders, String productID,
			int buyCount) {
		return storeOrdersDao.addOrderSimple(storeOrders, productID, buyCount);
	}

	public int updateOrderState(StoreOrders storeOrders) {
		return storeOrdersDao.updateOrderState(storeOrders);
	}

}
