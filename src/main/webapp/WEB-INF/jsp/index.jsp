<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/index.js"></script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<title>Pic-A-Place</title>
<script type="text/javascript">

function noBack()
{
	window.history.forward();
}
	setTimeout("noBack()", 0);
	window.onunload=function(){null;};

</script>

</head>
<body bgcolor="lightblue" background="${pageContext.request.contextPath}/resources/images/instagram-crop.jpg" style="color:black;" onload="if(event.persisted) noBack();">
<%session.invalidate();
%>

</head>
<body bgcolor="lightblue" background="${pageContext.request.contextPath}/resources/images/instagram-crop.jpg" style="color:black;">
 <h1 align="center">
<b>User Registration</b>
</h1>
<!-- <form name="signupform" onsubmit="return index()" method="post" action="/LoginController1"> -->
<center>

<div> First Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="firstname" id="firstname" size="20" /></div><br/>

<div> Last Name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="lastname" id="lastname" size="20" /></div><br/>

<div> Username &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="username" id="username" size="20" /></div><br/>

<div>Password &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="password" id="password" /></div><br/>

<div>Confirm Password &nbsp;<input type="password" name="cfpassword" id="cfpassword"/></div><br/>

<div>E-mail &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="email" id="email" /></div><br/>

<input type="submit" value="Sign Up" onclick="validateForm()" />

<input type="reset" value="Reset" />

</center>
<!-- </form> -->

<script type="text/javascript"  language="javascript">
function validateForm() {
	alert("form");
    var v1 = document.getElementById("firstname").value;
    if (v1 == null || v1 == "") {
        alert("First name cannot be left blank");
        return false;
    }
    var v2=document.getElementById("lastname").value;
    if (v2 == null || v2 == "") {
        alert("Last name cannot be left blank");
        return false;
    }
    var v3 = document.getElementById("username").value;
    if (v3 == null || v3 == "") {
        alert("username cannot be left blank");
        return false;
    }
    var v4 = document.getElementById("password").value;
    if (v4 == null || v4 == "") {
        alert("Password cannot be left blank");
        return false;
    }
    var v5 = document.getElementById("cfpassword").value;
    if (v5 == null || v5 == "") {
        alert("Confirm Password cannot be left blank");
        return false;
    }
    var v6 = document.getElementById("email").value;
    if (v6 == null || v6 == "") {
        alert("Email cannot be left blank");
        return false;
    }
    return index();
    
} 

function index() {
	
	var firstname=document.getElementById("firstname").value;
    var lastname=document.getElementById("lastname").value;
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    var email=document.getElementById("email").value;
    
    var string={ "firstname" : firstname , "lastname" : lastname,
    		"username" : username,"password" : password, "email" : email
    };
    alert(string);

         /* $.ajax({
             type: "POST",
             url: "http:/localhost:8080/LoginController1",
             contentType: "application/json",
             data:JSON.stringify(string),
             dataType: "JSON",
             success: function(data){        
                alert(data);
             },
         error : function(jqXHR, textStatus, errorThrown){
        	 alert(textStatus + errorThrown); 
         }
         });  */
    	jQuery.ajax({
        type: "POST",
        url: "http://localhost:8080/LoginController1",
        data:JSON.stringify(string),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
             
             alert(status);
             window.location.href="/pik-a-place"
        },
    
        error: function (jqXHR, status, textStatus) {            
             // error handler
             alert("error" + status + "Text" + textStatus);
             window.location.href="/Error"
        }

    });

}
</script>
</body>
</html>