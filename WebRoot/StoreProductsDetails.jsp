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

		<title>My JSP 'StoreProductsAdd.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>

	</head>

	<body>
			<div id="" class="">
				<div id="" class="">
					豪管家商城管理中心 > 商品详情
				</div>
				<div class="" align="right">
				</div>
				<div id="" class="" align="center">
					商品详情
				</div>
				<br>
				<div id="" class="" >
					<table border align="center">
						<tr>
							<td>
								商品名称：
							</td>
							<td>
								${p.productName}
							</td>
						</tr>
						
						<tr>
							<td>
								原价：
							</td>
							<td>
								${p.productPrice}
							</td>
						</tr>
						<tr>
							<td>
								售价：
							</td>
							<td>
								${p.productRealityPrice}
							</td>
						</tr>
						<tr>
							<td>
								商品类别：
							</td>
							<td>
							${p.ptName }
							</td>
						</tr>
						<tr>
							<td>
								商品颜色
							</td>
							<td>
								${p.pcName }
							</td>
						</tr>
						<tr>
							<td>
								商品规格
							</td>
							<td>
								${p.psName }
							</td>
						</tr>
						<tr>
							<td>
								商品单位
							</td>
							<td>
								${p.puName }
							</td>
						</tr>
						<tr>
							<td>
								商品库存
							</td>
							<td>
								${p.productStock }
							</td>
						</tr>
												<tr>
							<td>商品销量：
							</td>
							<td>${p.productSaleCount}
							</td>
						</tr>
												<tr>
							<td>上架时间：
							</td>
							<td>
								<fmt:formatDate value="${p.productDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr>
							<td>
								商品图片
							</td>
							<td>
							<c:forEach items="${imgs}" var="img">
								  <img src="<%=path%>/images/${img}" width="200" height="100" alt="加载中..."><br/>
							</c:forEach>
							</td>
						</tr>
						<tr>
							<td>
								商品描述：
							</td>
							<td>
								${p.productDesc }
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">

								<input type="button" value="确定" onclick="history.back()">
							</td>
						</tr>
					</table>
				</div>
			</div>
	</body>
</html>
