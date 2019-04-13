package zhan.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import zhan.domain.User;
import zhan.utils.DataSourceUtils;

public class UserDao {
	//用户注册
	public int regist(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int row = qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
					user.getEmail(), user.getTelephone(), user.getBirthday(),user.getSex(),
					user.getState(), user.getCode());
		return row;
	}
	
	//激活账号
	public int active(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state = ? where code = ?";
		int row = qr.update(sql, 1, activeCode);
		return row;
	}

	//判断用户名是否重复
	public Long checkUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username = ?";
		Long row = (Long) qr.query(sql, new ScalarHandler(), username);
		return row;
	}

	//用户登录
	public User login(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return qr.query(sql, new BeanHandler<User>(User.class), username, password);
	}

}
