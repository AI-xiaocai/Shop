package zhan.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zhan.domain.Category;
import zhan.service.ProductService;

public class UpdateCategoryAdminUIServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		ProductService service = new ProductService();
		
		Category updateCategory = null;
		try {
			updateCategory = service.getCategoryByCid(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("updateCategory", updateCategory);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}