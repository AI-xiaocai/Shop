package zhan.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zhan.domain.Category;
import zhan.service.ProductService;
import zhan.utils.CommonsUtils;

public class AddCategoryAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(CommonsUtils.getUUid());
		category.setCname(cname);
		
		ProductService service = new ProductService();
		service.addCategory(category);
		
		response.sendRedirect(request.getContextPath() + "/categoryListAdmin");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}