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
messages.freeChoice='<fmt:message key="label.freeChoice"/>';
messages.confirm='<fmt:message key="label.confirm"/>';
messages.cancel='<fmt:message key="label.cancel"/>';
messages.confirmTitle='<fmt:message key="label.confirmTitle"/>';
messages.confirmPropertyDeletion='<fmt:message key="label.confirmPropertyDeletion"/>';
messages.phase='<fmt:message key="label.phase"/>';
messages.confirmPassToNextPhase='<fmt:message key="label.confirmPassToNextPhase"/>';
messages.actionMessageTitle='<fmt:message key="label.actionMessageTitle"/>';
messages.ok='<fmt:message key="label.ok"/>';
messages.historyAdd='<fmt:message key="label.historyAdd"/>';
messages.historyDelete='<fmt:message key="label.historyDelete"/>';
messages.cost='<fmt:message key="label.cost"/>';


window.addEventListener('load', (event) => {
	$.get(
		"<%=request.getContextPath()%>/dispatcher/rest/character/${character.id}",
		( character ) => {
			$(".initialSpinner").remove();
			refreshCharacter(character);
		}
	);
	new bootstrap.Tooltip(document.querySelectorAll("#playerName")[0]);
});

function displayPointPool(pointPool) {
	$("#pointPoolsContainer").append(
		"<div class='text-nowrap pointPool'>" 
			+ "<span>" + pointPool.name + "&#8239;:&nbsp;</span>"
			+ "<span>" + pointPool.remaining + "/" + pointPool.total + "</span>" 
		+ "</div>"
	);
}

function displayError(error) {
	$("#errorContainer").append(
		"<li>"
			+ "<i class='bi bi-exclamation-circle-fill'></i>"
			+ error 
		+ "</li>"
	);
}


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
		liElement.children("ul.subProperties").remove();
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
		
		const subPropertiesAbsoluteName = property.subProperties.map((property) => property.absoluteName)
		
		const subPropertiesUlElement = liElement.children(".subProperties");
		subPropertiesUlElement.children("li.propertyNode").each((index, subPropertyNodeElement) => {
			if (!subPropertiesAbsoluteName.includes($(subPropertyNodeElement).attr("propertyName"))) {
				$(subPropertyNodeElement).remove();
			}
		});
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
			propertyLineElement.append("<i class='bi bi-pencil actionIcon editIcon'></i>");
			propertyLineElement.find(".editIcon").click(clickOnEditValue.bind(this, propertyLineElement, property));
		} else if (!property.editable) {
			propertyLineElement.find(".editIcon").remove();
		}
		
	} else {
		propertyLineElement.find(".separator").remove();
		propertyLineElement.find(".propertyValue").remove();
		propertyLineElement.find(".editIcon").remove();
	}
	
	
	if (property.removable && propertyLineElement.find(".deleteIcon").length == 0) {
		propertyLineElement.append("<i class='bi bi-trash actionIcon deleteIcon'></i>");
		propertyLineElement.find(".deleteIcon").click(clickOnDeleteProperty.bind(this, propertyLineElement, property));
	} else if (!property.removable) {
		propertyLineElement.find(".deleteIcon").remove();
	}
}
	 
function clickOnEditValue(propertyLineElement, property, event) {
	propertyLineElement.find(".propertyValue").append(
		"<dialog class='card editPropertyCard flyingCard' open>"
			+ "<form class='card-body flyingCardBody' method='dialog'>"
				+ "<input type='image' src='<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/x.svg' class='flyingCardButton cancel'/>"
				+ "<input type='image' src='<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/check.svg' class='flyingCardButton validate'/>"
			+ "</form>"
		+ "</dialog>");
	const cardElement = propertyLineElement.find(".propertyValue .editPropertyCard");
	
	if (property.options) {
		cardElement.find("form").prepend("<select name='value' class='editPropertyField' required></select>");
		for (const option of property.options) {
			cardElement.find(".editPropertyField").append("<option>"+option+"</option>");
		}
		if (property.value) {
			cardElement.find(".editPropertyField").val(property.value);
		} else {
			cardElement.find(".editPropertyField").prepend("<option value='' hidden>"+messages.chooseOption+"</option>");
		}
	} else if (property.valueType === "StringValue") {
		cardElement.find("form").prepend("<input type='text' name='value' class='editPropertyField' value='"+propertyLineElement.find(".propertyValue").text()+"'/>");
	} else if (property.valueType === "IntValue" || property.valueType === "DoubleValue") {
		cardElement.find("form").prepend("<input type='number' class='editPropertyField' name='value' value='"+propertyLineElement.find(".propertyValue").text()+"' required/>");
		if (property.valueOffset) {
			propertyLineElement.find(".editPropertyCard input[type='number']").attr("step", property.valueOffset);
		}
		if (property.minValue) {
			propertyLineElement.find(".editPropertyCard input[type='number']").attr("min", property.minValue);
		}
		if (property.maxValue) {
			propertyLineElement.find(".editPropertyCard input[type='number']").attr("max", property.maxValue);
		}
	}
	
	propertyLineElement.find(".editPropertyCard .editPropertyField").focus();
	propertyLineElement.find(".editPropertyCard .editPropertyField").keydown((e) => {
		if (e.key === "Escape") {
			cardElement.remove();
			e.preventDefault();
		}
	});
	
	propertyLineElement.find(".editPropertyCard .cancel").click(() => {cardElement.remove()});
	propertyLineElement.find(".editPropertyCard form").submit(setPropertyValue.bind(this, propertyLineElement));
}

