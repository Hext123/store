package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import entity.Message;
import entity.StoreShopCar;

import service.StoreShopCarService;

@Controller
@RequestMapping(value = "/StoreShopCar")
public class StoreShopCarController {
	@Autowired
	private StoreShopCarService storeShopCarService;

	@RequestMapping(value = "/addStoreShopCar", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addStoreShopCar(StoreShopCar storeShopCar) {
		try {
			storeShopCarService.add(storeShopCar);
			return JSON.toJSONString(new Message("��ӹ��ﳵ�ɹ�", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("��ӹ��ﳵʧ��", 0));
		}
	}

	@RequestMapping(value = "/delStoreShopCar", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delStoreShopCar(StoreShopCar storeShopCar) {
		try {
			storeShopCarService.del(storeShopCar);
			return JSON.toJSONString(new Message("ɾ���ɹ�", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("ɾ��ʧ��", 0));
		}
	}

	@RequestMapping(value = "/updateStoreShopCar", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateStoreShopCar(StoreShopCar storeShopCar) {
		try {
			storeShopCarService.update(storeShopCar);
			return JSON.toJSONString(new Message("�޸ĳɹ�", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("�޸�ʧ��", 0));
		}
	}

	@RequestMapping(value = "/findShopCarByUserID", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findShopCarByUserID(int userID, int pageIndex, int pageSize) {

		List<StoreShopCar> storeShopCars = storeShopCarService.findByUserID(
				userID, pageIndex, pageSize);
		return JSON.toJSONString(storeShopCars);

	}
}
