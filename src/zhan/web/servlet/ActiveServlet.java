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
		//设置用户状态为激活状态
		UserService userService = new UserService();
		boolean isActive = false;
		try {
			isActive = userService.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//跳转到登录界面
		if (isActive) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}else {
			System.out.println("激活失败!");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}