<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../defs/taglibs.jsp"%>
<div class="alert alert-danger">Work in progress</div>
<c:choose>
	<c:when test="${user != null }">
	<form:form commandName="user"
	cssClass="form-horizontal updateForm">
	<c:if test="${success eq true}">
		<div class="alert alert-success">Update successful!</div>
	</c:if>
	<div class="form-group">
		<label for="name" class="col-sm-2 control-label">Name</label>
		<div class="col-sm-10">
			<form:input path="name" cssClass="form-control" value="${user.name }" />
			<form:errors path="name" />
		</div>
	</div>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">Email</label>
		<div class="col-sm-10">
			<form:input path="email" cssClass="form-control" value="${user.email }"/>
			<form:errors path="email" />
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password</label>
		<div class="col-sm-10">
			<form:password path="password" cssClass="form-control" />
			<form:errors path="password" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-5 col-sm-2">
<!-- 			<input type="submit" value="Update" class="btn btn-lg btn-primary" /> -->
		</div>
	</div>
</form:form>
	</c:when>
	<c:otherwise>
	<h2><c:out value="Not found" /></h2>
	</c:otherwise>
</c:choose>