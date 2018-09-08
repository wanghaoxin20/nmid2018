package servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户登出
 * */

public class LoginOut extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2438150840140905901L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		//遍历session 删除所有的session信息
		Enumeration<?> names = session.getAttributeNames();
		while(names.hasMoreElements()) {
			String key = (String) names.nextElement();
			System.out.println(key);
			session.removeAttribute(key);
		}
		//重定向到登录页面
		resp.sendRedirect("login.html");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
