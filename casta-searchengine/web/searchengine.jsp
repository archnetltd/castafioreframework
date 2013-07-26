<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>The Mauritian E Mall</title>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/os.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="emimg/item/mall.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="css/themes/aristo/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
		<style>
								.menu{
									padding: 0;
									margin: 0;
									list-style: none;
									height: 31px;	
								}
								
								.menu li{
									float: left;
									padding: 7px 10px;
									margin: 0;
									height: 15px;
									width: 100px;
									text-align: center;
									
								}
								
								.menu li a{color: black !important;}
								.sub-menu{
									list-style: none;
									margin: 0;
									padding: 0;
									float:left;
									width: 372px;
									position: relative;
									top: 8px;
									left: -11px;
									-mox-box-shadow:5px 5px 5px #333;
									box-shadow:5px 5px 5px #333;
									z-index: 4000;
									
									
								}
								
								.sub-menu li{
									color:black;
									text-align: left;
									width: 350px;
									height: 82px;
									cursor: pointer;
								}
								
								
								
								.sub-menu li .sub-menu-item{
									float: left;
								}
								
								.sub-menu li{
									
								}
								
								.sub-menu li .sub-menu-item img{
									width: 75px;
									float: left;
									margin: 3px 4px 3px 3px;
								}
								
								.ui-widget-content{
									background: white !important;
								}
								
								.emallcatalogue .EXPageIterator .paginator_p.selected{
									background:  none repeat scroll 0 0 #CA047F !important;
								}
								
								.emallcatalogue .EXPageIterator .paginator_p{
									background: none repeat scroll 0 0 white !important;
									border: none !important;
									box-shadow:1px 2px 4px;
									-mox-box-shadow:1px 2px 4px;
									-webkit-box-shadow:1px 2px 4px;
									ms-box-shadow:1px 2px 4px;
								}
							</style>
		
	</head>
	<body id="index" style="background: white;">
		
		<div style="position: absolute; left: 58px; top: 35px;display:none" class="loader">
			<div class="bubblingG">
				<span id="bubblingG_1"></span>
				<span id="bubblingG_2"></span>
				<span id="bubblingG_3"></span>
			</div>
		</div>
		
		<div id="searchengine"></div>
		
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
				$('#searchengine').castafiore('searchengine');
			});
		</script>
	</body>
</html>