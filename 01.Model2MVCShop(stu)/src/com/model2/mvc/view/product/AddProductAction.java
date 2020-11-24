package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
	
/*		
 * 		[ ��ǰ ��� ��û ]
 * 		���� ��û URL : /addProduct.do
 * 		Action Class : AddProductAction
 */
public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// ======================= File Upload ===============================//
		if (FileUpload.isMultipartContent(request)) {
			String temDir = "/Users/jungjoowon/git/Model2MVCShop/01.Model2MVCShop(stu)/WebContent/images/uploadFiles/";

			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024 * 1024 * 10);
			fileUpload.setSizeThreshold(1024 * 100);

			if (request.getContentLength() < fileUpload.getSizeMax()) {
				ProductVO productVO = new ProductVO();
				
				StringTokenizer token = null;
				
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();
				for (int i = 0; i < Size; i++) {
					
					FileItem fileItem = (FileItem) fileItemList.get(i);
					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							productVO.setManuDate(manuDate);
						}
						else if (fileItem.getFieldName().equals("prodName"))
							productVO.setProdName(fileItem.getString("euc-kr"));
						else if (fileItem.getFieldName().equals("prodDetail"))
							productVO.setProdDetail(fileItem.getString("euc-kr"));
						else if (fileItem.getFieldName().equals("price"))
							productVO.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					} else {
						
						if (fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("/");
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							productVO.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						} else {
							productVO.setFileName("../../images/empty.GIF");
						}
					}
				}
				
				ProductService service = new ProductServiceImpl();
				service.addProduct(productVO);
				
				request.setAttribute("vo", productVO);
			} else {
				int overSize = (request.getContentLength() / 10000000);
				System.out.println("������ ũ��� 1MB ���� �Դϴ�. �ø��� ���� �뷮��" + overSize + "MB �Դϴ�.");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data �� �ƴմϴ�.");
		}
		
		
//		ProductVO productVO = new ProductVO(); // �ν��Ͻ� ����
//		productVO.setProdName(request.getParameter("prodName"));
//		productVO.setProdDetail(request.getParameter("prodDetail"));
//		productVO.setManuDate(request.getParameter("manuDate"));
//		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
//		productVO.setFileName(request.getParameter("fileName"));
//		
//		System.out.println("Ŭ���̾�Ʈ�� ��û�� ��ǰ ���� �� ��� : " + productVO);
//		
//		ProductService service = new ProductServiceImpl();
//		
//		service.addProduct(productVO);
//		
//		request.setAttribute("vo", productVO); // request �� ��Ƽ�
		
		
		return "forward:/product/readProduct.jsp"; 	// ������ �̵�
													// �߰���� ��ư - addProductView.jsp	�� �̵�
													// Ȯ�� ��ư - /listProduct.do?menu=manage URL�� �̵�
	}

}
