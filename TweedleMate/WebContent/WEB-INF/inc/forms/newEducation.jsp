<%@ include file="/WEB-INF/inc/taglib.jspf"%>
<script type="text/javascript">
	jQuery(document).ready(function(){
        $("div#newEducation").hide();
        jQuery('input#hideNewEducation').click(function(){
            $("div#newEducation").hide();
            $('input#showNewEducation').show();
            $('form#newEducationForm').trigger("reset");
        });
        jQuery('input#showNewEducation').click(function(){
            $("div#newEducation").show();
            $('input#showNewEducation').hide()
        });
        
    });	
</script>
<form  method="post" action="<c:url value='/UpdateInfo/Education' />" id="newEducationForm">
<fieldset>
	<legend>Add an education</legend>
	<label for="schoolName">School Name</label> 
	<input type="text" id="schoolName" name="schoolName" value="${form.schoolName }" size="20" maxlength="20" /><br/>
	
	<label for="country">Where ?</label>
	<select name="country">
		<option value="none">Please select a country</option>
	<c:forEach items="${countries }" var="c">
		<option value="${c.code3}"><c:out value="${c.name }"/></option>
	</c:forEach>
	</select> <br/>
	
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

	<input type="checkbox" id="isHomeUniversity" name="isHomeUniversity" value="${form.isHomeuniversity }" size="20" maxlength="20" />		
	<label for="isHomeUniversity">Is it your home school ?</label> <br/>
	
	<input type="checkbox" id="isCurrentEducation" name="isCurrentEducation" value="${form.isCurrentEducation }" size="20" maxlength="20" />		
	<label for="isCurrentEducation">Is it your current school ?</label> <br/>

	<input type="submit" value="Add education">
	<input type="button" value="Cancel" id="hideNewEducation">
</fieldset>
</form>