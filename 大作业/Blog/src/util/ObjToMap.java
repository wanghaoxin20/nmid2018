package util;

import java.util.HashMap;
import java.util.Map;

import entity.Blog;
import entity.User;

/**
 * ������ת��Ϊmap,���ڹ���sql���
 * */

public class ObjToMap {
	
	
	public static Map<String, String> user(User user){
		Map<String, String> data = new HashMap<>();
		return data;
	}
	
	/**
	 * ��Ӧ��ṹblog_info
	 * */
	public static Map<String, String> blog_info(Blog blog){
		Map<String, String> data = new HashMap<>();
		data.put("id", blog.getBlogID());
		data.put("title", "'" + blog.getBlogTitle() + "'");
		data.put("publishDate", "'" + blog.getPublishDate() + "'");
		return data;
	}
	
	/**
	 * ��Ӧ��ṹuserblog
	 * */
	public static Map<String, String> userblog(User user, Blog blog){
		Map<String, String> data = new HashMap<>();
		data.put("username", "'" + user.getUsername() + "'");
		data.put("blog_id", blog.getBlogID());
		return data;
	}
	
	/**
	 * ��Ӧ��ṹblog_text
	 * */
	public static Map<String, String> blog_text(Blog blog){
		Map<String, String> data = new HashMap<>();
		data.put("blog_id", blog.getBlogID());
		data.put("text", "'" + blog.getBlogContent() + "'");
		return data;
	}
	
}
