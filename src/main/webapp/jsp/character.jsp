<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
		<script type="text/javascript">

var shownEditPropertyField = null;

window.addEventListener('load', (event) => {
	$.get(
		"/gencross-online/dispatcher/rest/character/${character.id}",
		function( character ) { 
			$("#characterRootSpinner").remove();
			character.properties.forEach(addRootProperty);
		});
	
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
		var propertyForm = $(this).parents(".propertyForm");
		$.post(
			"/gencross-online/dispatcher/rest/character/${character.id}/setValue",
			propertyForm.serialize());
	});
});

function addRootProperty(property) {
	addProperty(document.getElementById("characterRoot"), property);
}

function addProperty(parentUlElement, property) {
	const liElement = document.createElement("li");
	liElement.setAttribute("propertyName", property.absoluteName);
	liElement.classList.add("propertyNode");
	parentUlElement.appendChild(liElement);
	
	const nodeIconElement = document.createElement("span");
	nodeIconElement.classList.add("nodeIcon");
	liElement.appendChild(nodeIconElement);
	
	const textElement = document.createElement("span");
	textElement.appendChild(document.createTextNode(property.name));
	liElement.appendChild(textElement);
	
	if (property.value != null) {
		textElement.appendChild(document.createTextNode(": "));
		textElement.appendChild(document.createTextNode(property.value));
		if (property.editable) {
			const editIconElement = document.createElement("img");
			editIconElement.src = "/gencross-online/img/bootstrap-icons-1.4.1/pencil.svg";
			editIconElement.classList.add("actionIcon");
			liElement.appendChild(editIconElement);
			$(editIconElement).click(clickOnEditValue);
		}
	}
	
	if (property.subProperties != null) {
		liElement.classList.add("collapsed");
		nodeIconElement.addEventListener("click", () => {
			if (liElement.classList.contains("collapsed")) {
				liElement.classList.remove("collapsed");
				liElement.classList.add("expanded");
			} else if (liElement.classList.contains("expanded")) {
				liElement.classList.add("collapsed");
				liElement.classList.remove("expanded");
			}
		});
		const ulElement = document.createElement("ul");
		liElement.appendChild(ulElement);
		property.subProperties.forEach((subProperty) => {addProperty(ulElement, subProperty)});
	} else {
		liElement.classList.add("end");
	}
}

function clickOnEditValue(event) {
	const liElement = event.currentTarget.parentElement;
	const cardElement = document.createElement("div");
	cardElement.classList.add("card");
	cardElement.classList.add("editPropertyField");
}

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
						<ul id="characterRoot">
							<div id="characterRootSpinner" class="spinner-border" role="status"></div>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>