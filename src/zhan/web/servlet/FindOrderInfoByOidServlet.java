package zhan.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import zhan.service.ProductService;

public class FindOrderInfoByOidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String oid = request.getParameter("oid");
		ProductService service = new ProductService();
		List<Map<String,Object>> mapList = service.findOrderInfoByOid(oid);
		
		//×ª³Éjson
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}