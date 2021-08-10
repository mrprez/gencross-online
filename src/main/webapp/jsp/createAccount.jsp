<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
		<style type="text/css">
html {
	height: 100%;
}
body {
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
}
		</style>
		<script>
function checkConfirm() {
	const passwordInput = document.querySelector('#createAccountForm').elements['passwordInput'];
	const confirmInput = document.querySelector('#createAccountForm').elements['confirmInput'];
	if (passwordInput.value != confirmInput.value) {
		confirmInput.setCustomValidity("<fmt:message key="label.confirmPasswordDoesNotMatch"/>");
	} else {
		confirmInput.setCustomValidity("");
	}
}
		</script>
	</head>
	<body>
		<div class="container">
			<div class="row align-items-center justify-content-center">
  				<div class="col-4">
  					<div class="card">
  						<div class="card-body">
	  						<h5><fmt:message key="label.newAccount"/></h5>
	  						<form method="POST" action="/gencross-online/dispatcher/account" id="createAccountForm">
	  							<div class="mb-3">
	  								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	  								<label for="usernameInput"><fmt:message key="label.username"/></label>
	  								<c:choose>
		  								<c:when test="${exception != null && exception.getClass().simpleName == 'UsernameAlreadyExistException'}">
		  									<c:set var="usernameClass" value="form-control is-invalid"/>
		  									<c:set var="usernameFeedback" value="validationServerUsernameFeedback"/>
		  								</c:when>
		  								<c:otherwise>
		  									<c:set var="usernameClass" value="form-control"/>
		  								</c:otherwise>
	  								</c:choose>
	  								<input type="text" name="username" value="${username}" class="${usernameClass}" id="usernameInput" aria-describedby="${usernameFeedback}" required/>
	  								<div id="validationServerUsernameFeedback" class="invalid-feedback">
										<fmt:message key="label.usernameAlreadyExist"/>
									</div>
	  							</div>
	  							<div class="mb-3">
	  								<label for="emailInput"><fmt:message key="label.email"/></label>
	  								<c:choose>
		  								<c:when test="${exception != null && exception.getClass().simpleName == 'EmailAlreadyExistException'}">
		  									<c:set var="emailClass" value="form-control is-invalid"/>
		  									<c:set var="emailFeedback" value="validationServerEmailFeedback"/>
		  								</c:when>
		  								<c:otherwise>
		  									<c:set var="emailClass" value="form-control"/>
		  								</c:otherwise>
	  								</c:choose>
	  								<input type="email" name="email" value="${email}" class="${emailClass}" id="emailInput" aria-describedby="${emailFeedback}" required/>
	  								<div id="emailServerUsernameFeedback" class="invalid-feedback">
										<fmt:message key="label.emailAlreadyExist"/>
									</div>
	  							</div>
	  							<div class="mb-3">
	  								<label for="passwordInput"><fmt:message key="label.password"/></label>
	  								<input type="password" name="password" value="${password}" class="form-control" id="passwordInput" onchange="checkConfirm()" required/>
	  							</div>
	  							<div class="mb-3">
	  								<label for="confirmInput"><fmt:message key="label.confirmPassword"/></label>
	  								<input type="password" name="confirm" value="${password}" class="form-control" id="confirmInput" onkeyup="checkConfirm()" required/>
	  							</div>
	  							<button type="submit" class="btn btn-primary"><fmt:message key="label.validate"/></button>
	  						</form>
	  						<div class="mt-2">
	  							<a href="/gencross-online/dispatcher/login"><fmt:message key="label.login"/></a>
	  						</div>
	  					</div>
  					</div>
  				</div>
  			</div>
		</div>
	</body>
</html>