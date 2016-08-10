<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../defs/taglibs.jsp"%>

<style>
.ratings {
font-size: large;
}
</style>
<div class="row">
	<c:forEach items="${items }" var="item">
		<div class="col-xs-6 col-sm-4 col-lg-4 col-md-4">
			<div class="thumbnail">
				<a href="<spring:url value="/items/${item.id}.html" />"><img
					class="img-rounded item-image" src="<spring:url value="/items/getImage.html?id=${item.id}" />" ></a>
				<div class="caption">

					<h4>
						<a href="<spring:url value="/items/${item.id}.html" />">${item.name}</a>
					</h4>
					<h4>
						<fmt:formatNumber value="${item.price }" type="currency" />
					</h4>
					<div class="ratings">
						<p class="pull-right">reviews: ${item.reviews.size() }</p>
						<p>
							<c:forEach var="i" begin="1" end="5">
								<span
									class="glyphicon glyphicon-star${i <= item.point ? '' : '-empty'}"></span>
							</c:forEach>
						</p>
					</div>
					<!-- 					TODO поміняти на аякс								-->
					<%-- 					<security:authorize access="isAuthenticated()"> --%>
					<!-- 						<p> -->
					<!-- 							<a -->
					<%-- 								href="<spring:url value="/cart/add/${item.id }.html" />" --%>
					<!-- 								class="btn btn-success">Add to cart</a> -->
					<!-- 						</p> -->
					<%-- 					</security:authorize> --%>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
