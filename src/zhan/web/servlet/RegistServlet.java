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
			//�Զ�������ת��������Stringת��Date��
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
			
			//ӳ���װ
			BeanUtils.populate(user, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setUid(CommonsUtils.getUUid());
		user.setTelephone(null);
		user.setState(0); //��ע���û�����״̬Ϊδ����
		String activeCode = CommonsUtils.getUUid();
		user.setCode(activeCode); //��ע���û�����������ΪUUID
		
		//����װ�õ�user���ݸ�service��
		UserService userService = new UserService();
		boolean isRegistSuccess = userService.regist(user);
		
		//�ж��Ƿ�ע��ɹ�
		if (isRegistSuccess) {
			//ע��ɹ�,���ͼ����ʼ�
			String emailMsg = "��ϲ��ע��ɹ���������������ӽ��м����˻�"
					+ "<a href='http://10.167.200.162:8080/Shop/active?activeCode="+activeCode+"'>"
					+ "http://10.167.200.162:8080/Shop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), "�����ʼ�", emailMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		}else {
			//ע��ʧ��
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}