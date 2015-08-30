package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import entity.StoreProductColor;
import entity.StoreProductSpec;
import entity.StoreProductTypes;
import entity.StoreProductUnit;
import entity.StoreProducts;

import service.CommonService;
import service.StorePMCService;
import service.StoreProductColorService;
import service.StoreProductSpecService;
import service.StoreProductTypesService;
import service.StoreProductUnitService;
import service.StoreProductsService;

@Controller
@RequestMapping(value = "/StoreProduct")
public class StoreProductController {
	@Autowired
	private StoreProductColorService storeProductColorService;
	@Autowired
	private StoreProductTypesService storeProductTypesService;
	@Autowired
	private StoreProductSpecService storeProductSpecService;
	@Autowired
	private StoreProductUnitService storeProductUnitService;
	@Autowired
	private StoreProductsService storeProductsService;
	@Autowired
	private StorePMCService storePMCService;
	@Autowired
	private CommonService commonService;

	static int defaultPMCID = 1;
	static int NormalProductState = 1;
	static int OffShelvesProductState = 0;
	static int orderByDesc = 1;
	static int orderByAsc = 0;
	static int AllTypeParentID = -1;

	/***************** web ************************/
	/**
	 * ��ѯ��Ʒ
	 * 
	 * @param storeProducts
	 * @return
	 */
	@RequestMapping(value = "/findStoreProduct", produces = "text/html;charset=UTF-8")
	public ModelAndView findStoreProduct(StoreProducts storeProducts) {

		storeProducts.setOrder("ProductDate");
		storeProducts.setDesc(orderByDesc);
		storeProducts.setPmcID(defaultPMCID);
		List<StoreProducts> storeProductsList = storeProductsService.find(
				storeProducts, 1, 100);
		List<StoreProductTypes> storeProductTypesList = storeProductTypesService
				.find(new StoreProductTypes(AllTypeParentID, defaultPMCID));

		ModelAndView mv = new ModelAndView("/StoreProductsList.jsp");

		mv.addObject("storeProductsList", storeProductsList);
		mv.addObject("storeProductTypesList", storeProductTypesList);
		return mv;
	}

	/**
	 * �����Ʒҳ��ĳ�ʼ��
	 * 
	 * @param storeProducts
	 * @return
	 */
	@RequestMapping(value = "/initAddStoreProduct", produces = "text/html;charset=UTF-8")
	public ModelAndView initAddStoreProduct(StoreProducts storeProducts) {
		List<StoreProductTypes> storeProductTypesList = storeProductTypesService
				.find(new StoreProductTypes(AllTypeParentID, defaultPMCID));
		List<StoreProductColor> storeProductColorsList = storeProductColorService
				.findAll();
		List<StoreProductSpec> storeProductSpecsList = storeProductSpecService
				.findAll();
		List<StoreProductUnit> storeProductUnitsList = storeProductUnitService
				.findAll();

		ModelAndView mv = new ModelAndView("/addStoreProducts.jsp");

		mv.addObject("storeProductTypesList", storeProductTypesList);
		mv.addObject("storeProductColorsList", storeProductColorsList);
		mv.addObject("storeProductSpecsList", storeProductSpecsList);
		mv.addObject("storeProductUnitsList", storeProductUnitsList);
		return mv;
	}

