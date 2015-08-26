package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreProductsDao;

import entity.StoreProducts;
import service.StoreProductsService;

@Service
@Transactional
public class StoreProductsServiceImpl implements StoreProductsService {

	@Autowired
	private StoreProductsDao storeProductsDao;

	public int add(StoreProducts storeProducts) {
		return storeProductsDao.add(storeProducts);
	}

	public StoreProducts findByID(String productID) {
		return storeProductsDao.findByID(productID);
	}

	public int update(StoreProducts storeProducts) {
		return storeProductsDao.update(storeProducts);
	}

	public List<Integer> findTypeInProductsByPMCID(int pmcID) {
		return storeProductsDao.findTypeInProductsByPMCID(pmcID);
	}

	public List<StoreProducts> find(StoreProducts storeProducts, int pageIndex,
			int pageSize) {
		return storeProductsDao.find(storeProducts, pageIndex, pageSize);
	}

	public int count(StoreProducts storeProducts) {
		return storeProductsDao.count(storeProducts);
	}

}
