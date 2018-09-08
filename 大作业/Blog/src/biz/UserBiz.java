package biz;

import java.util.Map;

import entity.Blog;
import entity.User;

/**
 * 用户管理事务的接口
 * */

public interface UserBiz {
	
	/**
	 * 检查密码登录
	 * */
	public boolean checkUser(User user);
	
	/**
	 * 是否具有操作权限
	 * */
	public boolean hasOptPermission(String username, String id);
	
	/**
	 * 发布博客
	 * */
	public boolean publishBlog(User user, Blog blog);
	
	/**
	 * 删除博客
	 * */
	public boolean delBlog(String blogID);
	
	
	/**
	 * 修改博客
	 * */
	public boolean updateBlog(Map<String, String> data, String id);
	
}
