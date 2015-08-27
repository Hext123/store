package service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.CommonDao;
import entity.T;

import service.CommonService;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;

	public String getID(Map<String, String> map) {
		return commonDao.getID(map);
	}

	public int addT(T t) {
		return commonDao.addT(t);
	}

	

}
