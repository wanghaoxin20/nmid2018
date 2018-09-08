package servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * �û��ǳ�
 * */

public class LoginOut extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2438150840140905901L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		//����session ɾ�����е�session��Ϣ
		Enumeration<?> names = session.getAttributeNames();
		while(names.hasMoreElements()) {
			String key = (String) names.nextElement();
			System.out.println(key);
			session.removeAttribute(key);
		}
		//�ض��򵽵�¼ҳ��
		resp.sendRedirect("login.html");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
