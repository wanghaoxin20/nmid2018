package servlet;

import java.io.IOException;

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
 * 展示内容
 * */

public class ShowBlog extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203760802811838852L;
	private static UserBiz biz = UserBizFactory.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取相关信息
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String id = req.getParameter("id");
		String opt = req.getParameter("opt");
		if (user != null && biz.checkUser(user) && biz.hasOptPermission(user.getUsername(), id)) { //检测用户信息成功
			if (id != null && opt != null) { //id 和 opt都不为空
				if (opt.equals("show")) { //请求类型为展示博客内容
					Blog blog = DataBaseUtil.getBlog(user, id); //获取博客的实例
					req.setAttribute("blog", blog);
					getServletContext().getRequestDispatcher("/showBlog.jsp").forward(req, resp);
				}else if(opt.equals("update")) { //请求为编辑博客内容
					Blog blog = DataBaseUtil.getBlog(user, id); //获取博客的实例
					req.setAttribute("blog", blog);
					getServletContext().getRequestDispatcher("/updateBlog.jsp").forward(req, resp);
				}else {
					resp.sendRedirect("login.html");
				}
			}
		} else { //用户信息检测失败
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
