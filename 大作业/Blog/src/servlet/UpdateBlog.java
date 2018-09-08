package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.UserBiz;
import biz.UserBizFactory;
import entity.User;

/**
 * 更新博客
 * */

public class UpdateBlog extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8735517915785951543L;
	private static UserBiz biz = UserBizFactory.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String id = req.getParameter("id");
		if(user != null && biz.checkUser(user) && biz.hasOptPermission(user.getUsername(), id)) { //检测用户信息成功
			//构造更新的博客内容
			Map<String, String> data = new HashMap<>();  
			data.put("title", "'" + req.getParameter("title") + "'");
			data.put("text", "'" + req.getParameter("text") + "'");
			
			if(biz.updateBlog(data, id)) { //更新博客成功 
				resp.sendRedirect("index.jsp"); 
			}else { //更新失败
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter writer = resp.getWriter();
				writer.println("<h1>修改失败</h1>");
			}
			
		}else { //检测失败
			resp.sendRedirect("login.html");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
