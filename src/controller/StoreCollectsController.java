package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import entity.Message;
import entity.StoreCollects;
import entity.StoreProducts;

import service.StoreCollectsService;

@Controller
@RequestMapping(value = "/StoreCollects")
public class StoreCollectsController {
	@Autowired
	private StoreCollectsService storeCollectsService;

	@RequestMapping(value = "/addStoreCollects", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addStoreCollects(StoreCollects storeCollects) {
		int n = storeCollectsService.add(storeCollects);
		if (n > 0) {

			return JSON.toJSONString(new Message("收藏成功", n));
		} else {

			return JSON.toJSONString(new Message("收藏失败", n));
		}
	}

	@RequestMapping(value = "/StoreCollectsList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String StoreCollectsList(int userID, int pageIndex, int pageSize) {

		List<StoreProducts> StoreProducts = storeCollectsService.findByUserID(
				userID, pageIndex, pageSize);

		int count = storeCollectsService.count(userID);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("count", count);
		map.put("storeProducts", StoreProducts);

		return JSON.toJSONString(map);

	}

	@RequestMapping(value = "/checkCollectStatus", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkCollectStatus(StoreCollects storeCollects) {
		int n = storeCollectsService.checkCollectStatus(storeCollects);
		if (n > 0) {

			return JSON.toJSONString(new Message("已收藏", n));
		} else {
			return JSON.toJSONString(new Message("未收藏", n));
		}
	}

	@RequestMapping(value = "/delStoreCollects", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delStoreCollects(StoreCollects storeCollects) {
		int n = storeCollectsService.del(storeCollects);
		if (n > 0) {

			return JSON.toJSONString(new Message("删除收藏成功", n));
		} else {
			return JSON.toJSONString(new Message("删除收藏失败", n));
		}
	}
}
