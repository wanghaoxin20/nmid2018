<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="util.DataBaseUtil"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="entity.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<sql:setDataSource driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/blog?useSSL=false" user="root"
	password="Whx022098" var="blog" /> <%/******設置数据库******/ %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:choose>
	<c:when test="${islogin && user != null}"> <%//检测信息 %>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>${user.username}的博客</title> <%/**************/ %>
		<link rel="stylesheet" href="css/style.css" type="text/css"
			media="all" />
</head>
<body>
	<!-- Header -->
	<div id="header">
		<div class="shell">
			<!-- Logo + Top Nav -->
			<div id="top">
				<h1>
					<a href="#">MagicWolf</a>
				</h1>
				<div id="top-navigation">
					欢迎&nbsp;&nbsp;<a href="#"><strong>${user.username}</strong></a> <span>|</span> <%/**************/ %>
					<a href="LoginOut">退出</a>
				</div>
			</div>
			<!-- End Logo + Top Nav -->

			<!-- Main Nav -->
			<div id="navigation">
				<ul>
					<li><a href="index.jsp" class="active"><span>首页</span></a></li>
					<li><a href="publish.jsp"><span>发布文章</span></a></li>
				</ul>
			</div>
			<!-- End Main Nav -->
		</div>
	</div>
	<!-- End Header -->

	<!-- Container -->
	<div id="container">
		<div class="shell">

			<!-- Small Nav -->
			<div class="small-nav">
				<a href="index.jsp">首页</a> <span>&gt;</span> 文章列表
			</div>
			<!-- End Small Nav -->

			<br />
			<!-- Main -->
			<div id="main">
				<div class="cl">&nbsp;</div>

				<!-- Content -->
				<div id="content">

					<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2 class="left">文章列表</h2>
							<div class="right">
								<label>搜索文章</label>
								<form action="search.jsp" method="post">
									<input type="text" name="keyword" class="field small-field" />
									<input type="submit" class="button" value="搜索" />
								</form>
							</div>
						</div>
						<!-- End Box Head -->

						<!-- Table -->
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th width="13"><input type="checkbox" class="checkbox" /></th>
									<th>标题</th>
									<th>日期</th>
									<th>作者</th>
									<th width="110" class="ac"></th>
								</tr>
								<%/******sql查询********/ %>
								<sql:query var="blog_info" dataSource="${blog}">
									select * from blog_info where id in (select blog_id from userblog where username='${user.username}')
								</sql:query>
								
								<%/******遍历结果********/ %>
								<c:forEach items="${blog_info.rows}" var="row">
									<%/************************/ %>
									<tr>
										<td><input type="checkbox" class="checkbox" /></td>
										<td><h3>
												<a href="ShowBlog?opt=show&id=${row.id}">${row.title}</a>
											</h3></td>
										<td>${row.publishDate }</td>
										<td><a href="#">${user.username}</td>
										<td><a href="DelBlog?id=${row.id}" class="ico del">删除</a><a
											href="ShowBlog?opt=update&id=${row.id}" class="ico edit">编辑</a></td>
									</tr>
									<%/************************/ %>
								</c:forEach>

							</table>


							<!-- Pagging -->
							<div class="pagging">
								<div class="left">1-2</div>
								<div class="right">
									<a href="#">上一页</a> <a href="#">1</a> <a href="#">2</a> <a
										href="#">3</a> <a href="#">4</a> <a href="#">245</a> <span>...</span>
									<a href="#">下一页</a> <a href="#">最后一页</a>
								</div>
							</div>
							<!-- End Pagging -->

						</div>
						<!-- Table -->

					</div>
					<!-- End Box -->

				</div>
				<!-- End Content -->
				<div class="cl">&nbsp;</div>
			</div>
			<!-- Main -->
		</div>
	</div>
	<!-- End Container -->

	<!-- Footer -->
	<div id="footer">
		<div class="shell">
			<span class="left">&copy; 2018 - MrWangx</span> <span class="right">
				Design by <a href="http://chocotemplates.com" target="_blank"
				title="The Sweetest CSS Templates WorldWide">Chocotemplates.com</a>
			</span>
		</div>
	</div>
	<!-- End Footer -->

</body>
</html>
</c:when>
<c:otherwise>
	<c:redirect url="login.html" />
</c:otherwise>
</c:choose>


