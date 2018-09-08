package biz;

import java.util.Map;

import entity.Blog;
import entity.User;

/**
 * �û���������Ľӿ�
 * */

public interface UserBiz {
	
	/**
	 * ��������¼
	 * */
	public boolean checkUser(User user);
	
	/**
	 * �Ƿ���в���Ȩ��
	 * */
	public boolean hasOptPermission(String username, String id);
	
	/**
	 * ��������
	 * */
	public boolean publishBlog(User user, Blog blog);
	
	/**
	 * ɾ������
	 * */
	public boolean delBlog(String blogID);
	
	
	/**
	 * �޸Ĳ���
	 * */
	public boolean updateBlog(Map<String, String> data, String id);
	
}
