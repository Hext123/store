package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreProductSpecDao;

import entity.StoreProductSpec;
import service.StoreProductSpecService;

@Service
@Transactional
public class StoreProductSpecServiceImpl implements StoreProductSpecService {
	@Autowired
	private StoreProductSpecDao storeProductSpecDao;

	public List<StoreProductSpec> findAll() {
		return storeProductSpecDao.findAll();
	}

}