function clickOnAddProperty(addActionElement, parentProperty, event) {
	$(addActionElement).append(
		"<dialog class='card addPropertyCard flyingCard' open>"
			+ "<form class='card-body flyingCardBody' method='dialog'>"
				+ "<div class='addPropertyInputGroup'></div>"
				+ "<input type='image' src='<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/x.svg' class='flyingCardButton cancel'/>"
				+ "<input type='image' src='<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/check.svg' class='flyingCardButton validate'/>"
			+ "</form>"
		+ "</dialog>");
	const cardElement = $(addActionElement).find(".addPropertyCard");
	if (parentProperty.subPropertiesListOptions != null && parentProperty.subPropertiesListOptions.length > 0 && parentProperty.subPropertiesListOpen) {
 		cardElement.find(".addPropertyInputGroup").append(
 				"<select name='addPropertySelect' class='addPropertySelect' required><option value='' hidden>"+messages.chooseOption+"</option></select>"
 				+ "<input type='text' name='addPropertyText' class='addPropertyText freeChoiceText'/>");
		for (const option of parentProperty.subPropertiesListOptions) {
			cardElement.find(".addPropertySelect").append("<option>"+option+"</option>");
			cardElement.find(".addPropertySelect option").last().attr("value", option);
		}
		cardElement.find(".addPropertySelect").append("<option value='???'>"+messages.freeChoice+"</option>");
		cardElement.find(".addPropertySelect").focus();
		cardElement.find(".addPropertyText").hide();
		cardElement.find(".addPropertySelect").change(() => {
			if (cardElement.find(".addPropertySelect").val() == "???") {
				cardElement.find(".addPropertyText").slideDown();
				cardElement.find(".addPropertyText").prop("required", true);
			} else {
				cardElement.find(".addPropertyText").slideUp();
				cardElement.find(".addPropertyText").prop("required", false);
			}
		});
	} else if (parentProperty.subPropertiesListOptions != null && parentProperty.subPropertiesListOptions.length > 0) {
		cardElement.find(".addPropertyInputGroup").append("<select name='addPropertySelect' class='addPropertySelect' required><option value='' hidden>"+messages.chooseOption+"</option></select>");
		for (const option of parentProperty.subPropertiesListOptions) {
			cardElement.find(".addPropertySelect").append("<option>"+option+"</option>");
			cardElement.find(".addPropertySelect option").last().attr("value", option);
		}
		cardElement.find(".addPropertySelect").focus();
		cardElement.find(".addPropertySelect").keydown((e) => {
			if (e.key === "Escape") {
				cardElement.remove();
				e.preventDefault();
			}
		});
	} else if (parentProperty.subPropertiesListOpen) {
		cardElement.find(".addPropertyInputGroup").append("<input type='text' name='addPropertyText' class='addPropertyText' required/>");
		cardElement.find(".addPropertyText").focus();
		cardElement.find(".addPropertyText").keydown((e) => {
			if (e.key === "Escape") {
				cardElement.remove();
				e.preventDefault();
			}
		});
	}
	
	if (parentProperty.subPropertiesListOptions != null && parentProperty.subPropertiesListOptions.length > 0) {
		cardElement.find(".addPropertyInputGroup").append(
			"<div class='specificationGroup'>"
				+ "<label for='specificationText'>Précisez&#8239;:&nbsp;</label>"
				+ "<input type='text' name='specificationText' class='specificationText' required/>"
			+ "</div>");
		cardElement.find(".specificationGroup").hide();
		cardElement.find(".addPropertySelect").change(() => {
			if (cardElement.find(".addPropertySelect").val().endsWith(" - ")) {
				cardElement.find(".specificationGroup").slideDown();
				cardElement.find(".specificationText").prop("required", true);
			} else {
				cardElement.find(".specificationGroup").slideUp();
				cardElement.find(".specificationText").prop("required", false);
			}
		});
	}
	
 	cardElement.find(".cancel").click(() => {cardElement.remove()});
 	cardElement.find("form").submit(addProperty.bind(this, cardElement, parentProperty));
}

