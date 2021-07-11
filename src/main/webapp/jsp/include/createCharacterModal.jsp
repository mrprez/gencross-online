<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal" tabindex="-1" id="createCharacterModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="/gencross-online/dispatcher/character" method="POST">
				<div class="modal-header">
					<h5 class="modal-title"><fmt:message key="label.newCharacter"/></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<input type="hidden" name="tableId" value="${tableId}"/>
						<label for="nameInput"><fmt:message key="label.name"/></label>
						<input type="text" name="name" class="form-control" id="nameInput" auto-focus required/>
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
$('#createCharacterModal').on('shown.bs.modal', function () {
	$('#nameInput').trigger('focus');
})
</script>
