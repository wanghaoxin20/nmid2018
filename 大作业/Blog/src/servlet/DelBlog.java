package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.UserBiz;
import biz.UserBizFactory;
import entity.User;

/**
 * 删除博客
 * */

public class DelBlog extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6799636991419313458L;
	private static UserBiz biz = UserBizFactory.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(); //获取session
		User user = (User) session.getAttribute("user");  //获取user对象
		String id = req.getParameter("id");
		if (user != null && biz.checkUser(user) && biz.hasOptPermission(user.getUsername(), id)) { //检查信息
				if (biz.delBlog(id)) { //删除博客成功
					resp.setContentType("text/html;charset=utf-8");
					PrintWriter writer = resp.getWriter();
					writer.print("<script>alert('删除成功')</script>");
					resp.sendRedirect("index.jsp");
				} else {
					resp.setContentType("text/html;charset=utf-8");
					PrintWriter writer = resp.getWriter();
					writer.println("<h1>删除失败</h1>");
				}
		} else { //如果user为null重定向到登录页面
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
