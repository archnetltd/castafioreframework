<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
// mysqldump -h localhost -uroot -pa28n12l10 eliensonsv2 > /usr/local/software/tomcat6/webapps/elie/backup/1335153905793-elieandsons.sql
String command = "mysqldump -h localhost -uroot -pa28n12l10 eliensonsv2 > /usr/local/software/tomcat6/webapps/elie/backup/" + System.currentTimeMillis() + "-elieandsons.sql";
out.println(command);
	Runtime.getRuntime().exec(command);
%>
</body>
</html>