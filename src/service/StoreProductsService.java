package service;

import java.util.List;

import entity.StoreProducts;

public interface StoreProductsService {

	int add(StoreProducts storeProducts);

	int update(StoreProducts storeProducts);

	StoreProducts findByID(String productID);

	List<StoreProducts> find(StoreProducts storeProducts,int pageIndex,
			int pageSize);
	
	List<Integer> findTypeInProductsByPMCID(int pmcID);

	int count(StoreProducts storeProducts);
}
