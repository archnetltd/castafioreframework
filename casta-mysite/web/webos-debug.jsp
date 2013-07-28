<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Castafiore</title>
		
			
			<script type="text/javascript" src="js/jquery-1.4.2.js"></script>
			<script type="text/javascript" src="js/jquery.plugin-0810.02.js"></script>
			<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
			
			<script type="text/javascript" src="js/jquery.rightClick.js"></script>
			<script type="text/javascript" src="js/castafiore.js"></script>
			<script type="text/javascript" src="js/fg.menu.js"></script>
			
			<script type="text/javascript" src="js/jquery.loadmask.min.js"></script>
			<script type="text/javascript" src="js/jquery.history.js"></script>
			<script type="text/javascript" src="js/jquery.layout.min.js"></script>
			<script type="text/javascript" src="js/jquery.hotkeys-0.7.9.min.js"></script>
			<script type="text/javascript" src="js/jquery.bgiframe.min.js"></script>
			
			<script type="text/javascript" src="js/jquery.autocomplete.min.js"></script>
			
			<script type="text/javascript" src="js/ext.js"></script>
			<script type="text/javascript" src="htmlBox/htmlbox.full.js"></script>
			
			
		
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
	</head>
	<body style="background-image:url(webos/img/background.jpg);margin: 0;padding: 0">
		<div id="webos"></div>
		<script>
			$(document).ready(function(){
				$('#webos').castafiore('webos');
			});
		</script>
		
	</body>
</html>