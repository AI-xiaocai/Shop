package zhan.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zhan.domain.Cart;
import zhan.domain.CartItem;
import zhan.domain.Order;
import zhan.domain.OrderItem;
import zhan.domain.User;
import zhan.service.ProductService;
import zhan.utils.CommonsUtils;

public class SubmitOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		//·â×°Order¶ÔÏó
		Order order = new Order();
		String oid = CommonsUtils.getUUid();
		order.setOid(oid);
		order.setOrdertime(new java.sql.Date(new Date().getTime()));
		Cart cart = (Cart) session.getAttribute("cart");
		order.setTotal(cart.getTotal());
		order.setState(0);
		
		order.setAddress(null);
		order.setName(null);
		order.setTelephone(null);
		
		order.setUser(user);
		
		Map<String, CartItem> cartItems = cart.getCartItems();
		for(Entry<String, CartItem> entry : cartItems.entrySet()) {
			CartItem cartItem = entry.getValue();
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(CommonsUtils.getUUid());
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		ProductService service = new ProductService();
		service.submitOrder(order);
		
		session.setAttribute("order", order);
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}