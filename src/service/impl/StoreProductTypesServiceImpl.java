package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreProductTypesDao;

import entity.StoreProductTypes;
import service.StoreProductTypesService;

@Service
@Transactional
public class StoreProductTypesServiceImpl implements StoreProductTypesService {
	@Autowired
	private StoreProductTypesDao storeProductTypesDao;

	public List<StoreProductTypes> find(StoreProductTypes storeProductTypes) {
		return storeProductTypesDao.find(storeProductTypes);
	}

}