function clickOnDeleteProperty(propertyLineElement, property, event) {
	$("body").append(
		"<div class='modal fade' id='deletePropertyModal' tabindex='-1' role='dialog' aria-labelledby='deletePropertyLabel' aria-hidden='true'>"
  			+ "<div class='modal-dialog' role='document'>"
				+ "<div class='modal-content'>"
	  				+ "<div class='modal-header'>"
						+ "<h5 class='modal-title' >"+messages.confirmTitle+"</h5>"
					+ "</div>"
	  				+ "<div class='modal-body'>"
	  					+ messages.confirmPropertyDeletion.replace("{0}", property.name)
	  				+ "</div>"
					+ "<div class='modal-footer'>"
						+ "<button type='button' class='btn btn-primary confirmButton'>"+messages.confirm+"</button>"
						+ "<button type='button' class='btn btn-secondary cancelButton' data-dismiss='modal'>"+messages.cancel+"</button>"
					+ "</div>"
	  			+ "</div>"
			+ "</div>"
		+ "</div>");
	const deletePropertyModal = new bootstrap.Modal(document.getElementById('deletePropertyModal'));
	document.getElementById('deletePropertyModal').addEventListener('show.bs.modal', () => {
		$("#deletePropertyModal .confirmButton").click((event) => {
			deleteProperty(propertyLineElement, event);
			deletePropertyModal.hide();
		});
	});
	document.getElementById('deletePropertyModal').addEventListener('show.bs.modal', () => {
		$("#deletePropertyModal .cancelButton").click(() => { deletePropertyModal.hide(); });
	});
	document.getElementById('deletePropertyModal').addEventListener('hidden.bs.modal', () => {
		$("#deletePropertyModal").remove();
	});
	deletePropertyModal.show();
}

function clickOnPassToNextPhase() {
	$("body").append(
		"<div class='modal fade' id='passToNextPhaseModal' tabindex='-1' role='dialog' aria-labelledby='passToNextPhaseLabel' aria-hidden='true'>"
  			+ "<div class='modal-dialog' role='document'>"
				+ "<div class='modal-content'>"
	  				+ "<div class='modal-header'>"
						+ "<h5 class='modal-title' >"+messages.confirmTitle+"</h5>"
					+ "</div>"
	  				+ "<div class='modal-body'>"
	  					+ messages.confirmPassToNextPhase
	  				+ "</div>"
					+ "<div class='modal-footer'>"
						+ "<button type='button' class='btn btn-primary confirmButton'>"+messages.confirm+"</button>"
						+ "<button type='button' class='btn btn-secondary cancelButton' data-dismiss='modal'>"+messages.cancel+"</button>"
					+ "</div>"
	  			+ "</div>"
			+ "</div>"
		+ "</div>");
	const passToNextPhaseModal = new bootstrap.Modal(document.getElementById('passToNextPhaseModal'));
	document.getElementById('passToNextPhaseModal').addEventListener('show.bs.modal', () => {
		$("#passToNextPhaseModal .confirmButton").click((event) => {
			passToNextPhase(event);
			passToNextPhaseModal.hide();
		});
	});
	document.getElementById('passToNextPhaseModal').addEventListener('show.bs.modal', () => {
		$("#passToNextPhaseModal .cancelButton").click(() => { passToNextPhaseModal.hide(); });
	});
	document.getElementById('passToNextPhaseModal').addEventListener('hidden.bs.modal', () => {
		$("#passToNextPhaseModal").remove();
	});
	passToNextPhaseModal.show();
}

function passToNextPhase() {
	$("#nextPhaseButton").hide();
	$("#nextPhaseButton").after("<div class='spinner-border nextPhaseButtonSpinner' role='status'></div>")
	$.ajax("<%=request.getContextPath()%>/dispatcher/rest/character/"+characterId+"/passToNextPhase", {
		method: "POST"
	}).done(refreshCharacter);
}

