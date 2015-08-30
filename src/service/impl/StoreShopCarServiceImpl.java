package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreShopCarDao;

import entity.StoreProducts;
import entity.StoreShopCar;
import service.StoreShopCarService;

@Service
@Transactional
public class StoreShopCarServiceImpl implements StoreShopCarService {
	@Autowired
	private StoreShopCarDao storeShopCarDao;

	public void add(StoreShopCar storeShopCar) {
		storeShopCarDao.add(storeShopCar);
	}

	public void del(StoreShopCar storeShopCar) {
		storeShopCarDao.del(storeShopCar);
	}

	public void update(StoreShopCar storeShopCar) {
		storeShopCarDao.update(storeShopCar);

	}

	public List<StoreProducts> findByUserID(int userID, int pageIndex,
			int pageSize) {
		return storeShopCarDao.findByUserID(userID, pageIndex, pageSize);
	}

	public int count(int userID) {
		return storeShopCarDao.count(userID);
	}

}
