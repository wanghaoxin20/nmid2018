package entity;

/**
 * 博客
 * */

public class Blog {
	
	private String blogID; //博客的id
	private String blogTitle;  //博客标题
	private String publishDate;  //博客发布日期
	private String blogContent; //博客内容
	private String author;  //博客作者
	
	public Blog() {};
	
	public Blog(String blogID, String blogTitle, String publishDate, String blogContent, String author) {
		setBlogID(blogID);
		setBlogTitle(blogTitle);
		setPublishDate(publishDate);
		setBlogContent(blogContent);
		setAuthor(author);
	}
	
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public String getBlogID() {
		return blogID;
	}

	public void setBlogID(String blogID) {
		this.blogID = blogID;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
}
