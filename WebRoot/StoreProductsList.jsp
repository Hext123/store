<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'StoreProductsList.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	</head>

	<body>
		<mvc:form modelAttribute="storeProducts" action="StoreProduct/findStoreProduct">
			<div id="" class="">
				<div id="" class="">
					豪管家商城管理中心 > 商品管理
				</div>
				<div class="" align="right">
					<input type="button" value="添加商品" onclick="location.href='<%=path%>/StoreProduct/initAddStoreProduct'">
				</div>
				<div id="" class="" align="center">
					所属分类：
					<select name="ptID">
						<option value="0" selected>
							所有分类</option>
										<c:forEach items="${storeProductTypesList}" var="pt">
											<c:if test="${pt.ptParentID==0}">
												<option value="${pt.ptID}">
													${pt.ptName}
												</option>
												<c:forEach items="${storeProductTypesList}" var="ptt" varStatus="vs">
													<c:if
														test="${ptt.ptParentID!=0&&ptt.ptParentID==pt.ptID}">
														<option value="${ptt.ptID}">
															<c:if test="${not vs.last}">├</c:if>
															<c:if test="${vs.last}">└</c:if>
															${ptt.ptName}
														</option>
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
					</select>
					商品名称：
					<input type="text" name="productName">
					<input type="submit" value="搜索">
				</div>
				<br>
				<div id="" class="" align="center"  style="width: 500" >
					<table class="table table-hover table-bordered"> 
						<tr>
							<th>
								商品名称
							</th>
							<th>
								单价
							</th>
							<th>
								上架时间
							</th>
							<th>
								库存
							</th>
							<th>
								销量
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach items="${storeProductsList}" var="li">
						<tr>
							<td>${li.productName}</td>
							<td>${li.productPrice}</td>
							<td><fmt:formatDate value="${li.productDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${li.productStock}</td>
							<td>${li.productSaleCount}</td>
							<td>
								<a href="StoreProduct/findStoreProductByID?id=${li.productID}">详情</a>
								<a href="StoreProduct/initUpdateStoreProduct?id=${li.productID}">修改</a>
								<a href="StoreProduct/offShelvesStoreProduct?id=${li.productID}">下架</a>
							</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</mvc:form>
	</body>
</html>
