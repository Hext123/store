package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreProductUnitDao;

import entity.StoreProductUnit;
import service.StoreProductUnitService;

@Service
@Transactional
public class StoreProductUnitServiceImpl implements StoreProductUnitService {
	@Autowired
	private StoreProductUnitDao storeProductUnitDao;

	public List<StoreProductUnit> findAll() {
		return storeProductUnitDao.findAll();
	}

}
