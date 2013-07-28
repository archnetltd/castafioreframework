<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/castafiore.tld" prefix="casta" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Use castafiore with tag</title>
		<script type="text/javascript" src="js/1.js"></script>
		<link rel="stylesheet" href="css/1.css" type="text/css"></link>
		<link rel="stylesheet" href="css/themes/kureem/kureem-theme.css" type="text/css"></link>
	</head>
	<body>
	<script>
	$(document).ready(function(){
		$("#9520965 *[__path^='0']").remove();
		$("<div ></div>").attr({"id":"9520965"}).appendTo($("#root_os"));
		$("#9520965 *[__path^='0/0']").remove();
		$("<div> <div> <div id=\"login-box\" style=\"margin: auto;\"> <h2>Login<\/h2> Please enter your username and password to login <div id=\"login-box-name\" style=\"margin-top:20px;\">Username:<\/div> <div id=\"login-box-field\" style=\"margin-top:20px;\"> <input name=\"username\" class=\"form-login\" title=\"Username\" value=\"\" size=\"30\" maxlength=\"2048\"\/> <\/div> <div id=\"login-box-name\">Password:<\/div> <div id=\"login-box-field\"> <input name=\"password\" type=\"password\" class=\"form-login\" title=\"Password\" value=\"\" size=\"30\" maxlength=\"2048\"\/> <\/div> <br\/> <span class=\"login-box-options\"> <input type=\"checkbox\" name=\"1\" value=\"1\"> Remember Me <a href=\"#\" style=\"margin-left:30px;\">Forgot password?<\/a> <\/span> <br\/> <br\/> <a href=\"#\" name=\"login\"><img src=\"images\/login-btn.png\" width=\"103\" height=\"42\" style=\"margin-left:90px;\"\/><\/a> <\/div> <\/div> <\/div>").attr({"id":"31329679"}).appendTo($("#9520965"));
		$("#31329679  *[name='username']").replaceWith($("<input  type=text ></input>").attr({"id":"21778233"}));
		$("#21778233").attr({"stf":"true","name":"username","class":" form-login","__path":"0\/0\/0"});
		$("#31329679  *[name='password']").replaceWith($("<input  type=password ></input>").attr({"id":"15023722"}));
		$("#15023722").attr({"stf":"true","name":"password","class":" form-login","__path":"0\/0\/1"});
		$("#31329679  *[name='login']").replaceWith($("<a ><img src=\"blueprint\/images\/login-btn.png\" width=\"103\" height=\"42\" style=\"margin-left:90px;\" \/></a>").attr({"id":"6609152"}));
		$("#6609152").attr({"name":"login","__path":"0\/0\/2"}).click(function (event) {
			$("#6609152");
			sCall({"casta_eventid":"5272169","casta_applicationid":"os","casta_componentid":"6609152"})
			;
			$("#9520965").mask("chargement....");
	
		});
		$("#31329679").attr({"name":"login","__path":"0\/0"});
		$("#9520965").attr({"name":"os","__path":"0"});
		jQuery('.loadmask').css('display','none');jQuery('.loadmask-msg').css('display','none');
	});</script>
		<casta:castafiore name="helloworld"/>
	</body>
	<script type="text/javascript" src="js/2.js"></script>
</html>
