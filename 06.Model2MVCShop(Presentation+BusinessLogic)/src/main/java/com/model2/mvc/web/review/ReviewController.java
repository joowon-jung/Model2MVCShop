package com.model2.mvc.web.review;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("/addReviewView.do")
	public String addReviewView(@RequestParam("prodNo") int prodNo,
								@RequestParam("tranNo") int tranNo,
								Model model) throws Exception {
		
		model.addAttribute("product", productService.getProduct(prodNo));
		model.addAttribute("purchase", purchaseService.getPurchase(tranNo));
		
		return "forward:/review/addReviewView.jsp";
	}
	
	@RequestMapping("/addReview.do")
	public String addReview(@RequestParam("prodNo") int prodNo,
							@RequestParam("tranNo") int tranNo,
							@ModelAttribute("review") Review review,
							HttpSession session) throws Exception {
		
		review.setWriter((User)session.getAttribute("user"));
		review.setReviewProd(productService.getProduct(prodNo));
		review.setReviewPurchase(purchaseService.getPurchase(tranNo));
		
		reviewService.addReview(review);
		
		return "forward:/listPurchase.do";
	}
	
	@RequestMapping("/getReview.do")
	public String getReview(@RequestParam("tranNo") int tranNo,
							Model model) throws Exception {
	
		Review review = reviewService.getReview(tranNo);
		review.setReviewPurchase(purchaseService.getPurchase(tranNo));
		review.setReviewProd(productService.getProduct(review.getReviewProd().getProdNo()));
		model.addAttribute("review", review);
		
		return "forward:/review/getReview.jsp";
	}
	
	@RequestMapping("/updateReviewView.do")
	public String updateReviewView(@RequestParam("prodNo") int prodNo,
								@RequestParam("tranNo") int tranNo,
								Model model) throws Exception {
		
		model.addAttribute("product", productService.getProduct(prodNo));
		model.addAttribute("purchase", purchaseService.getPurchase(tranNo));
		model.addAttribute("review", reviewService.getReview(tranNo));
		
		return "forward:/review/updateReviewView.jsp";
	}
	
	@RequestMapping("/updateReview.do")
	public String updateReview(@ModelAttribute("review") Review review) throws Exception {
	
		System.out.println("/updateReview.do");
		System.out.println(review);
		
		reviewService.updateReview(review);
		
		return "forward:/listPurchase.do";
	}
}
