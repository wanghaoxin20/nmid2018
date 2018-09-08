package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.UserBiz;
import biz.UserBizFactory;
import entity.Blog;
import entity.User;
import util.DataBaseUtil;

/**
 * ��������
 * */

public class Publish extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4597866193784337286L;
	
	private static UserBiz biz = UserBizFactory.getInstance();
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if(user != null && biz.checkUser(user)) { //����û���Ϣ�ɹ�
			//���첩�Ͷ������Ϣ
			Blog blog = new Blog();
			blog.setBlogID(DataBaseUtil.getBlogID());
			blog.setAuthor(user.getUsername());
			blog.setPublishDate(sf.format(new Date(System.currentTimeMillis())));
			blog.setBlogTitle(req.getParameter("title"));
			blog.setBlogContent(req.getParameter("text"));
			
			if(biz.publishBlog(user, blog)) { //�����ɹ�
				resp.sendRedirect("index.jsp");
			}else { //����ʧ��
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter writer = resp.getWriter();
				writer.println("<h1>����ʧ��</h1>");
			}
			
		}else { //��Ϣ���ʧ�����򵽵�¼ҳ��
			resp.sendRedirect("login.html");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
