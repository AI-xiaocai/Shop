package zhan.service;

import java.sql.SQLException;

import zhan.dao.UserDao;
import zhan.domain.User;

public class UserService {

	//ע���û�
	public boolean regist(User user) {
		UserDao userDao = new UserDao();
		int row = 0;
		try {
			row = userDao.regist(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return row > 0 ? true : false;
	}
	
	//�ж��û��Ƿ񼤻��˺�
	public boolean active(String activeCode) throws SQLException {
		UserDao userDao = new UserDao();
		int row = userDao.active(activeCode);
		return row > 0 ? true : false;
	}

	//�ж��û����Ƿ����
	public boolean checkUsername(String username) throws SQLException {
		UserDao userDao = new UserDao();
		Long row = userDao.checkUsername(username);
		return row > 0 ? true : false;
	}

	//�û���¼
	public User login(String username, String password) throws SQLException {
		UserDao userDao = new UserDao();
		return userDao.login(username, password);
	}

}
