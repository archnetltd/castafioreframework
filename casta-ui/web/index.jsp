<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Castafiore</title>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
	</head>
	<body>
		<%
			String applicationName = request.getParameter("applicationid");
			if(applicationName == null){
				applicationName = "apps";
			}
		%>
		<div id="application"></div>
		<script type="text/javascript" src="js/1.js"></script>
		<script type="text/javascript" src="js/2.js"></script>
		<script type="text/javascript" src="js/ext.js"></script>
		<script>
			$(document).ready(function(){
				$('#application').castafiore('<%=applicationName%>');
			});
		</script>
	</body>
</html>