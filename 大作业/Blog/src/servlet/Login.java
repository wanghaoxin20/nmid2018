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
 * 用户登录
 * */

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3665206282086019542L;
	private UserBiz biz = UserBizFactory.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String pwd = req.getParameter("pwd");
		User user = new User(name, pwd); //实例化构造用户对象
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter writer = resp.getWriter();
		if (biz.checkUser(user)) { 	//登录成功
			HttpSession session = req.getSession();
			//设置session属性
			session.setAttribute("user", user);
			session.setAttribute("islogin", true);
			//重定向
			resp.sendRedirect("index.jsp");
		} else { //登录失败
			writer.write("<h1>登录失败<h1>");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
