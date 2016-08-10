<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<table class="table table-bordered table-striped table-hover">
<thead>
	<tr>
		<th>user name</th>
		<th>block</th>
		<th>delete</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${users }" var="user">
		<tr><td style="font-size:3.5em;">
			<a href="<spring:url value = "/users/${user.id}.html" />" >
			${user.name }
			</a>
		</td>
		<td>
			<a href="<spring:url value = "/users/${user.enabled == true ? 'block' : 'unblock'}/${user.id}.html"  />" >
			<span style="font-size:3.5em;" class="glyphicon glyphicon-${user.enabled == true ? 'ok' : 'ban'}-circle"></span>
			</a>
		</td>
		<td>
			<a href="<spring:url value = "/users/remove/${user.id}.html" />" >
			<span style="font-size:3.5em;" class="glyphicon glyphicon-trash"></span>
			</a>
		</td></tr>
	</c:forEach>
</tbody>
</table>