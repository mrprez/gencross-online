<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>GenCross</title>
<link rel="icon" type="image/svg" href="/gencross-online/img/logo45.svg" />
<link href="/gencross-online/css/bootstrap.min.css" rel="stylesheet">
<link href="/gencross-online/css/bootstrap-icons.css" rel="stylesheet">
<link href="/gencross-online/css/gencross.css" rel="stylesheet">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="/gencross-online/js/jquery-3.6.0.min.js"></script>
<script src="/gencross-online/js/popper.min.js"></script>
<script src="/gencross-online/js/bootstrap.min.js"></script>
<script>
	"use strict";
	$(document).ajaxError(function( event, request, settings ) {
		if (request.status == 403) {
			window.location.href = '/gencross-online/dispatcher/login';
		}
	});
	
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	});
</script>
