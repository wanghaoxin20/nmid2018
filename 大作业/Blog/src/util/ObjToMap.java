package util;

import java.util.HashMap;
import java.util.Map;

import entity.Blog;
import entity.User;

/**
 * 将对象转换为map,用于构造sql语句
 * */

public class ObjToMap {
	
	
	public static Map<String, String> user(User user){
		Map<String, String> data = new HashMap<>();
		return data;
	}
	
	/**
	 * 对应表结构blog_info
	 * */
	public static Map<String, String> blog_info(Blog blog){
		Map<String, String> data = new HashMap<>();
		data.put("id", blog.getBlogID());
		data.put("title", "'" + blog.getBlogTitle() + "'");
		data.put("publishDate", "'" + blog.getPublishDate() + "'");
		return data;
	}
	
	/**
	 * 对应表结构userblog
	 * */
	public static Map<String, String> userblog(User user, Blog blog){
		Map<String, String> data = new HashMap<>();
		data.put("username", "'" + user.getUsername() + "'");
		data.put("blog_id", blog.getBlogID());
		return data;
	}
	
	/**
	 * 对应表结构blog_text
	 * */
	public static Map<String, String> blog_text(Blog blog){
		Map<String, String> data = new HashMap<>();
		data.put("blog_id", blog.getBlogID());
		data.put("text", "'" + blog.getBlogContent() + "'");
		return data;
	}
	
}
