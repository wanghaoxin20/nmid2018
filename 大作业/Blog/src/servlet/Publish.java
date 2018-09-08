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
 * 发布文章
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
		if(user != null && biz.checkUser(user)) { //检测用户信息成功
			//构造博客对象的信息
			Blog blog = new Blog();
			blog.setBlogID(DataBaseUtil.getBlogID());
			blog.setAuthor(user.getUsername());
			blog.setPublishDate(sf.format(new Date(System.currentTimeMillis())));
			blog.setBlogTitle(req.getParameter("title"));
			blog.setBlogContent(req.getParameter("text"));
			
			if(biz.publishBlog(user, blog)) { //发布成功
				resp.sendRedirect("index.jsp");
			}else { //发布失败
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter writer = resp.getWriter();
				writer.println("<h1>发布失败</h1>");
			}
			
		}else { //信息检测失败则定向到登录页面
			resp.sendRedirect("login.html");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
