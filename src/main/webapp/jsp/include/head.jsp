<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>GenCross</title>
<link rel="icon" type="image/svg" href="/gencross-online/img/logo45.svg" />
<link href="/gencross-online/css/bootstrap.min.css" rel="stylesheet">
<link href="/gencross-online/css/gencross.css" rel="stylesheet">
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
</script>