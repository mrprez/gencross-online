<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
	<head>
		<%@ include file="include/head.jsp" %>
	</head>
	<body class="rpgTable">
		<%@ include file="include/top.jsp" %>
		<div class="container">
			<nav aria-label="breadcrumb" style="--bs-breadcrumb-divider: '>';">
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/dispatcher/home"><fmt:message key="label.home"/></a></li>
					<li class="breadcrumb-item">${table.name}</li>
				</ol>
			</nav>
		</div>
	</body>
</html>