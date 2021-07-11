<nav id="topSideBar">
	<div class="container">
		<div class="gencrossLogoContainer">
			<a href="/gencross-online/dispatcher/home">
				<img src="/gencross-online/img/logo.svg" />
			</a>
		</div>
		<div class="accountLogoContainer">
			<div class="dropdown">
				<a class="btn btn-sm dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"> 
					<img src="/gencross-online/img/bootstrap-icons-1.4.1/person.svg" />
				</a>
				<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
					<li><a class="dropdown-item" href="/gencross-online/logout"><fmt:message key="label.logout"/></a></li>
					<li><button class="dropdown-item" type="button" onclick="openUpdateAccountModal()"><fmt:message key="label.updateAccount"/></button></li>
				</ul>
			</div>
		</div>
	</div>
</nav>
<script type="text/javascript">
"use strict";
function openUpdateAccountModal() {
	$.ajax("/gencross-online/dispatcher/account/include/update")
		.done(function(data, textStatus, xhr) {
			$('body').append(data);
			$('#updateAccountModal').modal('show');
		});
}
</script>