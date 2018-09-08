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
 * ɾ������
 * */

public class DelBlog extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6799636991419313458L;
	private static UserBiz biz = UserBizFactory.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(); //��ȡsession
		User user = (User) session.getAttribute("user");  //��ȡuser����
		String id = req.getParameter("id");
		if (user != null && biz.checkUser(user) && biz.hasOptPermission(user.getUsername(), id)) { //�����Ϣ
				if (biz.delBlog(id)) { //ɾ�����ͳɹ�
					resp.setContentType("text/html;charset=utf-8");
					PrintWriter writer = resp.getWriter();
					writer.print("<script>alert('ɾ���ɹ�')</script>");
					resp.sendRedirect("index.jsp");
				} else {
					resp.setContentType("text/html;charset=utf-8");
					PrintWriter writer = resp.getWriter();
					writer.println("<h1>ɾ��ʧ��</h1>");
				}
		} else { //���userΪnull�ض��򵽵�¼ҳ��
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
