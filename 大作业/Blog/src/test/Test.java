package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.javafx.sg.prism.web.NGWebView;
import com.sun.org.apache.bcel.internal.generic.NEW;

import biz.UserBiz;
import biz.UserBizFactory;
import entity.Blog;
import entity.User;
import util.DataBaseUtil;

public class Test {
	public static void main(String[] args) {
		UserBiz biz = UserBizFactory.getInstance();
		
		System.out.println(biz.hasOptPermission("xxm666", "15"));
		
//		User user = new User("test1", "test1");
//		Blog blog = new Blog();
//		blog.setBlogID(DataBaseUtil.getBlogID());
//		blog.setAuthor("test1");
//		blog.setBlogTitle("这是个测试");
//		blog.setPublishDate("20180830000000");
//		blog.setBlogContent("这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈");
//		biz.publishBlog(user, blog);
		
//		Blog blog = DataBaseUtil.getBlog(new User("test1", "test1"), "4");
//		System.out.println(blog.getBlogContent().replaceAll("\r\n", "<br>").replaceAll("<br><br>", "<br>"));
		
//		Map<String, String> data = new HashMap<>();
//		data.put("username", "'xxm666'");
//		data.put("password", "'xxm666'");
//		
//		DataBaseUtil.insert("user", data);
		
		
	}
}
