<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	padding-top: 70px;
}
/* ITEM CSS */
.item-image{
	max-width: 250px;
	max-height: 250px;
}
/* ITEM CSS ENDED */
/* CUSTOM AUTOCOMPLETE CSS */
.ui-autocomplete {
	position: absolute;
	z-index: 1000;
	cursor: default;
	padding: 0;
	margin-top: 2px;
	list-style: none;
	background-color: #ffffff;
	border: 1px solid #ccc -webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
}

.ui-autocomplete>li {
	padding: 3px 20px;
}

.ui-autocomplete>li.ui-state-focus {
	background-color: #DDD;
}

.ui-helper-hidden-accessible {
	display: none;
}
/* CUSTOM AUTOCOMPLETE CSS ENDED */
</style>

<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<%-- <spring:url value="/search.html" var="search_url" /> --%>
<script type="text/javascript">
$(function() {
  $(".autocomplete").autocomplete({
    source: function( request, response ) {
    	        $.ajax( {
    	          url: "<spring:url value='/search.html'/>",
    	          dataType: "text",
    	          data: {
    	            q : request.term
    	          },
    	          success: function( data ) {
    	            // Handle 'no match' indicated by [ "" ] response
    	            response( data.length === 1 && data[ 0 ].length === 0 ? [] : data );
    	          }
    	        });
    		}
  });
});
</script>
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
		prefix="tilesx"%>
	<%@ taglib uri="http://www.springframework.org/security/tags"
		prefix="security"%>

	<tilesx:useAttribute name="current" />

	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<spring:url value="/"/>">Web Store</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<ul class="nav navbar-nav navbar-right">
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<li class="${current == 'users' ? 'active' : ''}"><a
							href="<spring:url value="/users.html"/>">Users</a></li>
						<li class="${current == 'item_add' ? 'active' : ''}"><a
							href="<spring:url value="/items/add.html"/>">Add item</a></li>
					</security:authorize>
					<security:authorize access="! isAuthenticated()">
						<li class="${current == 'register' ? 'active' : ''}"><a
							href="<spring:url value="/register.html"/>">Sign in</a></li>
						<li class="${current == 'login' ? 'active' : ''}"><a
							href="<spring:url value="/login.html"/>">Log in</a></li>
					</security:authorize>

					<security:authorize access="isAuthenticated()">
						<%-- 						<li class="${current == 'account' ? 'active' : ''}"><a --%>
						<%-- 							href="<spring:url value="/account.html"/>">My account</a></li> --%>
						<li class="${current == 'cart' ? 'active' : ''}"><a
							href="<spring:url value="/cart.html"/>">My cart</a></li>
						<li><a href="<spring:url value="/logout"/>">Log out</a></li>
					</security:authorize>

				</ul>
				<form action='<spring:url value="/search.html" />' method="post" class="navbar-form"
					style="text-align: center;">
					<!-- 					<div class="col-xs-10 col-xs-offset-9"> -->
					<div class="input-group">
						<input type="text" class="form-control autocomplete" id="search"
							name="search" placeholder="Search for..."> <span
							class="input-group-btn">
							<button class="btn btn-default" type="submit">Go!</button>
						</span>
					</div>
				</form>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<div class="container">
		<!-- 		<div style="min-height: 2000;"> -->
		<tiles:insertAttribute name="body" />
		<!-- </div> -->
		<br> <br>

		<div style="text-align: center; bottom: 0;">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>