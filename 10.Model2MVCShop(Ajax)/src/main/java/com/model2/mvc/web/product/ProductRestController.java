package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> 상품관리 Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	// 컨트롤러 선언 시에 RestController로 선언 해 주지 않고 그냥 Controller로 선언하면 RequestBody 써줘야 함 !
	//@ResponseBody // 참고 : https://aljjabaegi.tistory.com/148, https://whitekeyboard.tistory.com/193
	@RequestMapping(value="json/listProduct", method=RequestMethod.POST)
	public List<Product> listProduct(@RequestBody Search search) throws Exception {
		
		System.out.println("json/product/listProduct : POST");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(100); // pageSize 지정하는데 값 다 나오게 하고 싶으니까 크게 줬음 
		
		System.out.println(search);
		
		Map<String, Object> map = productService.getProductList(search);
		
		List<Product> list = (List<Product>) map.get("list");
		
		System.out.println(list);
		
		return list;
	}

}
