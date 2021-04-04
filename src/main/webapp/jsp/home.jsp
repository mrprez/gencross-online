<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>GenCross</title>
		<link href="/gencross-online/css/bootstrap.min.css" rel="stylesheet">
		<link href="/gencross-online/css/gencross.css" rel="stylesheet">
		<script src="/gencross-online/js/jquery-3.6.0.min.js"></script>
		<script src="/gencross-online/js/bootstrap.min.js"></script>
		<script type="text/javascript">
function openCreateTableModal() {
	$('#newTableButton').width($('#newTableButton').width());
	$('#newTableButton').height($('#newTableButton').height());
	$('#newTableButtonLabel').fadeOut();
	$('#newTableButtonSpinner').fadeIn();
	$.ajax("/gencross-online/dispatcher/home/createTable")
		.done(function(data) {
			$('body').append(data);
			$('#createTableModal').modal('show');
			$('#newTableButtonLabel').fadeIn(0);
			$('#newTableButtonSpinner').fadeOut(0);
			$('#newTableButton').width('');
			$('#newTableButton').height('');
		});
}
		
		</script>
	</head>
	<body>
		<div class="container-liquid">
			<div class="row align-items-left">
				<div class="col">
					<img class=".img-fluid" src="/gencross-online/img/logo_GenCross.png" style="width:92px; height: 100px;"/>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row align-items-center">
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title"><fmt:message key="label.gmTableListTitle"/></h5>
							<div class="accordion" id="GmTablesAccordion">
								<c:forEach items="${userGmTables}" var="userGmTable">
									<div class="accordion-item">
										<h2 class="accordion-header" id="flush-headingGmTable${userGmTable.id}">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseGmTable${userGmTable.id}" aria-expanded="false" aria-controls="flush-collapseGmTable${userGmTable.id}">
												${userGmTable.name}
											</button>
										</h2>
										<div id="flush-collapseGmTable${userGmTable.id}" class="accordion-collapse collapse" aria-labelledby="flush-headingGmTable${userGmTable.id}" data-bs-parent="#GmTablesAccordion">
											<div class="accordion-body">Placeholder content for this accordion, which is intended to demonstrate the <code>.accordion-flush</code> class. This is the first item's accordion body.</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="card-footer">
							<button type="button" class="btn btn-primary" id="newTableButton" onclick="openCreateTableModal()">
								<span id="newTableButtonLabel"><fmt:message key="label.newTable"/></span>
								<div id="newTableButtonSpinner" class="spinner-border" role="status">
									<span class="visually-hidden">Loading...</span>
								</div>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>