<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.mrprez.gencross.Personnage"%>
<% request.setAttribute("property", ((Personnage) request.getAttribute("characterData")).getProperty((String) request.getParameter("propertyAbsoluteName")));%>

<li class="propertyNode ${property.subProperties != null ? 'collapsed' : 'end' }">
	<span class="nodeIcon"></span>
	<span class="propertyName">${property.fullName}</span>
	<c:if test="${property.value != null}">
		: ${property.value}
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