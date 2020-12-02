package com.model2.mvc.web.product;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.sun.media.jfxmedia.logging.Logger;

//==> 상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {

	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass()); // 디버깅 위함
	}
	
	// meta-data 로 부터 값 받아옴. pageSize : 한 페이지에 목록 몇개, pageUnit : 몇 페이지씩 나눌건지 
	//==> classpath:config/common.properties  ,  classpath:config/context-common.xml 참조 할것
	@Value("#{commonProperties['pageUnit'] ?: 5}") // 못 불러온다면 5 주입 
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 3}") // 못 불러온다면 3 주입 
	int pageSize;
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	@RequestMapping("addProduct") // 안 써줘도 되는데 가독성을 위해 
	public String addProduct(@ModelAttribute("product") Product product, 
							 @RequestParam("uploadFileName") MultipartFile file,
							 Model model) throws Exception {
		
		System.out.println("/product/addProduct");
		
		//===================== 파일 업로드 ========================
		System.out.println("파일 이름 : " + file.getOriginalFilename());
		String fileName = "";
		
		if(!(file.isEmpty())) { // 파일 선택 했으면 
			fileName = file.getOriginalFilename();
			File target = new File(uploadPath, fileName);
			FileCopyUtils.copy(file.getBytes(), target);
		}
				
		//Business Logic
		product.setFileName(fileName);
		product.setManuDate(product.getManuDate().replace("-", ""));
		productService.addProduct(product);
		// Model 과 View 연결
		model.addAttribute("vo", product);
		
		return "forward:/product/readProduct.jsp"; 
	}
	
	@RequestMapping("getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, 
							 @RequestParam(value="menu", required=false) String menu,
							 Model model,
							 HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		
		System.out.println("/product/getProduct");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("vo", product);
		model.addAttribute("menu", menu);
		
		// 최근 본 상품 구현 => 추후 코드 수정할 것 
		if (menu.equals("search")) { // 상품 검색 페이지 => 상품 상세보기 누르면 => 내가 본 상품에 담김

			Cookie[] cookies = request.getCookies(); // 현재 쿠키 받아옴 (세션 아이디 저장되어있는 쿠키 있으니까 반드시 한개이상 있을 것)

			int count = 0;

			for (int i = 0; i < cookies.length; i++) { // 쿠키 하나씩 받아오기 위한 for문

				if (cookies[i].getName().equals("history")) { // 쿠키 이름이 history 인게 있으면
					count++;
				}

				if (count == 1) {
					// 기존의 쿠키 value에 , 으로 구분해서 추가
					System.out.println("쿠키에 담겼음");
					System.out.println(cookies[i].getValue() + "," + prodNo);
					response.addCookie(new Cookie("history", cookies[i].getValue() + URLEncoder.encode("," + prodNo)));
					cookies[i].setMaxAge(0);
					break;
				} else {
					response.addCookie(new Cookie("history", Integer.toString(prodNo)));
				}
			}
		}

		if (menu != null && menu.equals("manage")) { // menu : manage 이면

			// 아직 requestScope에 prodNo가 담겨 있는 듯
			return "forward:/product/updateProduct?menu="+menu;
			//return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu; 오류남 (?) 
		}
		
		// menu : search 이면
		return "forward:/product/getProduct.jsp"; // 페이지 이동
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		
		System.out.println("/product/updateProduct : GET ");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("productVO", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, 
								@RequestParam("prodNo") int prodNo,
								Model model) throws Exception {
		
		System.out.println("/product/updateProduct : POST ");
		//Business Logic
		product.setManuDate(product.getManuDate().replace("-", ""));
		productService.updateProduct(product);
		
		// 아직 requestScope에 prodNo가 담겨 있는 듯
		return "forward:/product/getProduct";
		//return "forward:/getProduct.do?prodNo="+prodNo; 오류남 (?) 
	}
	
	@RequestMapping("deleteProduct")
	public String deleteProduct(@RequestParam("prodNo") int prodNo) throws Exception {
		
		System.out.println("/product/deleteProduct");
		//Business Logic
		productService.deleteProduct(prodNo);
		
		return "forward:/product/listProduct?menu=manage";
	}
	
	@RequestMapping("listProduct")
	public String listProduct(@ModelAttribute("search") Search search,
							  @RequestParam(value="menu", required=false) String menu,
							  //@RequestParam(value="findby", required=false) String findby,
							  Model model) throws Exception {
		
		System.out.println("/product/listProduct");
		
		// jsp를 거치지 않고 .do를 통해 여기로 왔을 때 첫 페이지를 1이라고 지정 
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); // pageSize 지정 
		
		System.out.println("보낼 서치 " + search);
		
		// Business logic 수행
		Map<String, Object> map = productService.getProductList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); 이렇게 두개가 담겨 있음 ! 
		Page resultPage	= // 페이지 나누는 것을 추상화 & 캡슐화 한 Page 클래스 이용 
				new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("menu", menu);
		//model.addAttribute("findby", findby);
		model.addAttribute("list", map.get("list")); // 페이지 클릭했을 때 나타나는 제품 정보가 담겨있을 것
		model.addAttribute("resultPage", resultPage); // 화면상의 페이지 정보가 다 담겨있음 
		model.addAttribute("search", search); // 검색 정보가 담겨있음 
		
		return "forward:/product/listProduct.jsp";
	}
}
