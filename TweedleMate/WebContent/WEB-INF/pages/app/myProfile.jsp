<%@ include file="/WEB-INF/inc/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Profile</title>
	<link href="<c:url value="/inc/css/style.css" />" rel="stylesheet">
	<script type="text/javascript" src="<c:url value="/inc/js/jquery-3.1.1.js"/>"></script>
	
</head>
<body>
<%@ include file="/WEB-INF/inc/header.jsp" %>
	<div id="content">
		<c:set var="u" value="${sessionScope.connectedUser }"/>
		<c:choose>
			<c:when test="${ not empty u.id}">
			<div id="mandatoryInfos">
				<ul>
					<li>Id : <c:out value="${u.id }"/></li>
					<li>E-mail : <c:out value="${u.email }"/></li>
					<li>Password : <c:out value="${u.password }"/></li>
					<li>FirstName : <c:out value="${u.firstName }"/></li>
					<li>RegistrationDate : <c:out value="${u.dateRegistration }"/></li>
				</ul>
			</div>
			<div id="optionalInfos">
			
				<ul>
					<li>Picture Path : <c:out value="${u.picturePath }"/><br/>
						<form action="<c:url value='/UpdateInfo/Picture' />" method="post" enctype="multipart/form-data">
							<input type="file"  name="picturePath" accept="image/*">
							<input type="submit" value="Add picture">
						</form>
					</li>
					<li>Introduction Text : <c:out value="${u.introductionText }"/></li>
					<span>
						<input type="text" id="introductionText" disabled>
					</span>
					<li>Appointment Price : <c:out value="${u.appointmentPrice }"/><br/>
					<input type="text" id="appointmentPrice" disabled>
					</li>
					<li>Education :<br/>
					<input type="submit" id="showNewEducation" value="Add a new education"/>
					<div id="displayEducation">
						<c:choose>
							<c:when test="${not empty u.myEducation }">
								<c:forEach items="${u.myEducation}" var="e">
								<div style="width:200px; float:left">
									<p>
										You went to <b>${e.school.name }</b> for <b>${e.durationMonth }</b> month !<br/>
										Oh wow you have studied in <b>${e.school.country.name }</b>> :o nice !<br/>
										Home ? ${e.isHomeUniversity} <br/>
										Current ? ${e.isCurrentEducation }
									</p>
								</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								You don't have put any education for now. If you want to do mentoring you should add one.
							</c:otherwise>
						
						</c:choose>
					</div>
					<div id="newEducation">
					TODO : LAISSER OUVERT APRES UNE UNE ERREUR DANS LE TRAITEMENT DU FORMULAIRE 
						<%@ include file="/WEB-INF/inc/forms/newEducation.jsp" %>
					</div>
					</li>
				</ul>
			</div>
			</c:when>
			<c:otherwise>
				You should be connected to access your information
			</c:otherwise>
	
		</c:choose>
	</div>
<%@ include file="/WEB-INF/inc/footer.jsp" %>
</body>
</html>