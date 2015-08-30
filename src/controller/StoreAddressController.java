package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.StoreAddressService;

import com.alibaba.fastjson.JSON;

import entity.Message;
import entity.StoreAddress;

@Controller
@RequestMapping(value = "/StoreAddress")
public class StoreAddressController {
	@Autowired
	private StoreAddressService storeAddressService;

	@RequestMapping(value = "/addStoreAddress", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addStoreCollects(StoreAddress storeAddress) {
		try {
			storeAddressService.add(storeAddress);
			return JSON.toJSONString(new Message("添加成功", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("添加失败", 0));
		}
	}

	@RequestMapping(value = "/updateStoreAddress", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateStoreAddress(StoreAddress storeAddress) {
		try {
			storeAddressService.update(storeAddress);
			return JSON.toJSONString(new Message("修改成功", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("修改失败", 0));
		}
	}

	@RequestMapping(value = "/updateAddressDefault", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateAddressDefault(StoreAddress storeAddress) {
		try {
			storeAddressService.updateAddressDefault(storeAddress);
			return JSON.toJSONString(new Message("修改成功", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("修改失败", 0));
		}
	}

	@RequestMapping(value = "/delStoreAddress", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delStoreAddress(int addressID) {
		try {
			storeAddressService.del(addressID);
			return JSON.toJSONString(new Message("删除成功", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("删除失败", 0));
		}
	}

	@RequestMapping(value = "/findAddressByUserID", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findAddressByUserID(int userID) {
		List<StoreAddress> storeAddresses = storeAddressService
				.findByUserID(userID);
		return JSON.toJSONString(storeAddresses);

	}
}
