<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	  								<label for="usernameInput"><fmt:message key="label.username"/></label>
	  								<input type="text" name="username" class="form-control" id="usernameInput" required/>
	  							</div>
	  							<div class="mb-3">
	  								<label for="emailInput"><fmt:message key="label.email"/></label>
	  								<input type="email" name="email" class="form-control" id="emailInput" required/>
	  							</div>
	  							<div class="mb-3">
	  								<label for="passwordInput"><fmt:message key="label.password"/></label>
	  								<input type="password" name="password" class="form-control" id="passwordInput" onchange="checkConfirm()" required/>
	  							</div>
	  							<div class="mb-3">
	  								<label for="confirmInput"><fmt:message key="label.confirmPassword"/></label>
	  								<input type="password" name="confirm" class="form-control" id="confirmInput" onkeyup="checkConfirm()" required/>
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