<%@ include file="/WEB-INF/inc/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Profile</title>
	<link href="<c:url value="/inc/css/style.css" />" rel="stylesheet">
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
					<li>Picture Path : <c:out value="${u.picturePath }"/></li>
					<span>
						<input type="text" id="picturePath" disabled>
					</span>
					<li>Introduction Text : <c:out value="${u.introductionText }"/></li>
					<span>
						<input type="text" id="introductionText" disabled>
					</span>
					<li>Appointment Price : <c:out value="${u.appointmentPrice }"/></li>
					<span>
						<input type="text" id="appointmentPrice" disabled>
					</span>
					<li>Education : <c:out value="${u.education.durationMonth}"/><br/>
					
					<form  method="post" action="<c:url value='/UpdateInfo/Education' />">
					<fieldset>
						<legend>Add an education</legend>
						<label for="schoolName">School Name</label> 
						<input type="text" id="schoolName" name="schoolName" value="${form.schoolName }" size="20" maxlength="20" /><br/>
						
						<label for="durationMonth">Duration (in month)</label> 
						<input type="text" id="durationMonth" name="durationMonth" value="${form.durationMonth }" size="20" maxlength="20" /><br/>

						<label for="startYear">Start year</label> 
						<input type="text" id="startYear" name="startYear" value="${form.startYear }" size="20" maxlength="20" /><br/>
						
						<label for="promotion">Promotion</label> 
						<input type="text" id="promotion" name="promotion" value="${form.promotion }" size="20" maxlength="20" /><br/>
						
						<label for="major">Major</label> 
						<input type="text" id="major" name="major" value="${form.major }" size="20" maxlength="20" /><br/>

						<label for="scholarship">Scholarship</label> 
						<input type="text" id="scholarship" name="scholarship" value="${form.scholarship }" size="20" maxlength="20" /><br/>
	
						<input type="checkbox" id="isHomeuniversity" name="isHomeuniversity" value="${form.isHomeuniversity }" size="20" maxlength="20" />		
						<label for="isHomeuniversity">Is it your home school ?</label> <br/>
						
						<input type="checkbox" id="isCurrentEducation" name="isCurrentEducation" value="${form.isCurrentEducation }" size="20" maxlength="20" />		
						<label for="isCurrentEducation">Is it your current school ?</label> <br/>
					
						<input type="submit" value="addEducation">
					</fieldset>
					</form>
					</li>
				</ul>
			</div>
			</c:when>
			<c:otherwise>
				You should be connected to access your information
			</c:otherwise>
	
		</c:choose>
	</div>
<%@ includefile="/WEB-INF/inc/footer.jsp" %>
</body>
</html>