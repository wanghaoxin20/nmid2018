package biz.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import biz.UserBiz;
import entity.Blog;
import entity.User;
import util.DataBaseUtil;
import util.ObjToMap;

public class UserBizImpl implements UserBiz{
	
	/**
	 * 检查密码登录
	 * */
	@Override
	public boolean checkUser(User user) {
		if(DataBaseUtil.UserExist(user.getUsername(), user.getPassword())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否具有操作权限
	 * */
	@Override
	public boolean hasOptPermission(String username, String id) {
		Connection connection = DataBaseUtil.getcon();
		String sql = "select * from userblog where username='" + username + "' and blog_id=" + id;
		boolean flag = false;
		try {
			PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet rSet = pStatement.executeQuery();
			if(rSet.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}

	/**
	 * 发布博客
	 * */
	@Override
	public boolean publishBlog(User user, Blog blog) {
		Map<String, String> blog_info = ObjToMap.blog_info(blog); 
		Map<String, String> userblog = ObjToMap.userblog(user, blog);
		Map<String, String> blog_text = ObjToMap.blog_text(blog);
		
		if(DataBaseUtil.insert("blog_info", blog_info) != 0 && 
				DataBaseUtil.insert("blog_text", blog_text) != 0 && 
				DataBaseUtil.insert("userblog", userblog) != 0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 删除博客
	 * */
	@Override
	public boolean delBlog(String blogID) {
		if(DataBaseUtil.delete("blog_info", "id", blogID) != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 修改博客
	 * */
	@Override
	public boolean updateBlog(Map<String, String> data, String id) {
		Map<String, String> blog_title = new HashMap<>();
		blog_title.put("title", data.get("title"));
		Map<String, String> blog_text = new HashMap<>();
		blog_text.put("text", data.get("text"));
		if(DataBaseUtil.update("blog_info", blog_title, "id", id) != 0 &&
				DataBaseUtil.update("blog_text", blog_text, "blog_id", id) != 0) {
			return true;
		}
		return false;
	}

}