function setPropertyValue(propertyLineElement, event) {
	const value = propertyLineElement.find(".flyingCardBody [name='value']").val();
	const propertyName = propertyLineElement.parent().attr("propertyName");
	propertyLineElement.find(".flyingCardBody").remove();
	propertyLineElement.find(".propertyValue").empty();
	propertyLineElement.find(".propertyValue").append("<div class='propertyValueSpinner spinner-border' role='status'></div>");
	
	$.ajax("<%=request.getContextPath()%>/dispatcher/rest/character/"+characterId+"/setValue", {
		method: "PUT",
		data: { 'property': propertyName, 'value': value }
	}).done(refreshCharacter);
}

function addProperty(addPropertyCard, parentProperty, event) {
	const data = new Object();
	data.parentProperty = parentProperty.absoluteName;
	
	if (addPropertyCard.find(".addPropertySelect").length > 0) {
		data.name = addPropertyCard.find(".addPropertySelect").val();
		if (data.name == '???') {
			data.name = addPropertyCard.find(".addPropertyText").val();
		} else if (data.name.endsWith(" - ")) {
			data.specification = addPropertyCard.find(".specificationText").val();
		}
	} else if (addPropertyCard.find(".addPropertyText").length > 0) {
		data.name = addPropertyCard.find(".addPropertyText").val();
	}
	
	$.ajax("<%=request.getContextPath()%>/dispatcher/rest/character/"+characterId+"/addProperty", {
		method: "POST",
		data: data
	}).done(refreshCharacter);
}

function deleteProperty(propertyLineElement, event) {
	const propertyName = propertyLineElement.parent().attr("propertyName");
	const nodeElement = propertyLineElement.parent();
	nodeElement.empty();
	nodeElement.append("<div class='nodeSpinner spinner-border' role='status'></div>");
	
	$.ajax("<%=request.getContextPath()%>/dispatcher/rest/character/"+characterId+"/deleteProperty", {
		method: "DELETE",
		data: { 'property': propertyName }
	}).done(refreshCharacter);	
}

function displayActionMessage(actionMessage) {
	$("body").append(
		"<div class='modal fade' id='actionMessageModal' tabindex='-1' role='dialog' aria-labelledby='actionMessageLabel' aria-hidden='true'>"
  			+ "<div class='modal-dialog' role='document'>"
				+ "<div class='modal-content'>"
	  				+ "<div class='modal-header'>"
						+ "<h5 class='modal-title' >"+messages.actionMessageTitle+"</h5>"
					+ "</div>"
	  				+ "<div class='modal-body'>"
	  					+ actionMessage
	  				+ "</div>"
					+ "<div class='modal-footer'>"
						+ "<button type='button' class='btn btn-primary cancelButton' data-dismiss='modal'>"+messages.ok+"</button>"
					+ "</div>"
	  			+ "</div>"
			+ "</div>"
		+ "</div>");
	const actionMessageModal = new bootstrap.Modal(document.getElementById('actionMessageModal'));
	document.getElementById('actionMessageModal').addEventListener('show.bs.modal', () => {
		$("#actionMessageModal .cancelButton").click(() => { actionMessageModal.hide(); });
	});
	document.getElementById('actionMessageModal').addEventListener('hidden.bs.modal', () => {
		$("#actionMessageModal").remove();
	});
	actionMessageModal.show();
}

