package zhan.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zhan.domain.Cart;
import zhan.domain.CartItem;
import zhan.domain.Product;
import zhan.service.ProductService;

public class AddItemToCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String buyNum = request.getParameter("buyNum");
		int buyCount = Integer.parseInt(buyNum);
		String pid = request.getParameter("pid");
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
		
		CartItem cartItem = new CartItem();
		ProductService service = new ProductService();
		Product product = null;
		try {
			product = service.getProduct(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		double subTot = buyCount * product.getShop_price();
		//判断session中购物车里是否已经有该商品
		if (cart.getCartItems().containsKey(pid)) {
			buyCount += cart.getCartItems().get(pid).getBuyNum();
		}
		
		cartItem.setProduct(product);
		cartItem.setBuyNum(buyCount);
		double subTotal = buyCount * product.getShop_price();
		cartItem.setSubTotal(subTotal);
		cart.getCartItems().put(pid, cartItem);
		cart.setTotal(cart.getTotal()+subTot);
		
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}