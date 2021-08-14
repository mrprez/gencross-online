<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.mrprez.gencross.Personnage"%>
<%@page import="com.mrprez.gencross.value.StringValue"%>
<% request.setAttribute("property", ((Personnage) request.getAttribute("characterData")).getProperty((String) request.getParameter("propertyAbsoluteName")));%>

<li class="propertyNode ${property.subProperties != null ? 'collapsed' : 'end' }">
	<span class="nodeIcon"></span>
	<span class="propertyName">${property.fullName}</span>
	<c:if test="${property.value != null}">
		:
		<div class="propertyValue">
			${property.value}
			<c:if test="${property.editable}">
				<div class="editPropertyContainer">
					<img class="editIcon" src="<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/pencil.svg"/>
					<c:if test="${property.value['class'].simpleName == 'StringValue'}">
						<div class="card editPropertyCard editStringPropertyField">
							<div class="card-body">
								<form class="propertyForm">
									<input type="hidden" name="property" value="${property.absoluteName}"/>
									<input type="text" name="value" value="${property.value}"/>
									<img class="editPropertyButton valid" src="<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/check.svg"/>
									<img class="editPropertyButton close" src="<%=request.getContextPath()%>/img/bootstrap-icons-1.4.1/x.svg"/>
								</form>
							</div>
						</div>
					</c:if>
				</div>
			</c:if>
		</div>
	</c:if>
	<c:if test="${property.subProperties != null}">
		<ul>
			<c:forEach items="${property.subProperties}" var="subProperty">
				<c:import url="include/characterProperty.jsp">
					<c:param name="propertyAbsoluteName" value="${subProperty.absoluteName}"/>
				</c:import>
			</c:forEach>
		</ul>
	</c:if>
</li>