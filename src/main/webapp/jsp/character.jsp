<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
		<script type="text/javascript">

"use strict";
		
const characterId = ${character.id};
const messages = new Object();
messages.chooseOption='<fmt:message key="label.chooseOption"/>';

window.addEventListener('load', (event) => {
	$.get(
		"/gencross-online/dispatcher/rest/character/${character.id}",
		( character ) => {
			$("#characterRootSpinner").remove();
			character.properties.forEach(displayRootProperty);
		}
	);
});

function displayRootProperty(property) {
	displayProperty($("#characterRoot"), property);
}

function displayProperty(parentUlElement, property) {
	if (parentUlElement.children("[propertyName='"+property.absoluteName+"']").length == 0) {
		parentUlElement.append(
				"<li class='propertyNode'>"
					+ "<span class='nodeIcon'></span>"
					+ "<span class='propertyLine'>"
						+ "<span class='propertyName'>"+property.name+"</span>"
					+ "</span>"
				+ "</li>"
		);
		parentUlElement.children(".propertyNode").last().attr("propertyName", property.absoluteName);
	}
	
	const liElement = parentUlElement.children("[propertyName='"+property.absoluteName+"']");
	
	updatePropertyLineElement(liElement.children(".propertyLine"), property);
	
	if (property.subProperties == null) {
		liElement.removeClass("collapsed");
		liElement.removeClass("expanded");
		liElement.addClass("end");
	} else {
		if (liElement.children("ul.subProperties").length == 0) {
			liElement.removeClass("end");
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
		}
		
		const subPropertiesUlElement = liElement.children(".subProperties");
		property.subProperties.forEach((subProperty) => {displayProperty(subPropertiesUlElement, subProperty)});
		
		subPropertiesUlElement.children(".addAction").remove();
		if (!property.subPropertiesListFixe) {
			subPropertiesUlElement.append("<span class='addAction'><span class='addActionText'>Ajouter</span></span>");
			const addActionElement = subPropertiesUlElement.children(".addAction");
			addActionElement.children(".addActionText").click(clickOnAddProperty.bind(this, addActionElement, property));
		}
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
			"<dialog class='card editPropertyCard flyingCard' open>"
				+ "<form class='card-body flyingCardBody' method='dialog'>"
					+ "<input type='text' name='value' value='"+propertyLineElement.find(".propertyValue").text()+"'/>"
					+ "<input type='image' src='/gencross-online/img/bootstrap-icons-1.4.1/x.svg' class='flyingCardButton cancel'/>"
					+ "<input type='image' src='/gencross-online/img/bootstrap-icons-1.4.1/check.svg' class='flyingCardButton validate'/>"
				+ "</form>"
			+ "</dialog>");
	const cardElement = propertyLineElement.find(".propertyValue .editPropertyCard");
	propertyLineElement.find(".editPropertyCard input[type='text']").focus();
	propertyLineElement.find(".editPropertyCard input[type='text']").keydown((e) => {
		if (e.key === "Escape") {
			cardElement.remove();
			e.preventDefault();
		}
	});
	propertyLineElement.find(".editPropertyCard .cancel").click(() => {cardElement.remove()});
	propertyLineElement.find(".editPropertyCard .validate").click(setPropertyValue.bind(this, propertyLineElement));
}

