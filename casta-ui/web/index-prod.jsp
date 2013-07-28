<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<%
			boolean timber = false;
			String title = "Upstage";
			boolean emall = false;
			if("www.timberafrica.com".equalsIgnoreCase(request.getServerName())  || "www.timberafrica.mu".equalsIgnoreCase(request.getServerName())){
				title = "Timber Africa";
				timber = true;
			}else if("www.emallofmauritius.com".equalsIgnoreCase(request.getServerName())){
				emall = true;
				title = "eMall of Mauritius";
			}
		%>
		<title><%=title%></title>
	
		<%if(timber){%>
			<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/os.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
		<link rel="stylesheet" href="http://www.gcmingati.net/wordpress/wp-content/lab/jquery/newsticker/jq-liscroll/li-scroller.css" type="text/css"></link>
		<link rel="stylesheet" href="http://timberafrica.gofreeserve.com/pages/SpryAssets/SpryMenuBarVertical.css" type="text/css"></link>
		<link rel="stylesheet" href="1min.css" type="text/css"></link>
		<style>div[name="s"]{clear:both !important}</style>
		<%}else if(emall){%>
		
		<meta name="description" content="emallofmauritius.com, Mauritius's vital online shopping mall. emallofmauritius allows mauritians to buy online quickly and safely, The first complete online shopping mall for mauritius">
		<meta name="keywords" content="mauritius online shopping mall buy credit card phone safely"> 
		
		<link rel="stylesheet" href="emimg/item/mall.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="css/themes/emall1/theme.css" type="text/css"></link>
		<!--[if lt IE 9]>
		<style>
		.mall .search{width:737px;height:32px}
		.mall .search input{top: -6px;}
		.home .top{width: 732px;height: 225px;}
		.mall .login-btn-ctn{float: left; margin: 0 0 0 3px;}
		.mall .login .bl{background-position:0 0;}
		.mall .login .br{background-position:100% 0px;}
		.mall .login .bc{background-position:0px 6px;}
		.mall .login .mc{height:139px}
		.mall .login .login-frm-right{height:105px}
		</style>
		<![endif]-->
		
		<%}else{%>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/os.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
		<%}%>
	</head>
	<body>
		<%if(emall){%>
		<div id="searchengine" class="Blue"></div>	
		<script type="text/javascript" src="js/1.js"></script>
		<script type="text/javascript" src="js/2.js"></script>
		<script type="text/javascript" src="js/ext.js"></script>
		<script>
			$(document).ready(function(){
				$('#searchengine').castafiore('searchengine');
			});
		</script>
		<%}else{
				String ptl = "/root/users/upstage/ecommerce.ptl";
				if(timber){
					ptl = "/root/users/timbarafrica/ecommerce.ptl";
				}
		%>
				<div id="script_portal"></div>
				<div id="root_portal"></div>
				<script type="text/javascript" src="js/1.js"></script>
				<script type="text/javascript" src="js/2.js"></script>
				<script>
					jQuery(document).ready(function(){ 
						jQuery("#script_portal").mask('Please wait....');
						jQuery.post("castafiore/?casta_applicationid=portal&devmode=true&casta_portalpath=<%=ptl%>", 
							function(data){
								jQuery("#script_portal").html(data);
								jQuery(".loadmask").css('display', 'none');
								jQuery(".loadmask-msg").css('display', 'none');
							}
						);
					});
				</script>
		<%}%>
	</body>
</html>