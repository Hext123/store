package controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.StoreOrdersService;

import com.alibaba.fastjson.JSON;

import entity.Message;
import entity.StoreOrders;

@Controller
@RequestMapping(value = "/StoreOrders")
public class StoreOrdersController {
	@Autowired
	private StoreOrdersService storeOrdersService;

	@RequestMapping(value = "/addOrderSimple", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addOrderSimple(StoreOrders storeOrders, String productID,
			int buyCount) {
		try {
			String orderID = storeOrdersService.addOrderSimple(storeOrders,
					productID, buyCount);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", new Message("生成订单成功", 1));
			map.put("orderID", orderID);
			return JSON.toJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", new Message("生成订单失败", 0));
			map.put("orderID", "");
			return JSON.toJSONString(map);
		}
	}

	@RequestMapping(value = "/addOrder", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addOrder(StoreOrders storeOrders, String[] shopCarID) {
		try {
			String orderID = storeOrdersService
					.addOrder(storeOrders, shopCarID);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", new Message("生成订单成功", 1));
			map.put("orderID", orderID);

			return JSON.toJSONString(map);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", new Message("生成订单失败", 0));
			map.put("orderID", "");
			return JSON.toJSONString(map);
		}
	}

	@RequestMapping(value = "/updateOrderState", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateOrderState(StoreOrders storeOrders) {
		try {
			int n = storeOrdersService.updateOrderState(storeOrders);
			if (n == 0) {
				throw new Exception("修改订单状态失败");
			}
			return JSON.toJSONString(new Message("修改订单状态成功", 1));
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Message("修改订单状态失败", 0));
		}
	}
}
