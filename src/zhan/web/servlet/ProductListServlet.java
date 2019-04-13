package zhan.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zhan.domain.PageBean;
import zhan.domain.Product;
import zhan.service.ProductService;

public class ProductListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;
		
		ProductService service = new ProductService();
		String cid = request.getParameter("cid");
		PageBean<Product> pageBean = service.getPageBean(cid, currentPage, currentCount);
		
		//��ҳ����ʾ����
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		//����һ����¼��ʷ��Ʒ��Ϣ�ļ���
		List<Product> historyProductList = new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("pids".equals(cookie.getName())){
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for(String pid : split){
						Product pro = null;
						try {
							pro = service.getProduct(pid);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						historyProductList.add(pro);
					}
				}
			}
		}
		//����ʷ��¼�ļ��Ϸŵ�����
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}