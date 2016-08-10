<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../defs/taglibs.jsp"%>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<form:form commandName="item" enctype="multipart/form-data"  method="POST"
	cssClass="form-horizontal itemAddForm">
	<c:if test="${success eq true}">
		<div class="alert alert-success">Added successfully!</div>
	</c:if>
	<div class="form-group">
		<label for="name" class="col-sm-2 control-label">Name:</label>
		<div class="col-sm-10">
			<form:input path="name" cssClass="form-control" />
			<form:errors path="name" />
		</div>
	</div>
	<div class="form-group">
		<label for="image" class="col-sm-2 control-label">Image:</label>
		<div class="col-sm-10">
			<input id="imageFile" name="imageFile" type="file" class="form-control input-sm" />
<%-- 			<form:errors path="image" class="help-inline"/> --%>
		</div>
	</div>
	<div class="form-group">
		<label for="price" class="col-sm-2 control-label">Price: </label>
		<div class="col-sm-10">
			<form:input path="price" cssClass="form-control" />
			<form:errors path="price" />
		</div>
	</div>
	<div class="form-group">
		<label for="descriptionShort" class="col-sm-2 control-label">Short description: </label>
		<div class="col-sm-10">
			<form:textarea path="descriptionShort" cssClass="form-control" rows="3"/>
			<form:errors path="descriptionShort" />
		</div>
	</div>
	<div class="form-group">
		<label for="descriptionLong" class="col-sm-2 control-label">Long description: </label>
		<div class="col-sm-10">
			<form:textarea path="descriptionLong" cssClass="form-control" rows="5"/>
			<form:errors path="descriptionLong" />
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-5 col-sm-2">
			<input type="submit" value="Add" class="btn btn-lg btn-primary" />
		</div>
	</div>
</form:form>
<script type="text/javascript">
	$(document).ready(
			function() {
				$(".itemAddForm").validate(
						{
							rules : {
								name : {
									required : true,
									minlength : 3,
									remote: {
										url : "<spring:url value='/items/available.html' />",
										type: "get",
										data : {
											name : function(){
												return $("#name").val();
											}
										}
									}
								},
								image : {
									required : true,
									accept: "image/*"
								},
								price : {
									required : true,
									number : true,
									min : 1
								},
								descriptionShort : {
									required : true,
									minlength : 10,
								},
								descriptionLong : {
									required : true,
									minlength : 10
								}
							},
							highlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-success').addClass('has-error');
							},
							unhighlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-error').addClass('has-success');
							},
							messages : {
								name : {
									remote : "Such username already exists!"
								}
							}

						});
			});
</script>