package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StorePMCDao;

import entity.StorePMC;
import service.StorePMCService;

@Service
@Transactional
public class StorePMCServiceImpl implements StorePMCService {
	@Autowired
	private StorePMCDao storePMCDao;

	public List<StorePMC> findAll() {
		return storePMCDao.findAll();
	}

}
