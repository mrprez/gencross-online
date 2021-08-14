<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>GenCross</title>
<link rel="icon" type="image/svg" href="<%=request.getContextPath()%>/img/logo45.svg" />
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap-icons.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/gencross.css" rel="stylesheet">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script>
	"use strict";
	$(document).ajaxError(function( event, request, settings ) {
		if (request.status == 403) {
			window.location.href = '<%=request.getContextPath()%>/dispatcher/login';
		}
	});
	
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	});
</script>
