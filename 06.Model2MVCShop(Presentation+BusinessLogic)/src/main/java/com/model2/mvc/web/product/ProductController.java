package com.model2.mvc.web.product;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> ��ǰ���� Controller
@Controller
public class ProductController {

	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass()); // ����� ����
	}
	
	// meta-data �� ���� �� �޾ƿ�. pageSize : �� �������� ��� �, pageUnit : �� �������� �������� 
	//==> classpath:config/common.properties  ,  classpath:config/context-common.xml ���� �Ұ�
	@Value("#{commonProperties['pageUnit'] ?: 5}") // �� �ҷ��´ٸ� 5 ���� 
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 3}") // �� �ҷ��´ٸ� 3 ���� 
	int pageSize;
	
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product, Model model) throws Exception {
		
		System.out.println("/addProduct.do");
		//Business Logic
		productService.addProduct(product);
		// Model �� View ����
		model.addAttribute("vo", product);
		
		return "forward:/product/readProduct.jsp"; 
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo, 
							 @RequestParam(value="menu", required=false) String menu,
							 Model model,
							 HttpServletRequest request,
							 HttpServletResponse response) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("vo", product);
		model.addAttribute("menu", menu);
		
		// �ֱ� �� ��ǰ ���� => ���� �ڵ� ������ �� 
		if (menu.equals("search")) { // ��ǰ �˻� ������ => ��ǰ �󼼺��� ������ => ���� �� ��ǰ�� ���

			Cookie[] cookies = request.getCookies(); // ���� ��Ű �޾ƿ� (���� ���̵� ����Ǿ��ִ� ��Ű �����ϱ� �ݵ�� �Ѱ��̻� ���� ��)

			int count = 0;

			for (int i = 0; i < cookies.length; i++) { // ��Ű �ϳ��� �޾ƿ��� ���� for��

				if (cookies[i].getName().equals("history")) { // ��Ű �̸��� history �ΰ� ������
					count++;
				}

				if (count == 1) {
					// ������ ��Ű value�� , ���� �����ؼ� �߰�
					System.out.println("��Ű�� �����");
					System.out.println(cookies[i].getValue() + "," + prodNo);
					response.addCookie(new Cookie("history", cookies[i].getValue() + URLEncoder.encode("," + prodNo)));
					cookies[i].setMaxAge(0);
					break;
				} else {
					response.addCookie(new Cookie("history", Integer.toString(prodNo)));
				}
			}
		}

		if (menu != null && menu.equals("manage")) { // menu : manage �̸�

			// ���� requestScope�� prodNo�� ��� �ִ� ��
			return "forward:/updateProductView.do?menu="+menu;
			//return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu; ������ (?) 
		}
		
		// menu : search �̸�
		return "forward:/product/getProduct.jsp"; // ������ �̵�
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		
		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("productVO", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product, 
								@RequestParam("prodNo") int prodNo,
								Model model) throws Exception {
		
		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		
		// ���� requestScope�� prodNo�� ��� �ִ� ��
		return "forward:/getProduct.do";
		//return "forward:/getProduct.do?prodNo="+prodNo; ������ (?) 
	}
	
	@RequestMapping("/deleteProduct.do")
	public String deleteProduct(@RequestParam("prodNo") int prodNo) throws Exception {
		
		System.out.println("/deleteProduct.do");
		//Business Logic
		productService.deleteProduct(prodNo);
		
		return "forward:/listProduct.do?menu=manage";
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search,
							  @RequestParam(value="menu", required=false) String menu,
							  Model model) throws Exception {
		
		System.out.println("/listProduct.do");
		
		// jsp�� ��ġ�� �ʰ� .do�� ���� ����� ���� �� ù �������� 1�̶�� ���� 
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); // pageSize ���� 
		
		// Business logic ����
		Map<String, Object> map = productService.getProductList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); �̷��� �ΰ��� ��� ���� ! 
		Page resultPage	= // ������ ������ ���� �߻�ȭ & ĸ��ȭ �� Page Ŭ���� �̿� 
				new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("menu", menu);
		model.addAttribute("list", map.get("list")); // ������ Ŭ������ �� ��Ÿ���� ��ǰ ������ ������� ��
		model.addAttribute("resultPage", resultPage); // ȭ����� ������ ������ �� ������� 
		model.addAttribute("search", search); // �˻� ������ ������� 
		
		return "forward:/product/listProduct.jsp";
	}
}