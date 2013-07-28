<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Castafiore</title>
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>

	</head>
	<body>
		<%
			String applicationName = request.getParameter("applicationid");
			if(applicationName == null){
				applicationName = "showcase";
			}
		%>
		<div id="showcase"></div>
<script type="text/javascript" src="ckeditor/ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="js1/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="js1/jquery-ui-1.8.21.js"></script>
		<script type="text/javascript" src="js1/jquery.plugin.js"></script>
		<script type="text/javascript" src="js1/ext.js"></script>
		<script type="text/javascript" src="js1/jquery.maskedinput-1.3.js"></script>
		<script type="text/javascript" src="js1/jquery.rightClick.js"></script> 
		<script type="text/javascript" src="js1/jquery.blockUI.js"></script>
		<script>
			$(document).ready(function(){
				$('#showcase').castafiore('<%=applicationName%>');
			});
		</script>
	</body>
</html>