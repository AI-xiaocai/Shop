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
	//��ȡ������Ʒ
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
	
	//��ȡ������Ʒ
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

	//��ȡ��Ʒ���
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
		//��װPageBean
		PageBean<Product> pageBean = new PageBean<Product>();
		ProductDao productDao = new ProductDao();
		//1.��װcurrentPage
		pageBean.setCurrentPage(currentPage);
		//2.��װcurrentCount
		pageBean.setCurrentCount(currentCount);
		//3.��װtotalCount
		int totalCount = 0;
		try {
			totalCount = productDao.getTotalCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//4.��װtotalPage
		int totalPage = (int) Math.ceil(1.0*totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		//5.��װlist
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

	//����pid��ö�ӦProduct
	public Product getProduct(String pid) throws SQLException {
		ProductDao productDao = new ProductDao();
		return productDao.getProduct(pid);
	}

	//�ύ����
	public void submitOrder(Order order) {
		ProductDao dao = new ProductDao();
		//�����������
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

	//���¶����ռ�����Ϣ
	public void updateOrderAddr(String oid, String address, String name, String telephone) {
		ProductDao productDao = new ProductDao();
		try {
			productDao.updateOrderAddr(oid, address, name, telephone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//���¶���״̬,�Ѹ���Ϊ1,δ����Ϊ0
	public void updateOrderState(String r6_Order) {
		ProductDao productDao = new ProductDao();
		try {
			productDao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//��ѯ���ж���
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

	//��ѯ���ж�����Ϣ
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

	//��ѯ������Ʒ
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

	//��ѯ������Ʒ���
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

	//ɾ��ָ����Ʒ
	public void deleteGoodById(String pid) throws SQLException {
		ProductDao dao = new ProductDao();
		dao.deleteGoodById(pid);
	}

	//�������Ʒ
	public void addGood(Product product) throws SQLException {
		ProductDao dao = new ProductDao();
		dao.addGood(product);
	}

	//����pid�����Ʒ
	public Product getProductById(String pid) throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.getProductById(pid);
	}

	//������Ʒ
	public boolean updateGood(Product product) throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.updateGood(product);
	}

	//����cid��ȡ��Ʒ���
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

	//ɾ��ָ����Ʒ���
	public void delCategoryByCid(String cid) {
		ProductDao dao = new ProductDao();
		try {
			dao.delCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//����µ���Ʒ���
	public void addCategory(Category category) {
		ProductDao dao = new ProductDao();
		try {
			dao.addCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//������Ʒ���
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

	//��ȡ���ж���
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

	//����oid��ȡ���ж�������Ϣ
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
