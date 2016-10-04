<%@ include file="/WEB-INF/inc/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User registration</title>
	<link href="<c:url value="/inc/css/style.css" />" rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/inc/header.jsp" %>
	<form method="post" action="<c:url value='Inscription' />" >
		<fieldset>
			<legend>Inscription</legend>
			<p>
				Mandatory fields
			</p>

			<label for="email">E-mail</label>
			<input type="text" id="email" name="email" size="20" maxlength="60"/><br />
			<span class="error" ><c:out value="${form.errors['email'] }"/></span>
			
			<label for="password">Password</label> 
			<input type="password" id="password" name="password" value="" size="20" maxlength="20" />
			<span class="error"><c:out value="${form.errors['password'] }"/></span>
			<br />
				
			<label for="passwordVerification">Password verification</label> 
			<input type="password" id="passwordVerification" name="passwordVerification" value=""
				size="20" maxlength="20" />
			<span class="error"><c:out value="${form.errors['passwordVerification'] }"/></span>
				 <br />
			
			<label for="firstName">First Name</label>
			<input type="text" id="firstName" name="firstName" size="20" maxlength="60"/><br />
			<span class="error"><c:out value="${form.errors['firstName'] }"/></span>
			
			<p>
				Other informations
			</p>
			
			<div>
				<span>Education</span><br/>
				<input type="text" id="searchSchool" name="searchSchool" size="20"/> <br/>
				
				<span>Work</span><br/>
				<input type="text" id="searchWork" name="searchWork" size="20"/> <br/>
			</div>
			
			<label for="picturePath">Add a profil picture</label>
			<input type="text" id="picturePath" name="picturePath" size="120" maxlength="200"/><br/>
			
			
			<label for="introductionText">Introduction text</label>
			<input type="text" id="introdutionText" name="introdutionText" size="120" maxlength="200"/><br/>
			
			<label for="appointmentPrice">Appointment price</label>
			<input type="text" id="appointmentPrice" name="appointmentPrice" size="20" maxlength="10"/><br/>
			
			<input type="submit" value="go !"> 
		</fieldset>
	</form>
<%@ includefile="/WEB-INF/inc/footer.jsp" %>
</body>
</html>