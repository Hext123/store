package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.T;

import service.CommonService;

@Controller
public class TC {
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "/addT", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addStoreCollects(T t) {
		int n = commonService.addT(t);
		if (n > 0) {
			return "<script>alert('添加成功！');history.back();</script>";
		} else {
			return "<script>alert('添加失败！');history.back();</script>";
		}
	}
}
