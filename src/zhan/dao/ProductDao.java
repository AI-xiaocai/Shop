package zhan.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import zhan.domain.Category;
import zhan.domain.Order;
import zhan.domain.OrderItem;
import zhan.domain.Product;
import zhan.utils.DataSourceUtils;

public class ProductDao {
	//查询热门商品列表
	public List<Product> findHotProductList() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot=? limit ?,?";
		List<Product> hotProductList = qr.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
		return hotProductList;
	}

	//查询最新商品列表
	public List<Product> findNewProductList() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> newProductList = qr.query(sql, new BeanListHandler<Product>(Product.class) ,0 ,9);
		return newProductList;
	}

	//查询商品类别
	public List<Category> findCategoryList() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> categoryList = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}

	//根据cid获得对应类别商品总个数
	public int getTotalCount(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long row = (Long) qr.query(sql, new ScalarHandler(), cid);
		return row.intValue();
	}

	//分页: 获取指定页商品集合
	public List<Product> getProductList(String cid, int index, int currentCount) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
	}

	//根据pid获得Product
	public Product getProduct(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	//提交订单到数据库
	public void submitOrderToDB(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		qr.update(conn,sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}

	//提交订单项到数据库
	public void submitorderItemToDB(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem item : orderItems) {
			qr.update(conn,sql,item.getItemid(),item.getCount(),item.getSubtotal(),
					item.getProduct().getPid(),item.getOrder().getOid());
		}
	}

	//更新收货人信息
	public void updateOrderAddr(String oid, String address, String name, String telephone) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address=?,name=?,telephone=? where oid=?";
		qr.update(sql, address, name, telephone, oid);
	}

	//更新订单状态,未付款为0,已付款为1
	public void updateOrderState(String r6_Order) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state=? where oid=?";
		qr.update(sql, 1, r6_Order);
	}

	//查询指定用户的所有订单
	public List<Order> findAllOrders(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}

	//查询所有订单项
	public List<Map<String, Object>> findAllOrderItem(String oid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where "
				+ "i.pid=p.pid and i.oid=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), oid);
		return mapList;
	}

	//查询所有商品
	public List<Product> findAllGoods() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		List<Product> productList = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return productList;
	}

	//查询所有商品类别
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> categoryList = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}

	//根据pid删除商品
	public void deleteGoodById(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid = ?";
		qr.update(sql, pid);
	}

	//添加商品
	public void addGood(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCategory().getCid());
	}

	//根据pid获取商品
	public Product getProductById(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		Product updateProduct = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
		return updateProduct;
	}
	
	//根据cid获取商品类别
	public Category getCategoryByCid(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category where cid=?";
		Category category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		return category;
	}

	//修改商品信息
	public boolean updateGood(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,"
				+ "pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		int row = qr.update(sql,product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),
				product.getPflag(),product.getCategory().getCid(),product.getPid());
		if (row>0) {
			return true;
		}
		return false;
	}

	//删除指定商品类别
	public void delCategoryByCid(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from category where cid = ?";
		qr.update(sql, cid);
	}

	//添加新的商品类别
	public void addCategory(Category category) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?,?)";
		qr.update(sql, category.getCid(), category.getCname());
	}

	//修改商品类别
	public boolean updateCategory(Category category) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update category set cname=? where cid=?";
		int row = qr.update(sql,category.getCname(),category.getCid());
		if (row>0) {
			return true;
		}
		return false;
	}

	//查询所有订单
	public List<Order> getAllOrders() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
	}

	//根据oid查询所有订单项信息
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.pimage,p.pname,p.shop_price,i.count,i.subtotal "+
				" from orderitem i,product p where i.pid=p.pid and i.oid=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), oid);
		return mapList;
	}
}
