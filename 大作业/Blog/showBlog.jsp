<%@page import="entity.User"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entity.Blog"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:choose>
	<c:when test="${islogin && blog != null }"> <%/********检测信息成功***********/ %>
		<%
			Blog blog = (Blog) request.getAttribute("blog");
			String[] date = blog.getPublishDate().split(" ")[0].split("-");
		%>
		<c:set var="date" value="<%=date %>" scope="page" /> <%/*******设置日期变量********/ %>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>${blog.blogTitle}</title>
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
					<a href="##t">xxxx</a>
				</h1>
				<div id="top-navigation">
					欢迎&nbsp;&nbsp;<a href="#"><strong>${user.username }</strong> </a> <span>|</span>
					<a href="LoginOut">退出</a>
				</div>

				<!-- End Logo + Top Nav -->

				<!-- Main Nav -->
				<div id="navigation">
					&nbsp;
					<ul>

						<li><a href="##" class="active"><span>文章内容</span> </a></li>
					</ul>
				</div>
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
				<a href="index.jsp">首页</a> <span>&gt;</span> 文章内容
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
							<h2>文章内容</h2>
						</div>
						<!-- End Box Head -->

						<form action="edited" method="post">

							<!-- Form -->
							<div class="form">
								<p>

									<label>作者<span>*</span>
									</label> <label>${blog.author }</label><%/*******************/ %>
								</p>
								<p>

									<label>标题<span>*</span>
									</label> <label>${blog.blogTitle }</label><%/*******************/ %>
								</p>
								<p>

									<label>类型<span>*</span>
									</label> <label>暂无</label>
								</p>

								<p class="inline-field">
									<label>Date</label><br><%/*******日期************/ %>
									<lable class="field size2">${date[0]}</lable>
									<lable class="field size2">年</lable>
									<lable class="field size3">${date[1]}</lable>
									<lable class="field size2">月</lable>
									<lable class="field size3">${date[2]}</lable>
									<lable class="field size2">日</lable>
								</p>

								<p>
									<label>内容<span>*</span>
									</label>
								<div class="contentStyle">
									<%/**********内容*********/ %>
									<label><%=blog.getBlogContent().replaceAll("\r\n", "<br>").replaceAll("<br><br>", "<br>")%></label>
								</div>
								</p>

							</div>
							<!-- End Form -->

							<!-- Form Buttons -->

							<!-- End Form Buttons -->
						</form>
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
			<span class="left">&copy; 2018 - For MrWangx</span> <span
				class="right"> Design by <a href="##" target="_blank"
				title="The Sweetest CSS Templates WorldWide">xxxTeam</a>
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