	/**
	 * �����Ʒ
	 * 
	 * @param storeProducts
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addStoreProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addStoreProduct(
			StoreProducts storeProducts,
			@RequestParam(value = "file", required = false) CommonsMultipartFile[] files,
			HttpServletRequest request) {

		try {
			StringBuffer productImages = upload(files, request.getSession()
					.getServletContext().getRealPath("/images")
					+ "/");
			Map<String, String> map = new HashMap<String, String>();
			map.put("tbName", "Store_Products");
			map.put("keyName", "ProductID");
			map.put("qz", "SP");
			String id = commonService.getID(map);
			storeProducts.setProductID(id);
			storeProducts.setProductImages(stringRidComma(productImages));
			storeProducts.setProductDate(new Date());
			storeProducts.setProductState(NormalProductState);
			storeProducts.setPmcID(defaultPMCID);
			int n = storeProductsService.add(storeProducts);
			if (n == 0) {
				throw new Exception("���ݿ����ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "<script>alert('���ʧ�ܣ�');history.back();</script>";

		}
		return "<script>alert('��ӳɹ���');location.href='findStoreProduct';</script>";
	}

	/**
	 * �ϴ�
	 * 
	 * @param files
	 * @param path
	 * @return
	 * @throws IOException
	 */
	StringBuffer upload(CommonsMultipartFile[] files, String path)
			throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		StringBuffer sb = new StringBuffer();
		if (files != null) {

			for (int i = 0; i < files.length; i++) {
				System.out.println("fileName---------->"
						+ files[i].getOriginalFilename());

				if (!files[i].isEmpty()) {

					String fileName = "SP"
							+ sdf.format(new Date())
							+ i
							+ files[i].getOriginalFilename().substring(
									files[i].getOriginalFilename().lastIndexOf(
											"."));

					sb.append(fileName);
					sb.append(",");

					fileName = path + fileName;

					File file = new File(fileName);

					files[i].transferTo(file);

				}
			}
		}
		return sb;
	}

	String stringRidComma(StringBuffer sb) {
		String imgs = sb.toString();
		if (imgs.charAt(imgs.length() - 1) == ',') {
			imgs = imgs.substring(0, imgs.length() - 1);
		}
		return imgs;
	}

	/**
	 * ��Ʒ����
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findStoreProductByID", produces = "text/html;charset=UTF-8")
	public ModelAndView findStoreProductByID(String id) {

		StoreProducts storeProducts = storeProductsService.findByID(id);

		String productImages = storeProducts.getProductImages();
		String imgs[] = productImages.split(",");

		ModelAndView mv = new ModelAndView("/StoreProductsDetails.jsp");

		mv.addObject("p", storeProducts);
		mv.addObject("imgs", imgs);
		return mv;
	}

	/**
	 * �޸���Ʒҳ��ĳ�ʼ��
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/initUpdateStoreProduct", produces = "text/html;charset=UTF-8")
	public ModelAndView initUpdateStoreProduct(String id) {
		List<StoreProductTypes> storeProductTypesList = storeProductTypesService
				.find(new StoreProductTypes(AllTypeParentID, defaultPMCID));
		List<StoreProductColor> storeProductColorsList = storeProductColorService
				.findAll();
		List<StoreProductSpec> storeProductSpecsList = storeProductSpecService
				.findAll();
		List<StoreProductUnit> storeProductUnitsList = storeProductUnitService
				.findAll();

		StoreProducts storeProducts = storeProductsService.findByID(id);
		String productImages = storeProducts.getProductImages();
		String imgs[] = productImages.split(",");

		ModelAndView mv = new ModelAndView("/updateStoreProducts.jsp");

		mv.addObject("storeProductTypesList", storeProductTypesList);
		mv.addObject("storeProductColorsList", storeProductColorsList);
		mv.addObject("storeProductSpecsList", storeProductSpecsList);
		mv.addObject("storeProductUnitsList", storeProductUnitsList);
		mv.addObject("storeProducts", storeProducts);

		mv.addObject("imgs", imgs);
		return mv;
	}

	/**
	 * �޸���Ʒ
	 * 
	 * @param storeProducts
	 * @param files
	 * @param imgs
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStoreProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateStoreProduct(
			StoreProducts storeProducts,
			@RequestParam(value = "file", required = false) CommonsMultipartFile[] files,
			String[] imgs, HttpServletRequest request) {

		try {
			StringBuffer productImages = upload(files, request.getSession()
					.getServletContext().getRealPath("/images")
					+ "/");
			for (String img : imgs) {
				productImages.append(img);
				productImages.append(",");
			}

			storeProducts.setProductImages(stringRidComma(productImages));

			storeProducts.setProductState(NormalProductState);
			int n = storeProductsService.update(storeProducts);
			if (n == 0) {
				throw new Exception("���ݿ��޸�ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "<script>alert('�޸�ʧ�ܣ�');history.back();</script>";
		}
		return "<script>alert('�޸ĳɹ���');location.href='findStoreProduct';</script>";
	}

	/**
	 * �¼���Ʒ
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/offShelvesStoreProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String offShelvesStoreProduct(String id) {

		try {
			StoreProducts storeProducts = storeProductsService.findByID(id);
			if (storeProducts == null) {
				throw new Exception("δ�ҵ�Ҫ�¼ܵ���Ʒ");
			}
			storeProducts.setProductState(OffShelvesProductState);
			int n = storeProductsService.update(storeProducts);
			if (n == 0) {
				throw new Exception("���ݿ��޸�ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "<script>alert('�¼�ʧ�ܣ�');</script>";
		}
		return "<script>alert('�¼ܳɹ���');location.replace(document.referrer);</script>";
	}

	/*************************** APP���ݽӿ� *******************************/

	/**
	 * ��Ʒ����
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getStoreProductByID", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getStoreProductByID(String id) {

		StoreProducts storeProducts = storeProductsService.findByID(id);
		return JSON.toJSONString(storeProducts);
	}

	/**
	 * ��ѯ��Ʒjson�ӿ�
	 * 
	 * @param storeProducts
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findStoreProductList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findStoreProductList(
			@ModelAttribute StoreProducts storeProducts, int pageIndex,
			int pageSize) {
		
		System.out.println("================="+storeProducts);
		
		if (storeProducts.getOrder() == null
				|| storeProducts.getOrder().equals("")) {
			storeProducts.setOrder("ProductDate");
		}
		List<StoreProducts> storeProductsList = storeProductsService.find(
				storeProducts, pageIndex, pageSize);
		int count = storeProductsService.count(storeProducts);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("count", count);
		map.put("storeProducts", storeProductsList);

		return JSON.toJSONString(map);
	}

	/**
	 * �̳���ҳ����
	 * 
	 * @param pmcID
	 * @return
	 */
	@RequestMapping(value = "/StoreHomePage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String StoreHomePage(int pmcID) {
		String[] adImages = { "SP201508211426164920.jpg",
				"SP201508211426167071.jpg" };

		List<StoreProductTypes> productTypes = storeProductTypesService
				.find(new StoreProductTypes(0, pmcID));

		List<StoreProducts> recommends = storeProductsService.find(
				new StoreProducts("", 0, pmcID, NormalProductState,
						"ProductSaleCount", orderByDesc), 1, 3);

		List<Integer> types = storeProductsService
				.findTypeInProductsByPMCID(pmcID);
		List<StoreProducts> hotSelling = new ArrayList<StoreProducts>();
		for (Integer type : types) {
			List<StoreProducts> StoreProductsTemp = storeProductsService.find(
					new StoreProducts("", type, pmcID, NormalProductState,
							"ProductSaleCount", orderByDesc), 1, 5);
			hotSelling.addAll(StoreProductsTemp);
		}

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("adImages", adImages);
		map.put("productTypes", productTypes);
		map.put("recommends", recommends);
		map.put("hotSelling", hotSelling);

		return JSON.toJSONString(map);
	}
}
