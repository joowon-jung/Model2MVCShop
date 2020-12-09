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

//==> ������� Controller
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
	
	// meta-data �� ���� �� �޾ƿ�. pageSize : �� �������� ��� �, pageUnit : �� �������� �������� 
	//==> classpath:config/common.properties  ,  classpath:config/context-common.xml ���� �Ұ�
	@Value("#{commonProperties['pageUnit'] ?: 5}") // �� �ҷ��´ٸ� 5 ���� 
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 3}") // �� �ҷ��´ٸ� 3 ���� 
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
		
		// jsp�� ��ġ�� �ʰ� .do�� ���� ����� ���� �� ù �������� 1�̶�� ����
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); // pageSize ����
		
		// Business logic ����
		Map<String, Object> map = reviewService.getReviewList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); �̷��� �ΰ��� ��� ���� ! 
		Page resultPage	= // ������ ������ ���� �߻�ȭ & ĸ��ȭ �� Page Ŭ���� �̿� 
				new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list")); // ������ Ŭ������ �� ��Ÿ���� ��ǰ ������ ������� ��
		model.addAttribute("resultPage", resultPage); // ȭ����� ������ ������ �� ������� 
		model.addAttribute("search", search); // �˻� ������ ������� 
		
		return "forward:/review/listReview.jsp";
	}
}