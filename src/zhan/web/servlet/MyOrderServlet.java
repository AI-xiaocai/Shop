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
		
		//���bList<Order>
		ProductService service = new ProductService();
		List<Order> orderList = service.findAllOrders(user.getUid());
		if (orderList != null) {
			for (Order order : orderList) {
				//��ѯ�ö��������еĶ�����---mapList��װ���Ƕ��������͸ö������е���Ʒ����Ϣ
				List<Map<String, Object>> mapList = service.findAllOrderItem(order.getOid());
				for (Map<String, Object> map : mapList) {
					try {
						//��map��ȡ��count��subtotal��װ��OrderItem��
						OrderItem orderItem = new OrderItem();
						BeanUtils.populate(orderItem, map);
						//��map��ȡ��pimage��pname��shop_price��װ��Product��
						Product product = new Product();
						BeanUtils.populate(product, map);
						//��product��װ��OrderItem
						orderItem.setProduct(product);
						//��orderitem��װ��order�е�orderItemList��
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