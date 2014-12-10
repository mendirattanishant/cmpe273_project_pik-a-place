<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Thank You!!</title>
<script type="text/javascript">

function noBack()
{
	window.history.forward();
}
	setTimeout("noBack()", 0);
	window.onunload=function(){null;};

</script>

</head>
<h1 style=color:olive align="center">Thank You for using our Application</h1>
<body bgcolor="lightblue" background="${pageContext.request.contextPath}/resources/images/Project_Image.jpgt style="color:black;" onload="if(event.persisted) noBack();">
<%session.invalidate();

%>
<center>


<div align="center"><img src="${pageContext.request.contextPath}/resources/images/Logout.jpg" style="width: 1314px; height: 104px"></div>
<h2>
<a href="${pageContext.request.contextPath}/pik-a-place">Click here to Login</a>
</h2>
</center>
</body>
</html>