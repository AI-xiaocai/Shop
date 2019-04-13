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

public class GoodsListAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		List<Product> productList = null;
		
		try {
			productList = service.findAllGoods();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Category> categoryList = null;
		try {
			categoryList = service.findAllCategory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//将获取到的数据保存到request域中
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}