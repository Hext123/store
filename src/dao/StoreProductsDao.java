package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.StoreProducts;

public interface StoreProductsDao {
	int add(StoreProducts storeProducts);

	int update(StoreProducts storeProducts);

	StoreProducts findByID(String productID);

	List<StoreProducts> find(@Param("storeProducts") StoreProducts storeProducts,@Param("pageIndex") int pageIndex,
			@Param("pageSize")int pageSize);

	List<Integer> findTypeInProductsByPMCID(int pmcID);

	int count(StoreProducts storeProducts);

}
