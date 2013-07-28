<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="web splashy the most secure website to buy online in mauritius">
		<meta name="keywords" content="mauritius online shopping mall ecommerce buy online credit card paypall cart emall"> 
		<title>Web Splashy free website</title>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/os.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="css/themes/aristo/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
		<link rel="stylesheet" href="designer/google/css/buttons.css" type="text/css" media="screen, projection">
	</head>
	<body>
		<%String portalPath = "/root/users/" + request.getParameter("m") + "/site.ptl";%>
		<div id="portal"></div>
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
			$('#portal').castafiore('portal',{'casta_portalpath': '<%=portalPath%>'});
		});
			
		</script>
	</body>
</html>
