package zhan.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zhan.domain.User;
import zhan.service.UserService;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserService service = new UserService();
		User user = null;
		try {
			user = service.login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (user != null) {
			String autoLogin = request.getParameter("autoLogin");
			if(autoLogin != null) {
				username = URLEncoder.encode(username, "UTF-8");//����
				Cookie cookie_username = new Cookie("cookie_username", username);
				Cookie cookie_password = new Cookie("cookie_password", password);
				cookie_username.setMaxAge(60*60); //Cookie�־û�ʱ������Ϊ1Сʱ
				cookie_password.setMaxAge(60*60);
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
			}
			//��¼�ɹ�
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+"/index");
		}else {
			//��¼ʧ��
			request.setAttribute("loginInfo", "�û������������!");
			
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}