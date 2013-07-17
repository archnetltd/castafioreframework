<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Castafiore</title>
	</head>
	<body>
		<%
			String applicationName = request.getParameter("applicationid");
			if(applicationName == null){
				applicationName = "apps";
			}
		%>
		<div id="<%=applicationName%>"></div>
		
		
		<script type="text/javascript" src="castafiore/resource/classpath/org/castafiore/resource/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="ext.js"></script>
		<script type="text/javascript" src="http://68.68.109.26/upstage/ckeditor/ckeditor/ckeditor.js"></script>
		<script>
			$(document).ready(function(){
				$('#<%=applicationName%>').castafiore();
			});
		</script>
	</body>
</html>