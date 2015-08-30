package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StoreAddressDao;

import entity.StoreAddress;
import service.StoreAddressService;

@Service
@Transactional
public class StoreAddressServiceImpl implements StoreAddressService {

	@Autowired
	private StoreAddressDao storeAddressDao;

	public void add(StoreAddress storeAddress) {
		storeAddressDao.add(storeAddress);

	}

	public void del(int addressID) {
		storeAddressDao.del(addressID);

	}

	public List<StoreAddress> findByUserID(int userID) {
		return storeAddressDao.findByUserID(userID);
	}

	public void update(StoreAddress storeAddress) {

		storeAddressDao.update(storeAddress);

	}

}
