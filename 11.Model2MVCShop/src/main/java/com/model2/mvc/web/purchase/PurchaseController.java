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

//==> 구매관리 Controller
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

	// meta-data 로 부터 값 받아옴. pageSize : 한 페이지에 목록 몇개, pageUnit : 몇 페이지씩 나눌건지
	// ==> classpath:config/common.properties , classpath:config/context-common.xml 참조 할것
	@Value("#{commonProperties['pageUnit'] ?: 5}") // 못 불러온다면 5 주입
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 3}") // 못 불러온다면 3 주입
	int pageSize;
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchase(@RequestParam("prod_no") int prodNo) throws Exception {
		
		System.out.println("/purchase/addPurchase : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo); // 구매할 상품 번호에 해당하는 상품 찾아옴 
		// Model 과 View 연결
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
		// Model 과 View 연결
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
		//purchase.setPurchaseProd(productService.getProduct(purchase.getPurchaseProd().getProdNo())); 필요시 추가 
		// Model 과 View 연결
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
		// Model 과 View 연결
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
		
		//return "forward:/getPurchase.do?tranNo="+purchase.getTranNo(); 오류남 (?) 
		return "forward:/purchase/getPurchase";
	}
	
	@RequestMapping("updateTranCodeByProd")
	public String updateTranCodeByProd(@ModelAttribute("purchase") Purchase purchase,
									   @RequestParam("prodNo") int prodNo) throws Exception {
		
		System.out.println("/purchase/updateTranCodeByProd");
		
		Product product = new Product();
		product.setProdNo(prodNo); // prodNo 기반으로 TranCode Update 
		purchase.setPurchaseProd(product);
		
		//Business Logic
		purchaseService.updateTranCode(purchase);
		
														// 추후 수정 
		return "forward:/product/listProduct?menu=manage&findby=sold";
	}
	
	@RequestMapping("updateTranCode")
	public String updateTranCode(@ModelAttribute("purchase") Purchase purchase) throws Exception {
		
		System.out.println("/purchase/updateTranCode");
		
		Product product = new Product(); 
		purchase.setPurchaseProd(product); // DAO 안에서 Null값 안 들어가기 위함 
		
		//Business Logic
		purchaseService.updateTranCode(purchase); // tranNo 기반으로 TranCode Update 
		
		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping("listPurchase")
	public String listPurchase(@ModelAttribute("search") Search search,
							   HttpSession session,
							   Model model) throws Exception {
								
		
		System.out.println("/purchase/listPurchase");
		
		User user = (User) session.getAttribute("user");
		
		// jsp를 거치지 않고 .do를 통해 여기로 왔을 때 첫 페이지를 1이라고 지정
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); // pageSize 지정 
		
		// Business logic 수행
		Map<String, Object> map = purchaseService.getPurchaseList(search, user.getUserId());
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); 이렇게 두개가 담겨 있음 ! 
		Page resultPage	= // 페이지 나누는 것을 추상화 & 캡슐화 한 Page 클래스 이용 
				new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
}
