package zhan.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zhan.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("text/html;charset=UTF-8");
		
		String activeCode = request.getParameter("activeCode");
		//�����û�״̬Ϊ����״̬
		UserService userService = new UserService();
		boolean isActive = false;
		try {
			isActive = userService.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//��ת����¼����
		if (isActive) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}else {
			System.out.println("����ʧ��!");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}