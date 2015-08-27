<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加离职公司信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <div id="" class="">
	<h1 align="center">添加离职公司信息</h1>
	<mvc:form action="addT">
  <table border align="center">
  <tr>
	<td>姓名:</td>
	<td><input type="text" name="name"></td>
  </tr>
  <tr>
	<td>离职公司:</td>
	<td><input type="text" name="js"></td>
  </tr>
  <tr align="center">
	<td colspan="2"><input type="submit" value="添加"> <input type="reset" value="清空"></td>
  </tr>
  </table>
  </mvc:form>
 </div>
  </body>
</html>