function clickOnAddProperty(addActionElement, parentProperty, event) {
	$(addActionElement).append(
		"<dialog class='card addPropertyCard flyingCard' open>"
			+ "<form class='card-body flyingCardBody' method='dialog'>"
				+ "<input type='image' src='/gencross-online/img/bootstrap-icons-1.4.1/x.svg' class='flyingCardButton cancel'/>"
				+ "<input type='image' src='/gencross-online/img/bootstrap-icons-1.4.1/check.svg' class='flyingCardButton validate'/>"
			+ "</form>"
		+ "</dialog>");
	const cardElement = $(addActionElement).find(".addPropertyCard");
	if (parentProperty.subPropertiesListOptions != null && parentProperty.subPropertiesListOptions.length > 0 && parentProperty.subPropertiesListOpen) {
		cardElement.find("form.flyingCardBody").prepend(
				"<div class='addPropertyInputGroup'>"
					+ "<select name='addPropertySelect' class='addPropertySelect' required><option value='' hidden>"+messages.chooseOption+"</option></select>"
					+ "<input type='text' name='addPropertyText' class='addPropertyText'/>"
				+ "</div>");
		for (const option of parentProperty.subPropertiesListOptions) {
			cardElement.find(".addPropertySelect").append("<option>"+option+"</option>");
		}
		cardElement.find(".addPropertySelect").append("<option value='???'>Choix libre</option>");
		cardElement.find(".addPropertySelect").focus();
		cardElement.find(".addPropertyText").hide();
		cardElement.find(".addPropertySelect").change(() => {
			if (cardElement.find(".addPropertySelect").val() == "???") {
				cardElement.find(".addPropertyText").show(400);
			} else {
				cardElement.find(".addPropertyText").hide(400);
			}});
	} else if (parentProperty.subPropertiesListOptions != null && parentProperty.subPropertiesListOptions.length > 0) {
		cardElement.find("form").prepend("<select name='addPropertySelect' class='addPropertySelect' required><option value='' hidden>"+messages.chooseOption+"</option></select>");
		for (const option of parentProperty.subPropertiesListOptions) {
			cardElement.find(".addPropertySelect").append("<option>"+option+"</option>");
		}
		cardElement.find(".addPropertySelect").focus();
		cardElement.find(".addPropertySelect").keydown((e) => {
			if (e.key === "Escape") {
				cardElement.remove();
				e.preventDefault();
			}
		});
	} else if (parentProperty.subPropertiesListOpen) {
		cardElement.find("form").prepend("<input type='text' name='addPropertyText' class='addPropertyText' required/>");
		cardElement.find(".addPropertyText").focus();
		cardElement.find(".addPropertyText").keydown((e) => {
			if (e.key === "Escape") {
				cardElement.remove();
				e.preventDefault();
			}
		});
	}
	
	cardElement.find(".cancel").click(() => {cardElement.remove()});
	cardElement.find(".validate").click(addProperty.bind(this, cardElement, parentProperty));
}

function setPropertyValue(propertyLineElement, event) {
	const value = propertyLineElement.find(".flyingCardBody input").val();
	const propertyName = propertyLineElement.parent().attr("propertyName");
	propertyLineElement.find(".flyingCardBody").remove();
	propertyLineElement.find(".propertyValue").empty();
	propertyLineElement.find(".propertyValue").append("<div class='propertyValueSpinner spinner-border' role='status'></div>");
	
	$.ajax("/gencross-online/dispatcher/rest/character/"+characterId+"/setValue", {
		method: "PUT",
		data: { 'property': propertyName, 'value': value }
	}).done(refreshCharacter);	
}

function addProperty(addPropertyCard, parentProperty, event) {
	const parentPropertyName = parentProperty.absoluteName;
	let newPropertyName = null;
	if (addPropertyCard.find(".addPropertySelect").length > 0) {
		newPropertyName = addPropertyCard.find(".addPropertySelect").val();
		if (newPropertyName == '???') {
			newPropertyName = addPropertyCard.find(".addPropertyText").val();
		}
	} else if (addPropertyCard.find(".addPropertyText").length > 0) {
		newPropertyName = addPropertyCard.find(".addPropertyText").val();
	}
	
	if (newPropertyName != null) {
		$.ajax("/gencross-online/dispatcher/rest/character/"+characterId+"/addProperty", {
			method: "POST",
			data: { 'parentProperty': parentPropertyName, 'name': newPropertyName }
		}).done(refreshCharacter);
	}
}

function refreshCharacter(character) {
	character.properties.forEach(displayRootProperty);
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