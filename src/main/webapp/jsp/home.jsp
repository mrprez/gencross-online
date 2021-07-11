<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
		<script type="text/javascript">
"use strict";

window.addEventListener('load', (event) => {
	const results = new RegExp('[\?&]tableId=([^&#]*)').exec(window.location.search);
	if (results !== null) {
		const tableId = results[1];
		$('#flush-collapseGmTable'+tableId).collapse('show');
	}
});
		
		
function openCreateTableModal() {
	$('#newTableButton').width($('#newTableButton').width());
	$('#newTableButton').height($('#newTableButton').height());
	$('#newTableButton .buttonLabel').fadeOut();
	$('#newTableButton .buttonSpinner').fadeIn();
	$.ajax("/gencross-online/dispatcher/home/include/createTable")
		.done(function(data) {
			$('body').append(data);
			$('#createTableModal').modal('show');
			$('#newTableButton .buttonLabel').fadeIn(0);
			$('#newTableButton .buttonSpinner').fadeOut(0);
			$('#newTableButton').width('');
			$('#newTableButton').height('');
		});
}

function openCreateCharacterModal(tableId) {
	$('#newCharacterButton_'+tableId).width($('#newCharacterButton_'+tableId).width());
	$('#newCharacterButton_'+tableId).height($('#newCharacterButton_'+tableId).height());
	$('#newCharacterButton_'+tableId+' .buttonLabel').fadeOut();
	$('#newCharacterButton_'+tableId+' .buttonSpinner').fadeIn();
	$.ajax("/gencross-online/dispatcher/table/"+tableId+"/include/createCharacter")
		.done(function(data) {
			$('body').append(data);
			$('#createCharacterModal').modal('show');
			$('#newCharacterButton_'+tableId+' .buttonLabel').fadeIn(0);
			$('#newCharacterButton_'+tableId+' .buttonSpinner').fadeOut(0);
			$('#newCharacterButton_'+tableId).width('');
			$('#newCharacterButton_'+tableId).height('');
		});
}

function clickOnCharacter(characterId) {
	window.location = "/gencross-online/dispatcher/character/"+characterId;
}
		</script>
	</head>
	<body class="home">
		<%@ include file="include/top.jsp" %>
		<div class="container">
			<div class="row align-items-center">
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title"><fmt:message key="label.gmTableListTitle"/></h5>
							<c:if test="${empty userGmTables}">
								<fmt:message key="label.noTable"/>
							</c:if>
							<div class="accordion">
								<c:forEach items="${userGmTables}" var="userGmTable">
									<div class="accordion-item">
										<h2 class="accordion-header" id="flush-headingGmTable${userGmTable.table.id}">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseGmTable${userGmTable.table.id}" aria-controls="flush-collapseGmTable${userGmTable.table.id}">
												<span class="tableName">${userGmTable.table.name}</span>
												<span class="tableGame">${userGmTable.table.game}</span>
											</button>
										</h2>
										<div id="flush-collapseGmTable${userGmTable.table.id}" class="accordion-collapse collapse" aria-labelledby="flush-headingGmTable${userGmTable.table.id}">
											<div class="accordion-body">
												<ul class="list-group mb-3">
													<c:forEach items="${userGmTable.characters}" var="character">
														<li class="list-group-item character" onclick="clickOnCharacter(${character.id})">
															${character.name}
														</li>
													</c:forEach>
												</ul>
												<button type="button" class="btn btn-primary buttonWithSpinner" id="newCharacterButton_${userGmTable.table.id}" onclick="openCreateCharacterModal(${userGmTable.table.id})">
													<span class="buttonLabel"><fmt:message key="label.createCharacter"/></span>
													<div class="buttonSpinner spinner-border" role="status">
														<span class="visually-hidden"><fmt:message key="label.loading"/></span>
													</div>
												</button>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="card-footer">
							<button type="button" class="btn btn-primary buttonWithSpinner" onclick="openCreateTableModal()">
								<span class="buttonLabel"><fmt:message key="label.newTable"/></span>
								<div class="buttonSpinner spinner-border" role="status">
									<span class="visually-hidden"><fmt:message key="label.loading"/></span>
								</div>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>