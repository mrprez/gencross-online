<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
		<script type="text/javascript">
window.addEventListener('load', (event) => {
	$(".propertyNode .nodeIcon").click(function() {
		var propertyNode = $(this).parent();
		if (propertyNode.attr("class").includes("collapsed")) {
			propertyNode.removeClass("collapsed");
			propertyNode.addClass("expanded");
		} else if (propertyNode.attr("class").includes("expanded")) {
			propertyNode.removeClass("expanded");
			propertyNode.addClass("collapsed");
		}
	});
});

var shownEditPropertyField = null;

window.addEventListener('load', (event) => {
	$(".propertyNode .editPropertyContainer .editIcon").click(function() {
		if (shownEditPropertyField) {
			shownEditPropertyField.hide();
			shownEditPropertyField = null;
		}
		var editPropertyContainer = $(this).parent();
		shownEditPropertyField = editPropertyContainer.children(".editPropertyField");
		shownEditPropertyField.show();
	});
	$(".editPropertyButton.close").click(function() {
		shownEditPropertyField.hide();
		shownEditPropertyField = null;
	});
	$(".editPropertyButton.valid").click(function() {
		$.ajax(
			"/gencross-online/dispatcher/character/setValue",
			{ method: 'POST'});
	});
});

		</script>
	</head>
	<body class="character">
		<%@ include file="include/top.jsp" %>
		<div class="container">
			<div class="row align-items-center">
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">${character.name}</h5>
						</div>
						<ul>
							<c:set var="characterData" value="${character.data}" scope="request"></c:set>
							<c:forEach items="${character.data.properties}" var="property">
								<c:import url="include/characterProperty.jsp">
									<c:param name="propertyAbsoluteName" value="${property.absoluteName}"/>
								</c:import>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>