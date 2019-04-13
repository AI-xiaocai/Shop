package zhan.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zhan.domain.Category;
import zhan.domain.Product;
import zhan.service.ProductService;

public class UpdateGoodAdminUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		
		ProductService service = new ProductService();
		Product updateProduct = null;
		
		try {
			updateProduct = service.getProductById(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Category> categoryList = null;
		try {
			categoryList = service.findAllCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//����ȡ�������ݱ��浽request����
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("updateProduct", updateProduct);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}