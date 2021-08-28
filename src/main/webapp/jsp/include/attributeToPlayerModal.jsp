<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal" tabindex="-1" id="attributeToPlayerModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<form name="attributeToPlayerForm" method="POST">
				<div class="modal-header">
					<h5 class="modal-title"><fmt:message key="label.attributeToPlayer"/></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
	  				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" name="characterId" value="${characterId}"/>
					<div class="mb-3">
						<input class="form-check-input" type="radio" name="searchOrInvite" id="searchPlayer" value="searchPlayer" checked onchange="swapSearchOrInvite()"/>
						<label class="form-check-label" for="searchPlayer">
							<fmt:message key="label.searchExistingPlayer"/>
						</label>
						<input class="form-check-input" type="radio" name="searchOrInvite" id="invitePlayer" value="invitePlayer" onchange="swapSearchOrInvite()">
						<label class="form-check-label" for="invitePlayer">
							<fmt:message key="label.invitePlayer"/>
						</label>
					</div>
					<div class="mb-3">
						<span id="searchedPlayerContainer">
							<label for="searchedPlayer"><fmt:message key="label.username"/></label>
							<input type="text" name="searchedPlayer" id="searchedPlayer"/>
						</span>
						<span id="invitePlayerContainer">
							<label for="invitePlayer"><fmt:message key="label.email"/></label>
							<input type="email" name="invitePlayer" id="invitePlayer"/>
						</span>
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="label.cancel"/></button>
					<button type="submit" class="btn btn-primary"><fmt:message key="label.validate"/></button>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
"use strict";

function swapSearchOrInvite() {
	const searchOrInvite = document.forms.attributeToPlayerForm.searchOrInvite.value;
	console.log(searchOrInvite);
	if (searchOrInvite == "searchPlayer") {
		console.log("== searchOrInvite");
		$("#searchedPlayerContainer").show();
		$("#invitePlayerContainer").hide();
	} else {
		console.log("!= searchOrInvite");
		$("#searchedPlayerContainer").hide();
		$("#invitePlayerContainer").show();
	}
	
}

</script>

