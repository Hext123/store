package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreCollectsDao;

import entity.StoreCollects;
import entity.StoreProducts;
import service.StoreCollectsService;

@Service
@Transactional
public class StoreCollectsServiceImpl implements StoreCollectsService {

	@Autowired
	private StoreCollectsDao storeCollectsDao;

	public int add(StoreCollects storeCollects) {
		return storeCollectsDao.add(storeCollects);
	}

	public int count(int userID) {
		return storeCollectsDao.count(userID);
	}

	public int checkCollectStatus(StoreCollects storeCollects) {
		// TODO Auto-generated method stub
		return storeCollectsDao.checkCollectStatus(storeCollects);
	}

	public int del(StoreCollects storeCollects) {
		// TODO Auto-generated method stub
		return storeCollectsDao.del(storeCollects);
	}

	public List<StoreProducts> findByUserID(int userID, int pageIndex,
			int pageSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userID", userID);
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		return storeCollectsDao.findByUserID(map);
	}

}
