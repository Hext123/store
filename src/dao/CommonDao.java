package dao;

import java.util.Map;

import entity.T;

public interface CommonDao {
	String getID(Map<String, String> map);
	
	int addT(T t);
}
