<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="interactive website templates, jquery templates, ajax templates" />
		<meta name="description" content="Absolutely top quality, unique and creative web site templates designed by certified web professionals. The #1 web template membership on Internet." />
		<title>Interactive full website by TemplateWorld.com!</title>
		<link href="http://demo.templateworld.com/full-websites/160b/css/style.css" rel="stylesheet" type="text/css" />
		<!--Contact starts here -->
		<script type="text/javascript" src="http://demo.templateworld.com/full-websites/160b/include/jquery.twit.min.js"></script>
		<script type="text/javascript" src="http://demo.templateworld.com/full-websites/160b/include/jquery.twit.js"></script>
		<link href="http://demo.templateworld.com/full-websites/160b/include/jquery.twit.css" type="text/css" rel="stylesheet" />
		<!--Contact ends here -->

		<!--Twitter start here -->
		<script type="text/javascript" src="http://demo.templateworld.com/full-websites/160b/include/jquery.example.js"></script>
		<script type="text/javascript" src="http://demo.templateworld.com/full-websites/160b/include/jquery.validate.pack.js"></script>
		<script type="text/javascript" src="http://demo.templateworld.com/full-websites/160b/include/jquery.contactable.js"></script>
		<link rel="stylesheet" href="http://demo.templateworld.com/full-websites/160b/include/contactable.css" type="text/css" />
		<!--Twitter ends here -->

		<script type="text/javascript">
		//Contact starts here	
			$(function(){
				$('#contactAt').contactable({
			 		recipient: 'email@companyname.com',
			 		subject: 'The Quick Message'
			 	});
			});
	
//Contact Ends here

		
			$(document).ready(function(){
			
			//Twit start here
					twitShowStatus = 1;
					function twitShow() {
							$('div#twitable').animate({"marginLeft": "-=5px"}, "fast"); 
							$('#twitform').animate({"marginLeft": "-=0px"}, "fast");
							$('div#twitable').animate({"marginLeft": "+=748px"}, "slow"); 
							$('#twitform').animate({"marginLeft": "+=745px"}, "slow");
							$('.twitContainer .close').css({right: '-4px'});
							twitShowStatus = 0;
						}
					function twitHide() {
							$('#twitform').animate({"marginLeft": "-=745px"}, "slow");
							$('div#twitable').animate({"marginLeft": "-=748px"}, "slow").animate({"marginLeft": "+=5px"}, "fast"); 
							$('.twitContainer .close').css({right: '0px'});
							twitShowStatus =1;
						}
					
						$('div#twitable').click(
							function() {
							if(twitShowStatus==1)
								twitShow();
							else
								twitHide();
						
						});
					
					username = 'dotDW';// twitter User name
					$('#twitform').twit(username);
					$('#twitform').twit(username, {
					limit: 5,
					label: 'Twitter',
					title: 'My tweets'
					});
							
});	
	
</script>
<!--foot support-->
<style type="text/css" media="all">
#fotplug{
	width:100%;
	height:40px;
	float:left;
}
</style>

</head>
	<body style="margin: 0;padding: 0;">
		<div id="os"></div>
		
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
				$('#os').castafiore('os');
			});
		</script>
	</body>
</html>