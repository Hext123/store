package service;

import java.util.Map;

import entity.T;

public interface CommonService {

	String getID(Map<String, String> map);
	int addT(T t);
}
