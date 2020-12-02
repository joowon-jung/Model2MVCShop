package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

//==> ���Ű��� Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}

	// meta-data �� ���� �� �޾ƿ�. pageSize : �� �������� ��� �, pageUnit : �� �������� ��������
	// ==> classpath:config/common.properties , classpath:config/context-common.xml ���� �Ұ�
	@Value("#{commonProperties['pageUnit'] ?: 5}") // �� �ҷ��´ٸ� 5 ����
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 3}") // �� �ҷ��´ٸ� 3 ����
	int pageSize;
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchase(@RequestParam("prod_no") int prodNo) throws Exception {
		
		System.out.println("/purchase/addPurchase : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo); // ������ ��ǰ ��ȣ�� �ش��ϴ� ��ǰ ã�ƿ� 
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", product);
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
									@RequestParam("prodNo") int prodNo,
									HttpSession session) throws Exception {
		
		System.out.println("/purchase/addPurchase : POST");
		//Business Logic
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setBuyer((User) session.getAttribute("user"));
		purchase.setTranCode("1");

		purchaseService.addPurchase(purchase);
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("getPurchase")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception {
		
		System.out.println("/purchase/getPurchase");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		//purchase.setPurchaseProd(productService.getProduct(purchase.getPurchaseProd().getProdNo())); �ʿ�� �߰� 
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.GET)
	public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo) throws Exception {
		
		System.out.println("/purchase/updatePurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model �� View ����
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception {
		
		System.out.println("/purchase/updatePurchase : POST");
		//Business Logic
		purchaseService.updatePurchase(purchase);
		
		//return "forward:/getPurchase.do?tranNo="+purchase.getTranNo(); ������ (?) 
		return "forward:/purchase/getPurchase";
	}
	
	@RequestMapping("updateTranCodeByProd")
	public String updateTranCodeByProd(@ModelAttribute("purchase") Purchase purchase,
									   @RequestParam("prodNo") int prodNo) throws Exception {
		
		System.out.println("/purchase/updateTranCodeByProd");
		
		Product product = new Product();
		product.setProdNo(prodNo); // prodNo ������� TranCode Update 
		purchase.setPurchaseProd(product);
		
		//Business Logic
		purchaseService.updateTranCode(purchase);
		
														// ���� ���� 
		return "forward:/product/listProduct?menu=manage&findby=sold";
	}
	
	@RequestMapping("updateTranCode")
	public String updateTranCode(@ModelAttribute("purchase") Purchase purchase) throws Exception {
		
		System.out.println("/purchase/updateTranCode");
		
		Product product = new Product(); 
		purchase.setPurchaseProd(product); // DAO �ȿ��� Null�� �� ���� ���� 
		
		//Business Logic
		purchaseService.updateTranCode(purchase); // tranNo ������� TranCode Update 
		
		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping("listPurchase")
	public String listPurchase(@ModelAttribute("search") Search search,
							   HttpSession session,
							   Model model) throws Exception {
								
		
		System.out.println("/purchase/listPurchase");
		
		User user = (User) session.getAttribute("user");
		
		// jsp�� ��ġ�� �ʰ� .do�� ���� ����� ���� �� ù �������� 1�̶�� ����
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); // pageSize ���� 
		
		// Business logic ����
		Map<String, Object> map = purchaseService.getPurchaseList(search, user.getUserId());
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); �̷��� �ΰ��� ��� ���� ! 
		Page resultPage	= // ������ ������ ���� �߻�ȭ & ĸ��ȭ �� Page Ŭ���� �̿� 
				new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
}
