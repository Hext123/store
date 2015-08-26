package dao;

import java.util.List;

import entity.StoreProductTypes;

public interface StoreProductTypesDao {

	List<StoreProductTypes> find(StoreProductTypes storeProductTypes);
}
