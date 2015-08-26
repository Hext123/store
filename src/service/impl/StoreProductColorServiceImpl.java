package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreProductColorDao;

import entity.StoreProductColor;
import service.StoreProductColorService;

@Service
@Transactional
public class StoreProductColorServiceImpl implements StoreProductColorService {
	@Autowired
	private StoreProductColorDao storeProductColorDao;

	public List<StoreProductColor> findAll() {
		return storeProductColorDao.findAll();
	}

}
