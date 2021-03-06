<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>标记是否下载</title>
<style type="text/css"<!--
.normal {  font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; font-weight: normal; color: #000000}
.medium {  font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 15px; font-weight: bold; color: #000000; text-decoration: none}
--></style>
</head>
<body style="height:900px; overflow-y:scroll; scrollbar-base-color:#ff6600; border:1px solid;" >
<table border=1  align='center'>
	<c:forEach var="data" items="${data}">
	<tr>	
		<td class='normal' valign='top'><a target="_blank" href="${data.url}">${data.title}</a></td>	
		<td class='normal' valign='top'>${data.id}</td>	
		<td class='normal' valign='top'><a target="_blank" href="${ctx}/flag/${data.id}/1">下载</a></td>	
		<td class='normal' valign='top'><a target="_blank" href="${ctx}/flag/${data.id}/0">不下载</a></td>	
	</tr>
	</c:forEach>
</table>
</html>