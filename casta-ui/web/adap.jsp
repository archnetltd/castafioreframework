<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.castafiore.web.servlet.CastafioreServletAdaptor"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>The Mauritian E Mall</title>
		
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/os.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
		<link rel="stylesheet" href="designer/google/css/buttons.css" type="text/css" media="screen, projection">
	
		<%
			String app = request.getParameter("casta_applicationid");
		%>
	
	</head>
	<body style="margin: 0;padding: 0;">
		<div id="<%=app%>">
			<div id="root_<%=app%>">
				<%=CastafioreServletAdaptor.doAdapt(request,response)%>
			</div>
		</div>
		<div  id="footer" style="display: none;">
			<h5 class="accessAid">More Information</h5>
			<ul>
				<li><a href="http://www.archnetltd.com">About Us</a></li>
				<li><a href="agreementmerchant.html">Subscription agreement</a></li>
				<li><a href="plans.html">Subscription plans</a></li>
				<li><a href="#">Directory</a></li>
				<li><a href="#">FaceBook</a></li>
				<li><a href="#">Twitter</a></li>
			</ul>
			<p id="legal">Copyright &copy; 1999-2011 ArchNet ltd. All rights reserved.<br>Users are advised to read the <a href="terms.html">terms and conditions</a> carefully.</p>
		</div>		
		<script type="text/javascript" src="ckeditor/ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="js1/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="js1/jquery-ui-1.8.21.js"></script>
		<script type="text/javascript" src="js1/jquery.plugin.js"></script>
		<script type="text/javascript" src="js1/ext.js"></script>
		<script type="text/javascript" src="js1/jquery.maskedinput-1.3.js"></script>
		<script type="text/javascript" src="js1/jquery.rightClick.js"></script> 
		<script type="text/javascript" src="js1/jquery.blockUI.js"></script>
		

		
		<!--
		<script type="text/javascript" src="js/1.js"></script>
		<script type="text/javascript" src="js/2.js"></script>
		<script type="text/javascript" src="js/ext.js"></script>
		<script type="text/javascript" src="http://trentrichardson.com/examples/timepicker/js/jquery-ui-timepicker-addon.js"></script>
		 -->
		 
		 
		 
		<script>
			$(document).ready(function(){
				$('#os').castafiore('os');
			});
		</script>
	</body>
</html>