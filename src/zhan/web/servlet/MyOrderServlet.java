package zhan.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import zhan.domain.Order;
import zhan.domain.OrderItem;
import zhan.domain.Product;
import zhan.domain.User;
import zhan.service.ProductService;

public class MyOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		//封裝List<Order>
		ProductService service = new ProductService();
		List<Order> orderList = service.findAllOrders(user.getUid());
		if (orderList != null) {
			for (Order order : orderList) {
				//查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
				List<Map<String, Object>> mapList = service.findAllOrderItem(order.getOid());
				for (Map<String, Object> map : mapList) {
					try {
						//从map中取出count、subtotal封装到OrderItem中
						OrderItem orderItem = new OrderItem();
						BeanUtils.populate(orderItem, map);
						//从map中取出pimage、pname、shop_price封装到Product中
						Product product = new Product();
						BeanUtils.populate(product, map);
						//将product封装到OrderItem
						orderItem.setProduct(product);
						//将orderitem封装到order中的orderItemList中
						order.getOrderItems().add(orderItem);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}