package zhan.service;

import java.sql.SQLException;

import zhan.dao.UserDao;
import zhan.domain.User;

public class UserService {

	//注册用户
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
	
	//判断用户是否激活账号
	public boolean active(String activeCode) throws SQLException {
		UserDao userDao = new UserDao();
		int row = userDao.active(activeCode);
		return row > 0 ? true : false;
	}

	//判断用户名是否存在
	public boolean checkUsername(String username) throws SQLException {
		UserDao userDao = new UserDao();
		Long row = userDao.checkUsername(username);
		return row > 0 ? true : false;
	}

	//用户登录
	public User login(String username, String password) throws SQLException {
		UserDao userDao = new UserDao();
		return userDao.login(username, password);
	}

}
