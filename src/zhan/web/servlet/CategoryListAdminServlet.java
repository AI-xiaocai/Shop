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

public class CategoryListAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		List<Category> categoryList = null;
		try {
			categoryList = service.findAllCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//����ȡ�������ݱ��浽request����
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}