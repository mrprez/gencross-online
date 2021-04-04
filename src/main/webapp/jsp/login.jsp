<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
	</head>
	<body>
		<div class="container-liquid">
			<div class="row align-items-left">
				<div class="col">
					<img class=".img-fluid" src="/gencross-online/img/logo_GenCross.png"/>
				</div>
			</div>
  			<div class="row align-items-center justify-content-center">
  				<div class="col-4">
  					<div class="card">
  						<div class="card-body">
	  						<h5><fmt:message key="label.connection"/></h5>
	  						<form action="/gencross-online/login" method="POST">
	  							<div class="mb-3">
	  								<label for="usernameInput"><fmt:message key="label.username"/></label>
	  								<input type="text" name="username" class="form-control" id="usernameInput" auto-focus required/>
	  							</div>
	  							<div class="mb-3">
	  								<label for="passwordInput"><fmt:message key="label.password"/></label>
	  								<input type="password" name="password" class="form-control" id="passwordInput" required/>
	  							</div>
	  							<button type="submit" class="btn btn-primary"><fmt:message key="label.connection"/></button>
	  						</form>
	  						<div class="mt-2">
	  							<a href="/gencross-online/dispatcher/createAccount"><fmt:message key="label.createAccount"/></a>
	  						</div>
	  					</div>
  					</div>
  				</div>
  			</div>
		</div>
	</body>
</html>