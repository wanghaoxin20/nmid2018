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
 * չʾ����
 * */

public class ShowBlog extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203760802811838852L;
	private static UserBiz biz = UserBizFactory.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��ȡ�����Ϣ
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String id = req.getParameter("id");
		String opt = req.getParameter("opt");
		if (user != null && biz.checkUser(user) && biz.hasOptPermission(user.getUsername(), id)) { //����û���Ϣ�ɹ�
			if (id != null && opt != null) { //id �� opt����Ϊ��
				if (opt.equals("show")) { //��������Ϊչʾ��������
					Blog blog = DataBaseUtil.getBlog(user, id); //��ȡ���͵�ʵ��
					req.setAttribute("blog", blog);
					getServletContext().getRequestDispatcher("/showBlog.jsp").forward(req, resp);
				}else if(opt.equals("update")) { //����Ϊ�༭��������
					Blog blog = DataBaseUtil.getBlog(user, id); //��ȡ���͵�ʵ��
					req.setAttribute("blog", blog);
					getServletContext().getRequestDispatcher("/updateBlog.jsp").forward(req, resp);
				}else {
					resp.sendRedirect("login.html");
				}
			}
		} else { //�û���Ϣ���ʧ��
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
