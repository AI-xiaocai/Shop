package zhan.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import zhan.domain.Category;
import zhan.domain.Product;
import zhan.service.ProductService;
import zhan.utils.CommonsUtils;

public class AddGoodAdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Product product = new Product();
		
		//�ռ����ݵ�����
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			//���������ļ����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//�����ļ��ϴ����Ķ���
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����request����ļ�����󼯺�
			List<FileItem> parseRequest = upload.parseRequest(request);
			for(FileItem item : parseRequest){
				//�ж��Ƿ�����ͨ����
				boolean formField = item.isFormField();
				if(formField){
					//��ͨ���� ��ñ������� ��װ��Productʵ����
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					
					map.put(fieldName, fieldValue);
				}else{
					//�ļ��ϴ��� ����ļ����� ����ļ�������
					String fileName = item.getName();
					String path = this.getServletContext().getRealPath("upload");
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path+"/"+fileName);
					IOUtils.copy(in, out);
					in.close();
					out.close();
					item.delete(); //ɾ����ʱ�ļ�
					
					map.put("pimage", "upload/"+fileName);
				}
			}
			
			BeanUtils.populate(product, map);
			product.setPid(CommonsUtils.getUUid());
			product.setPdate(new java.sql.Date(new Date().getTime()));
			product.setPflag(0);
			Category category = new Category();
			category.setCid(map.get("cid").toString());
			product.setCategory(category);
			
			//��product���ݸ�service��
			ProductService service = new ProductService();
			service.addGood(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/goodsListAdmin");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}