/**
 * 
 */
function Login()
{
	
      return user();
      
      function username()
      {
            var username=document.getElementById("username").value;
            document.getElementById("error1").innerHTML="";
            var reg=/^[A-Za-z0-9\s]{4,20}$/;
            if(username==null || username=="")
            {
            document.getElementById("error1").innerHTML="username can not be blank";
            document.getElementById("username").focus();
            return false;
            }
            }   
            return true;
      }
      
      function password()
      {
    	  var password=document.getElementById("password").value;
          document.getElementById("error2").innerHTML="";
          var reg=/^[A-Za-z0-9\s]{6,30}$/;
          if(password==null || password=="")
          {
          document.getElementById("error2").innerHTML="Password cannot be blank";
          document.getElementById("password").focus();
          return false;

          }     
          return true;
      }
      function user() {
        		alert("1");
        		var username=document.getElementById("username").value;
        	    var password=document.getElementById("password").value;
        	    
        	    
        	    var string={ 
        	    		"username" : username,"password" : password
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
        	        url: "http://localhost:8080/userlogin",
        	        data:JSON.stringify(string),
        	        contentType: "application/json; charset=utf-8",
        	        dataType: "json",
        	        success: function (data, status, jqXHR) {
        	             
        	             alert(status);
        	             window.location.href="/success"
        	        },
        	    
        	        error: function (jqXHR, status, textStatus) {            
        	             // error handler
        	        	 
        	             alert("error " + status + " Text" + textStatus);
        	             window.location.href="/Error"
        	        }

        	    });

        	}
          






