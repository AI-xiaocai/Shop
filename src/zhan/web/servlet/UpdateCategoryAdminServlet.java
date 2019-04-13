package zhan.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import zhan.domain.Category;
import zhan.service.ProductService;

public class UpdateCategoryAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		
		boolean isSuccess = false;
		ProductService service = new ProductService();
		try {
			isSuccess = service.updateCategory(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isSuccess) {
			System.out.println("修改成功!");
		}else {
			System.out.println("修改失败!");
		}
		
		response.sendRedirect(request.getContextPath() + "/categoryListAdmin");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}