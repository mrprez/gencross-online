<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
		<script type="text/javascript">
		
const characterId = ${character.id};

window.addEventListener('load', (event) => {
	$.get(
		"/gencross-online/dispatcher/rest/character/${character.id}",
		( character ) => {
			$("#characterRootSpinner").remove();
			character.properties.forEach(addRootProperty);
		}
	);
});

function addRootProperty(property) {
	addProperty($("#characterRoot"), property);
}

function addProperty(parentUlElement, property) {
	parentUlElement.append(
			"<li class='propertyNode'>"
				+ "<span class='nodeIcon'></span>"
				+ "<span class='propertyLine'>"
					+ "<span class='propertyName'>"+property.name+"</span>"
				+ "</span>"
			+ "</li>"
	);
	
	const liElement = parentUlElement.children(".propertyNode").last();
	liElement.attr("propertyName", property.absoluteName);
	
	updatePropertyLineElement(liElement.children(".propertyLine"), property);
	
	if (property.subProperties != null) {
		liElement.addClass("collapsed");
		liElement.children(".nodeIcon").click(() => {
			if (liElement.hasClass("collapsed")) {
				liElement.removeClass("collapsed");
				liElement.addClass("expanded");
			} else if (liElement.hasClass("expanded")) {
				liElement.addClass("collapsed");
				liElement.removeClass("expanded");
			}
		});
		liElement.append("<ul class='subProperties'></ul>");
		property.subProperties.forEach((subProperty) => {addProperty(liElement.children(".subProperties"), subProperty)});
	} else {
		liElement.addClass("end");
	}
}

function updatePropertyLineElement(propertyLineElement, property) {
	if (property.value != null) {
		if (propertyLineElement.find(".separator").length == 0) {
			propertyLineElement.append("<span class='separator'>: </span>");
		}
		if (propertyLineElement.find(".propertyValue").length == 0) {
			propertyLineElement.append("<span class='propertyValue'></span>");
		}
		propertyLineElement.find(".propertyValue").text(property.value);
		
		if (property.editable && propertyLineElement.find(".editIcon").length == 0) {
			propertyLineElement.append("<img src='/gencross-online/img/bootstrap-icons-1.4.1/pencil.svg' class='actionIcon editIcon'/>");
			propertyLineElement.find(".editIcon").click(clickOnEditValue.bind(this, propertyLineElement));
		}
	} else {
		propertyLineElement.find(".separator").remove();
		propertyLineElement.find(".propertyValue").remove();
		propertyLineElement.find(".editIcon").remove();
	}
}
	 
function clickOnEditValue(propertyLineElement, event) {
	propertyLineElement.find(".propertyValue").append(
			"<div class='card editPropertyField'>"
				+ "<div class='card-body edit-card-body'>"
					+ "<input type='text' name='value' value='"+propertyLineElement.find(".propertyValue").text()+"'/>"
					+ "<img src='/gencross-online/img/bootstrap-icons-1.4.1/x.svg' class='editPropertyButton cancel'/>"
					+ "<img src='/gencross-online/img/bootstrap-icons-1.4.1/check.svg' class='editPropertyButton validate'/>"
				+ "</div>"
			+ "</div>");
	const cardElement = propertyLineElement.find(".propertyValue .editPropertyField");
	propertyLineElement.find(".editPropertyField input[type='text']").focus();
	propertyLineElement.find(".editPropertyField input[type='text']").keydown((e) => {
		if (e.key === "Escape") {
			cardElement.remove();
			e.preventDefault();
		}
	});
	propertyLineElement.find(".editPropertyField .editPropertyButton.cancel").click(() => {cardElement.remove()});
	propertyLineElement.find(".editPropertyField .editPropertyButton.validate").click(setPropertyValue.bind(this, propertyLineElement));
}

function setPropertyValue(propertyLineElement, event) {
	const value = propertyLineElement.find(".edit-card-body input").val();
	const propertyName = propertyLineElement.parent().attr("propertyName");
	$(this).find(".edit-card-body").remove();
	$(this).find(".propertyValue").empty();
	$(this).find(".propertyValue").append("<div class='propertyValueSpinner spinner-border' role='status'></div>");
	
	console.log("value="+value);
	console.log("propertyName="+propertyName);
	$.ajax("/gencross-online/dispatcher/rest/character/"+characterId+"/setValue", {
		method: "PUT",
		data: { 'property': propertyName, 'value': value }
	}).done(refreshCharacter);
	
}

function refreshCharacter(character) {
	console.log(character);
	const liElements = $("#characterRoot").children();
	
	let propertyIndex = 0;
	let liElementIndex = 0;
	
	while (propertyIndex<character.properties.length && liElementIndex<liElements.length) {
		if (character.properties[propertyIndex].absoluteName == liElements.eq(liElementIndex).attr("propertyName")) {
			const propertyLineElement = liElements.eq(liElementIndex).children(".propertyLine");
			updatePropertyLineElement(propertyLineElement, character.properties[propertyIndex]);
			propertyIndex++;
			liElementIndex++;
		} else {
			console.log(character.properties[propertyIndex].absoluteName);
			console.log(liElements.eq(liElementIndex).attr("propertyName"));
			propertyIndex++;
			liElementIndex++;
		}
		
	}
	
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