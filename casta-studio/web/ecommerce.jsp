<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
	
		<%
			org.castafiore.shoppingmall.merchant.Merchant merchant = org.castafiore.searchengine.MallUtil.getMerchant(request.getParameter("m"));
			
			
		%>
		<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
				
		
				
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="<%=merchant.getTitle() %> is in emall of mauritius, the most secure website to buy online in mauritius">
		<meta name="keywords" content="mauritius online shopping mall ecommerce buy online credit card paypall cart emall"> 
		<title><%=merchant.getTitle()%></title>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
		<link rel="stylesheet" href="blueprint/os.css" type="text/css" media="screen, projection"></link> 
		<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>
		
	</head>
	<body>
	<div id="switcher"></div>
		<%
			String applicationName = "portal";
			String portalPath = "/root/users/" + request.getParameter("m") + "/ecommerce.ptl";
			
			String uid = request.getParameter("p");
		%>
		<div id="script_<%=applicationName%>"></div>
		<div id="root_portal"></div>
		<script type="text/javascript" src="js/1.js"></script>
		<script type="text/javascript" src="js/2.js"></script>
		<script type="text/javascript" src="http://jqueryui.com/themeroller/themeswitchertool/"></script>
		<script>
			jQuery(document).ready(function(){ 
				jQuery("#script_<%=applicationName%>").mask('Please wait....');
				jQuery.post("castafiore/?casta_applicationid=portal&casta_portalpath=<%=portalPath%>&casta_userid=<%=uid%>", 
					function(data){
						jQuery("#script_<%=applicationName%>").html(data);
						jQuery(".loadmask").css('display', 'none');
						jQuery(".loadmask-msg").css('display', 'none');
					}
				);

				$('#switcher').themeswitcher();
			});
			
		</script>
	</body>
</html>
