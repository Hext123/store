package dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import entity.StoreShopCar;

public interface StoreShopCarDao {
	void add(StoreShopCar storeShopCar);

	void del(StoreShopCar storeShopCar);

	void update(StoreShopCar storeShopCar);

	List<StoreShopCar> findByUserID(
			@Param("userID") int userID,
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}
