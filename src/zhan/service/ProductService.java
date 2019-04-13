package zhan.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import zhan.dao.ProductDao;
import zhan.domain.Category;
import zhan.domain.Order;
import zhan.domain.PageBean;
import zhan.domain.Product;
import zhan.utils.DataSourceUtils;

public class ProductService {
	//获取最热商品
	public List<Product> findHotProductList() {
		ProductDao productDao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = productDao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductList;
	}
	
	//获取最热商品
	public List<Product> findNewProductList() {
		ProductDao productDao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = productDao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProductList;
	}

	//获取商品类别
	public List<Category> findCategoryList() {
		ProductDao productDao = new ProductDao();
		List<Category> categoryList = null;
		try {
			categoryList = productDao.findCategoryList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	public PageBean<Product> getPageBean(String cid, int currentPage, int currentCount) {
		//封装PageBean
		PageBean<Product> pageBean = new PageBean<Product>();
		ProductDao productDao = new ProductDao();
		//1.封装currentPage
		pageBean.setCurrentPage(currentPage);
		//2.封装currentCount
		pageBean.setCurrentCount(currentCount);
		//3.封装totalCount
		int totalCount = 0;
		try {
			totalCount = productDao.getTotalCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//4.封装totalPage
		int totalPage = (int) Math.ceil(1.0*totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		//5.封装list
		int index = (currentPage - 1) * currentCount; 
		List<Product> productList = null;
		try {
			productList = productDao.getProductList(cid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(productList);
		
		return pageBean;
	}

	//根据pid获得对应Product
	public Product getProduct(String pid) throws SQLException {
		ProductDao productDao = new ProductDao();
		return productDao.getProduct(pid);
	}

	//提交订单
	public void submitOrder(Order order) {
		ProductDao dao = new ProductDao();
		//开启事务控制
		try {
			DataSourceUtils.startTransaction();
			dao.submitOrderToDB(order);
			dao.submitorderItemToDB(order);
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//更新订单收件人信息
	public void updateOrderAddr(String oid, String address, String name, String telephone) {
		ProductDao productDao = new ProductDao();
		try {
			productDao.updateOrderAddr(oid, address, name, telephone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//更新订单状态,已付款为1,未付款为0
	public void updateOrderState(String r6_Order) {
		ProductDao productDao = new ProductDao();
		try {
			productDao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//查询所有订单
	public List<Order> findAllOrders(String uid) {
		ProductDao productDao = new ProductDao();
		List<Order> orderList = null;
		try {
			orderList = productDao.findAllOrders(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	//查询所有订单信息
	public List<Map<String, Object>> findAllOrderItem(String oid) {
		ProductDao productDao = new ProductDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = productDao.findAllOrderItem(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}

	//查询所有商品
	public List<Product> findAllGoods() {
		List<Product> productList = null;
		ProductDao dao = new ProductDao();
		try {
			productList = dao.findAllGoods();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	//查询所有商品类别
	public List<Category> findAllCategory() {
		List<Category> categoryList = null;
		ProductDao dao = new ProductDao();
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	//删除指定商品
	public void deleteGoodById(String pid) throws SQLException {
		ProductDao dao = new ProductDao();
		dao.deleteGoodById(pid);
	}

	//添加新商品
	public void addGood(Product product) throws SQLException {
		ProductDao dao = new ProductDao();
		dao.addGood(product);
	}

	//根据pid获得商品
	public Product getProductById(String pid) throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.getProductById(pid);
	}

	//更新商品
	public boolean updateGood(Product product) throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.updateGood(product);
	}

	//根据cid获取商品类别
	public Category getCategoryByCid(String cid){
		ProductDao dao = new ProductDao();
		Category category = null;
		try {
			category = dao.getCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	//删除指定商品类别
	public void delCategoryByCid(String cid) {
		ProductDao dao = new ProductDao();
		try {
			dao.delCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//添加新的商品类别
	public void addCategory(Category category) {
		ProductDao dao = new ProductDao();
		try {
			dao.addCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//更新商品类别
	public boolean updateCategory(Category category) {
		ProductDao dao = new ProductDao();
		boolean isSuccess = false;
		try {
			isSuccess = dao.updateCategory(category);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return isSuccess;
	}

	//获取所有订单
	public List<Order> getAllOrders() {
		ProductDao dao = new ProductDao();
		List<Order> orderList = null;
		try {
			orderList = dao.getAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	//根据oid获取所有订单项信息
	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		ProductDao dao = new ProductDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
}