function displayHistory(history) {
	history.forEach((historyItem, index) => {
		if (index >= $("#history tbody tr").length) {
			$("#history tbody").prepend(
				"<tr data-bs-toggle='tooltip' data-bs-placement='left'>"
	      			+ "<th class='historyTitle' scope='row'></th>"
	      			+ "<td class='historyDescription'></td>"
	      			+ "<td class='historyCost'></td>"
	      		+ "</tr>");
		}
		
		const trSelector = $("#history tbody tr");
		const trElement = trSelector.get(trSelector.length - index - 1);
		$(trElement).attr("title", new Date(historyItem.date).toLocaleString());
		new bootstrap.Tooltip(trElement);
		
		let rowTitle = historyItem.absoluteName;
		if (historyItem.action == 1) {
			rowTitle = historyItem.absoluteName.substring(0, historyItem.absoluteName.lastIndexOf("#"));
			const propertyName = historyItem.absoluteName.substring(historyItem.absoluteName.lastIndexOf("#") + 1);
			$(trElement).children(".historyDescription").html("<i "
				+ "class='bi bi-node-plus me-2' "
				+ "title='"+messages.historyAdd+"' "
				+ "data-bs-toggle='tooltip'></i>");
			new bootstrap.Tooltip(document.querySelectorAll("#history tbody .historyDescription i.bi-node-plus")[0]);
			$(trElement).children(".historyDescription").append(propertyName);
		} else if (historyItem.action == 2) {
			$(trElement).children(".historyDescription").html(historyItem.oldValue + "<i class='bi bi-arrow-right-short'></i>" + historyItem.newValue);
		} else if (historyItem.action == 3) {
			rowTitle = historyItem.absoluteName.substring(0, historyItem.absoluteName.lastIndexOf("#"));
			const propertyName = historyItem.absoluteName.substring(historyItem.absoluteName.lastIndexOf("#") + 1);
			$(trElement).children(".historyDescription").html("<i "
				+ "class='bi bi-node-minus me-2' "
				+ "title='"+messages.historyDelete+"' "
				+ "data-bs-toggle='tooltip'></i>");
			new bootstrap.Tooltip(document.querySelectorAll("#history tbody .historyDescription i.bi-node-minus")[0]);
			$(trElement).children(".historyDescription").append(propertyName);
		}
		
		if (historyItem.pointPool) {
			$(trElement).children(".historyCost").html("<span title='"+messages.cost+"'>" + historyItem.cost+" "+historyItem.pointPool + "</span>");
		}
		$(trElement).children(".historyTitle").html(rowTitle.replaceAll("#", "<i class='bi bi-chevron-right'></i>"));
	});
}

function refreshCharacter(character) {
	$("#phaseName").html(messages.phase + "&#8239;:&nbsp;" + character.phase);
	$(".nextPhaseButtonSpinner").remove();
	if (character.nextPhase) {
		$("#nextPhaseButton").show();
	} else {
		$("#nextPhaseButton").hide();
	}
	$("#nextPhaseButton").prop("disabled", !character.nextPhaseAvailable);
	$("#pointPoolsContainer").empty();
	character.pointPools.forEach(displayPointPool);
	$("#errorContainer").empty();
	character.errors.forEach(displayError);
	character.properties.forEach(displayRootProperty);
	if (character.actionMessage) {
		displayActionMessage(character.actionMessage);
	}
	displayHistory(character.history);
}

		</script>
	</head>
	<body class="character">
		<%@ include file="include/top.jsp" %>
		<div class="container">
			<div class="row">
				<div class="col">
					<nav aria-label="breadcrumb" style="--bs-breadcrumb-divider: '>';">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/dispatcher/home"><fmt:message key="label.home"/></a></li>
							<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/dispatcher/table/${table.id}">${table.name}</a></li>
							<li class="breadcrumb-item active" aria-current="page">${character.name}</li>
						</ol>
					</nav>
				</div>
				<div class="col text-end">
					<span id="playerName" data-bs-toggle="tooltip" data-bs-placement="left" data-bs-original-title="<fmt:message key="label.playerTooltip"/>">
						${player.username}
					</span>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col border">
					<div class="characterHeader ps-3 pb-3 pt-3">
						<div class="phaseContainer">
							<h6 id="phaseName" class="pe-3"></h6>
							<button type="button" id="nextPhaseButton" class="btn btn-primary" onclick="clickOnPassToNextPhase()" disabled>
								<fmt:message key="label.nextPhase"/>
								<i class="bi bi-caret-right-fill"></i>
							</button>
						</div>
						<div class="historyButtonContainer">
							<button type="button" class="btn btn-outline-primary align-self-end collapsed displayHistoryButton" data-bs-toggle="collapse" data-bs-target="#historyContainer">
								<fmt:message key="label.displayHistory"/>
							</button>
						</div>
					</div>
					<div class="row border-top border-bottom p-3" id="pointPoolsContainer">
						<div class="spinner-border initialSpinner" role="status"></div>
					</div>
					<div class="row border-bottom">
						<ul id="errorContainer" class="text-warning bg-dark"></ul>
					</div>
					<div class="row">
						<span class="spinner-border initialSpinner m-3" role="status"></span>
						<ul class="p-3 m-0" id="characterRoot">
						</ul>
					</div>
				</div>
				<div class="col border ms-3 collapse collapse-horizontal" id="historyContainer">
					<div class="row">
						<div class="col">
							<h6 class="ps-3 pb-3 pt-3">
								<fmt:message key="label.history"/>
							</h6>
						</div>
						<div class="col pe-3 pt-3">
							<button type="button" class="btn-close" data-bs-toggle="collapse" data-bs-target="#historyContainer" title="<fmt:message key="label.hideHistory"/>"></button>
						</div>
					</div>
					<table id="history" class="table">
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
		
	</body>
</html>