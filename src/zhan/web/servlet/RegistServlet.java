package zhan.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import zhan.domain.User;
import zhan.service.UserService;
import zhan.utils.CommonsUtils;
import zhan.utils.MailUtils;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			//自定义类型转换器（将String转成Date）
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				}
			}, Date.class);
			
			//映射封装
			BeanUtils.populate(user, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setUid(CommonsUtils.getUUid());
		user.setTelephone(null);
		user.setState(0); //新注册用户设置状态为未激活
		String activeCode = CommonsUtils.getUUid();
		user.setCode(activeCode); //新注册用户激活码设置为UUID
		
		//将封装好的user传递给service层
		UserService userService = new UserService();
		boolean isRegistSuccess = userService.regist(user);
		
		//判断是否注册成功
		if (isRegistSuccess) {
			//注册成功,发送激活邮件
			String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
					+ "<a href='http://10.167.200.162:8080/Shop/active?activeCode="+activeCode+"'>"
					+ "http://10.167.200.162:8080/Shop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), "激活邮件", emailMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		}else {
			//注册失败
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}