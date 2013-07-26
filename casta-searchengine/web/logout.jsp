<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
	session.invalidate();

String to = request.getParameter("to");
if(to != null && to.length() > 0){
		response.sendRedirect(to);	
}else{
	response.sendRedirect("/");
}
%>
<title></title>
</head>
<body>

</body>
</html>