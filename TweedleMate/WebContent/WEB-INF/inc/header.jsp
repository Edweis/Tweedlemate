<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header">
	<div id="connectionBlock">
	<c:choose>
		<c:when test="${ empty sessionScope.connectedUser }">
		<form method="post" action='<c:url value="/Login" />' >
		<fieldset>
			<legend>You are not connected, wanna try ?</legend>
			<label for="email">Email</label> 
			<input type="text" id="email" name="email" value='<c:out value="${connEmail }" />' size="20" maxlength="20" /><br/>
			
			<label for="password">Password</label> 
			<input type="password" id="password" name="password" value="" size="20" maxlength="20" /><br/>
			
			<span class="error"><c:out value="${connError }"/></span><br/>
			
			<input type="submit" value="Aller"> <br/>

		</fieldset>
		</form>
		</c:when>
		<c:otherwise>
		<c:set var="hu" value="${sessionScope.connectedUser }"/>
			<p>How are you <a href="<c:url value='/Profile/${hu.id }/${hu.firstName }'/> "><c:out value="${hu.firstName }"/> ?</a></p>
			<p>Wanna <a href='<c:url value="/Logout"/>'>disconnect ?</a></p>
		</c:otherwise>
	</c:choose>
	</div>
	
	<div id="menu">
	NO MENU YET
	</div>
</div>