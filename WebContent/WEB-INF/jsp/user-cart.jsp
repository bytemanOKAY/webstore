<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../defs/taglibs.jsp"%>

<table class="table table-bordered table-striped table-hover">
<thead>
	<tr>
		<th>Photo</th>
		<th>Name</th>
		<th>Price</th>
		<th>Delete</th>
	</tr>
</thead>
<tbody>
	<c:set var="summary" value="0" />
	<c:forEach items="${user.cart }" var="item">
		<c:set var="summary" value="${summary + item.price }" />
		<tr>
		<td style="text-align:center;vertical-align: center">
			<a href="<spring:url value = "/items/${item.id}.html" />" >
			<img class="img-rounded" src="<spring:url value="/items/getImage.html?id=${item.id}" />" height="150"
					width="150">
			</a>
		</td>
		<td style="text-align:center;vertical-align: center;">
			<a href="<spring:url value = "/items/${item.id}.html" />" >
			${item.name }
			</a>
		</td>
		<td style="text-align:center;vertical-align: center;">
			<fmt:formatNumber value="${item.price }" type="currency"/>
			
		</td>
		<td style="text-align:center;vertical-align: center;">
			<a href="<spring:url value = "/cart/remove/${item.id}.html" />" >
			<span style="font-size:3.5em;" class="glyphicon glyphicon-trash"></span>
			</a>
			
		</td></tr>
	</c:forEach>
</tbody>
</table>
<h2 class="text-right">Summary: ${summary}</h2>