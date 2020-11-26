package com.model2.mvc.web.review;

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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.review.ReviewService;
import com.model2.mvc.service.user.UserService;

//==> 리뷰관리 Controller
@Controller
@RequestMapping("/review/*")
public class ReviewController {

	///Field
	@Autowired
	@Qualifier("reviewServiceImpl")
	private ReviewService reviewService;
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	// meta-data 로 부터 값 받아옴. pageSize : 한 페이지에 목록 몇개, pageUnit : 몇 페이지씩 나눌건지 
	//==> classpath:config/common.properties  ,  classpath:config/context-common.xml 참조 할것
	@Value("#{commonProperties['pageUnit'] ?: 5}") // 못 불러온다면 5 주입 
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 3}") // 못 불러온다면 3 주입 
	int pageSize;
	
	@RequestMapping(value="addReview", method=RequestMethod.GET)
	public String addReview(@RequestParam("prodNo") int prodNo,
								@RequestParam("tranNo") int tranNo,
								Model model) throws Exception {
		
		model.addAttribute("product", productService.getProduct(prodNo));
		model.addAttribute("purchase", purchaseService.getPurchase(tranNo));
		
		return "forward:/review/addReviewView.jsp";
	}
	
	@RequestMapping(value="addReview", method=RequestMethod.POST)
	public String addReview(@RequestParam("prodNo") int prodNo,
							@RequestParam("tranNo") int tranNo,
							@ModelAttribute("review") Review review,
							HttpSession session) throws Exception {
		
		review.setWriter((User)session.getAttribute("user"));
		review.setReviewProd(productService.getProduct(prodNo));
		review.setReviewPurchase(purchaseService.getPurchase(tranNo));
		
		reviewService.addReview(review);
		
		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping("getReview")
	public String getReview(@RequestParam("tranNo") int tranNo,
							@RequestParam("menu") String menu,
							Model model) throws Exception {
	
		Review review = reviewService.getReview(tranNo);
		review.setReviewPurchase(purchaseService.getPurchase(tranNo));
		review.setReviewProd(productService.getProduct(review.getReviewProd().getProdNo()));
		
		if (menu.equals("all")) {
			reviewService.updateCount(tranNo);
		}
		
		model.addAttribute("review", review);
		model.addAttribute("menu", menu);
		
		return "forward:/review/getReview.jsp";
	}
	
	@RequestMapping(value="updateReview", method=RequestMethod.GET)
	public String updateReview(@RequestParam("prodNo") int prodNo,
								@RequestParam("tranNo") int tranNo,
								Model model) throws Exception {
		
		model.addAttribute("product", productService.getProduct(prodNo));
		model.addAttribute("purchase", purchaseService.getPurchase(tranNo));
		model.addAttribute("review", reviewService.getReview(tranNo));
		
		return "forward:/review/updateReviewView.jsp";
	}
	
	@RequestMapping(value="updateReview", method=RequestMethod.POST)
	public String updateReview(@ModelAttribute("review") Review review) throws Exception {
	
		System.out.println("/review/updateReview");
		System.out.println(review);
		
		reviewService.updateReview(review);
		
		return "forward:/purchase/listPurchase";
	}
	
	@RequestMapping("listReview")
	public String listReview(@ModelAttribute("search") Search search,
							Model model) throws Exception {
		
		System.out.println("/review/listReview");
		
		// jsp를 거치지 않고 .do를 통해 여기로 왔을 때 첫 페이지를 1이라고 지정
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); // pageSize 지정
		
		// Business logic 수행
		Map<String, Object> map = reviewService.getReviewList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); 이렇게 두개가 담겨 있음 ! 
		Page resultPage	= // 페이지 나누는 것을 추상화 & 캡슐화 한 Page 클래스 이용 
				new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list")); // 페이지 클릭했을 때 나타나는 제품 정보가 담겨있을 것
		model.addAttribute("resultPage", resultPage); // 화면상의 페이지 정보가 다 담겨있음 
		model.addAttribute("search", search); // 검색 정보가 담겨있음 
		
		return "forward:/review/listReview.jsp";
	}
}
