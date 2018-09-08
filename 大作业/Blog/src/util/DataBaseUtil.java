package util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import entity.Blog;
import entity.User;

/**
 * 数据库工具
 * */

public class DataBaseUtil {
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 通过id从数据库获得blog的实例
	 * */
	public static Blog getBlog(User user, String blogid) {
		Blog blog = new Blog();
		String sql = "select blog_info.*,blog_text.text from blog_info,blog_text where blog_info.id=blog_text.blog_id and blog_info.id=" + blogid;
		Connection connection = getcon();
		try {
			PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet rSet = pStatement.executeQuery();
			if(rSet.next()) {
				blog.setAuthor(user.getUsername());
				blog.setBlogID(blogid);
				blog.setBlogTitle(rSet.getString("blog_info.title"));
				blog.setBlogContent(rSet.getString("blog_text.text"));
				blog.setPublishDate(rSet.getString("blog_info.publishDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return blog;
	}
	
	/**
	 * 获取blogid
	 * */
	public static String getBlogID() {
		String id = "0";
		String sql1 = "select * from id";
		Connection connection = getcon();
		if(connection != null) {
			try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet rSet = statement.executeQuery(sql1);
				if(rSet.next()) {
					id = rSet.getString("next_id");
					String sql2 = "update id set next_id=next_id+1";
					statement.executeUpdate(sql2);
				}
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
	/**
	 * 修改数据
	 * */
	public static int update(String tableName, Map<String, String> data, String key, String value) {
		int result = 0;
		String sql = getUpdateSql(tableName, data, key, value);
		Connection connection = getcon();
		if(connection != null) {
			PreparedStatement pStatement;
			try {
				pStatement = (PreparedStatement) connection.prepareStatement(sql);
				result = pStatement.executeUpdate();
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
			
		}
		return result;
	}
	
	/**
	 * 删除数据
	 * */
	public static int delete(String tableName, String key, String value) {
		String sql = getDeleteSql(tableName, key, value);
		int result = 0;
		Connection connection = getcon();
		if(connection != null) {
			PreparedStatement pStatement;
			try {
				pStatement = (PreparedStatement)connection.prepareStatement(sql);
				result = pStatement.executeUpdate();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 插入数据
	 */
	public static int insert(String tableName, Map<String, String> data) {
		String sql = getInsertSql(tableName, data);
		System.out.println(sql);
		int result = 0;
		Connection connection = getcon();
		if (connection != null) {
			try {
				PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(sql);
				result = pStatement.executeUpdate();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	/**
	 * 判断用户是否存在
	 */
	public static boolean UserExist(String username, String password) {
		String sql = "select * from user where username=\"" + username + "\" and password=\"" + password + "\"";
		ResultSet resultSet = null;
		boolean flag = false;
		Connection connection = getcon();
		PreparedStatement pStatement;
		if (connection != null) { // 如果连接不为null
			try {
				pStatement = (PreparedStatement) connection.prepareStatement(sql);
				resultSet = pStatement.executeQuery();
				flag = resultSet.next();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 获取update的sql语句
	 * */
	private static String getUpdateSql(String tableName, Map<String, String> data, String key, String value) {
		StringBuffer sql = new StringBuffer();
		sql.append("update " + tableName + " set ");
		int size = data.keySet().size();
		int p = 0;
		for(String k : data.keySet()) {
			if(p != size - 1) {
				sql.append(k + "=" + data.get(k) + ",");
			}else {
				sql.append(k + "=" + data.get(k) + " ");
			}
		}
		sql.append("where " + key + "=" + value);
		return sql.toString();
	}
	
	/**
	 * 获取删除的sql语句
	 * */
	private static String getDeleteSql(String tableName, String key, String value) {
		return "delete from " + tableName + " where " + key + "=" + value;
	}
	
	
	/**
	 * 获取插入的sql语句
	 */
	private static String getInsertSql(String tableName, Map<String, String> data) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + tableName + "(");
		int size = data.keySet().size();
		int p = 0;
		for (String key : data.keySet()) {
			if (p != size - 1) {
				sql.append(key + ",");
			}else {
				sql.append(key + ") values(");
			}
			p++;

		}
		p = 0;
		for (String key : data.keySet()) {
			if (p != size - 1) {
				sql.append(data.get(key) + ",");
			}else {
				sql.append(data.get(key) + ")");
			}
			p++;

		}
		return sql.toString();
	}

	/**
	 * 获取与数据库的连接
	 */
	public static Connection getcon() {
		Connection connection = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/blog?useSSL=false";
		try {
			Class.forName(driver);
			connection = (Connection) DriverManager.getConnection(url, "root", "Whx022098");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
