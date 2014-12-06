
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Login.js"></script>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to the CMPE-273 Project Pik-A-Place</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>

  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <title>Pik-A-Place</title>
  <script type="text/javascript">

function noBack()
{
	window.history.forward();
}
	setTimeout("noBack()", 0);
	window.onunload=function(){null;};

</script>
<link href="style.css" rel="stylesheet" type="text/css" />
</head>
<!-- 
<body bgcolor="lightblue"> background="images/instagram-crop.jpg" style="color:white;">
<div>
<h1 align="center"><br><br>Welcome</h1>
<center style="height: 665px; width: 1024px; ">

<center>
<script type="text/javascript" src="js/Login.js"></script>
 -->


<body bgcolor="lightblue" background="${pageContext.request.contextPath}/resources/images/instagram-crop.jpg" style="color:black;" onload="if(event.persisted) noBack();">
 <h1 align="center">
<b>Welcome To PIC-A-PLACE</b>
</h1>
 <style type="text/css">
.Err
{color: red}
</style>
<!-- 
<form method="post" action="LoginController" onsubmit="return Login()"> -->
<center>
<font color="red">
<%
           String msg=(String)request.getAttribute("errorMessage");
           if(msg!=null)     
           {
        	   out.println(msg);   
           }
%>
</font>
</center>
<table align="center">
<tr>
<td>
Username:</td><td> <td><input type="text" name="username" id="username"><div id="error1" class="Err">  </div></td>
<tr>
<td>
Password :</td><td> <td><input type=password name="password" id="password"><div id="error2" class="Err">  </div></td></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr></tr>
<tr><td>
 
 <br>

 </table>
 <table align="center">
 <td><td><td>
<input type="reset" value="Reset">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <input type="submit" value="Submit" onclick="Login()">
 
 <br>Not registered ?? <a href="${pageContext.request.contextPath}/signup">Sign Up</a>
  </td></td>
 </table>
 <!-- </form> -->
 </center>
 </div>
</body>
</html>