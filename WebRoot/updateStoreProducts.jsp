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
		<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script type="text/javascript">
	i = 1;
	$(document).ready(function(){
		
		$("#btn_add1").click(function(){
			document.getElementById("newUpload1").innerHTML+='<div id="div_'+i+'"><input  name="file" type="file"  /><input type="button" value="删除"  onclick="del_1('+i+')"/></div>';
			  i = i + 1;
		});
	});

	function del_1(o){
	 document.getElementById("newUpload1").removeChild(document.getElementById("div_"+o));
	}
</script>
	</head>

	<body>
		<mvc:form modelAttribute="storeProducts"
			action="StoreProduct/updateStoreProduct" method="post"
			enctype="multipart/form-data">

			<mvc:hidden path="productID" />

			<div id="" class="">
				<div id="" class="">
					豪管家商城管理中心 > 修改商品
				</div>
				<div class="" align="right">

				</div>
				<div id="" class="" align="center">
					修改商品
				</div>
				<br>
				<div class='col-xs-3'></div>
				<div id="" class="col-xs-6" >
					<table class="table table-bordered ">
						<tr>
							<td>
								商品名称：
							</td>
							<td>
								<mvc:input path="productName" />
								<font size="" color="red">*</font>
							</td>
						</tr>

						<tr>
							<td>
								原价：
							</td>
							<td>
								<mvc:input path="productPrice" />
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								售价：
							</td>
							<td>
								<mvc:input path="productRealityPrice" />
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								商品类别：
							</td>
							<td>
								<mvc:select path="ptID">
									<c:forEach items="${storeProductTypesList}" var="pt">
										<c:if test="${pt.ptParentID==0}">
											<option value="${pt.ptID}">
												${pt.ptName}
											</option>
											<c:forEach items="${storeProductTypesList}" var="ptt"
												varStatus="vs">
												<c:if test="${ptt.ptParentID!=0&&ptt.ptParentID==pt.ptID}">
													<option value="${ptt.ptID}">
														<c:if test="${not vs.last}">├</c:if>
														<c:if test="${vs.last}">└</c:if>
														${ptt.ptName}
													</option>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</mvc:select>
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								商品颜色
							</td>
							<td>
								<mvc:select path="pcID" items="${storeProductColorsList}"
									itemLabel="pcName" itemValue="pcID"></mvc:select>
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								商品规格
							</td>
							<td>
								<mvc:select path="psID" items="${storeProductSpecsList}"
									itemLabel="psName" itemValue="psID"></mvc:select>
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								商品单位
							</td>
							<td>
								<mvc:select path="puID" items="${storeProductUnitsList}"
									itemLabel="puName" itemValue="puID"></mvc:select>
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								商品库存
							</td>
							<td>
								<mvc:input path="productStock" />
								<font size="" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td>
								商品图片
							</td>
							<td>
								<c:forEach items="${imgs}" var="img">
									 <input type="checkbox" name="imgs" checked="checked" value="${img}">
									 <img src="<%=path%>/images/${img}" width="200" height="100" alt="加载中..."><br/>
								</c:forEach>
								<div id="newUpload1">
									<input type="file" name="file">
								</div>

								<input type="button" id="btn_add1" value="增加一张">
							</td>
						</tr>
						<tr>
							<td>
								商品描述：
							</td>
							<td>
								<mvc:textarea path="productDesc" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">

								<input type="submit" value="修改">
								<input type="button" value="返回" onclick=
	history.back();
>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</mvc:form>
	</body>
</html>
