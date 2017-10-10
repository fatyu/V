<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns=http://www.w3.org/1999/xhtml>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<base target="_blank">
<style>
body {
	padding: 0;
	margin: 0;
	background: #F5F4F2;
	font-size: 14px;
	color: #992222;
	font-family: "微软雅黑", sans-serif;
}

ul, dl, dt, dd, p {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

a {
	text-decoration: none;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.head {
	width: 960px;
	height: 65px;
	margin: 0 auto;
}

.logo {
	width: 130px;
	height: 35px;
	margin-top: 15px;
}

.logo .logoimg {
	display: inline-block;
	width: 160px;
	height: 35px;
	background: url(images/logo.png) no-repeat;
	text-indent: -999px;
}

.title {
	width: 80px;
	height: 35px;
	margin-top: 20px;
	color: #909090;
	font-size: 20px;
}

.search {
	width: 352px;
	height: 35px;
	margin: 15px 0 0 100px;
}

.search .text {
	width: 268px;
	height: 33px;
	padding: 0 10px;
	border: 1px solid #EBE9E7;
	background: #FFFFFF;
	line-height: 33px;
	font-size: 15px;
	color: #999999;
}

.search .button {
	border: 0;
	width: 62px;
	height: 35px;
	background: #B24422;
	line-height: 35px;
	font-size: 15px;
	color: #FFF;
	text-align: center;
}

.search .button:hover {
	background: #992222;
}

.favorite {
	width: 90px;
	height: 50px;
	background: #E8E6E3;
	text-align: center;
}

.favorite a {
	color: #992222;
	font-size: 14px;
	display: inline-block;
	margin-top: 12px;
}

.favorite a:hover {
	background: #992222;
	color: #FFF;
}

.content {
	width: 920px;
	margin: 20px auto;
	background: #FFF;
	padding: 1px 20px;
}

.content h2 {
	line-height: 25px;
	color: #909090;
	font-size: 18px;
	font-weight: normal;
}

.content ul {
	width: 920px;
	margin: 0 auto;
	border-left: 1px solid #DDD;
	border-top: 1px solid #DDD;
	margin-bottom: 20px;
	overflow: hidden;
}

.content ul li {
	float: left;
	width: 183px;
	height: 35px;
	border-right: 1px solid #DDD;
	border-bottom: 1px solid #DDD;
}

.content ul li a {
	margin: 7px 0 0 20px;
	display: inline-block;
	color: #992222;
}

.content ul li a:hover {
	background: #992222;
	color: #FFF;
}

.footer {
	width: 960px;
	height: 60px;
	margin: 0 auto;
}

.footer .fleft {
	width: 500px;
	height: 60px;
}

.footer .fleft p {
	line-height: 25px;
	color: #909090;
	font-size: 12px;
}

.footer .fleft .p1 a {
	color: #992222;
	margin-right: 10px;
}

.footer .fleft .p2 a {
	color: #909090;
}

.footer .fright {
	width: 450px;
	height: 60px;
}

.footer .fright p {
	line-height: 50px;
	color: #909090;
	text-align: center;
}
</style>
</head>
<body>
	<div class="head">
		<div class="logo fl">
			<a href="/" class="logoimg" target="_self">site.notalk.cc</a>
		</div>
	</div>
	<div class="content">
		<h2>${categoryName}</h2>
		<ul>
			<li><a href="${href}">${name}</a></li>
		</ul>
	</div>
</body>
</html>
