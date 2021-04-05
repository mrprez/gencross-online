<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal" tabindex="-1" id="updateAccountModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<form onsubmit="">
				<div class="modal-header">
					<h5 class="modal-title"><fmt:message key="label.updateAccount"/></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
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
function submitUpdateAccount() {
	var accountChange = {};
	// $('#updateAccountModal input[name=usernameInput]')
	
}
</script>
