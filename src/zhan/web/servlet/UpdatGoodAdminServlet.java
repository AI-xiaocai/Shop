package zhan.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import zhan.domain.Category;
import zhan.domain.Product;
import zhan.service.ProductService;

public class UpdatGoodAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cid = request.getParameter("cid");
		Map<String, String[]> properties = request.getParameterMap();
		Product product = new Product();
		try {
			BeanUtils.populate(product, properties); //映射封装
		} catch (Exception e) {
			System.out.println(e);
		}
		product.setPimage("products/1/c_0019.jpg");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(new java.sql.Date(new Date().getTime()));
		product.setPflag(0);
		
		boolean isSuccess = false;
		ProductService service = new ProductService();
		Category category = service.getCategoryByCid(cid);
		product.setCategory(category);
		try {
			isSuccess = service.updateGood(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isSuccess) {
			System.out.println("修改成功!");
		}else {
			System.out.println("修改失败!");
		}
		
		response.sendRedirect(request.getContextPath() + "/goodsListAdmin");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}