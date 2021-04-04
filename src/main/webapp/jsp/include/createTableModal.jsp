<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal" tabindex="-1" id="createTableModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="/gencross-online/dispatcher/table" method="POST">
				<div class="modal-header">
					<h5 class="modal-title"><fmt:message key="label.newTable"/></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<label for="nameInput"><fmt:message key="label.name"/></label>
						<input type="text" name="name" class="form-control" id="nameInput" auto-focus required/>
						<label for="gameInput"><fmt:message key="label.game"/></label>
						<select class="form-select" name="game" id="gameInput" required>
							<option></option>
							<c:forEach items="${pluginList}" var="plugin">
								<option>${plugin.name}</option>
							</c:forEach>
						</select>
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
$('#createTableModal').on('shown.bs.modal', function () {
	$('#nameInput').trigger('focus');
})
</script>
