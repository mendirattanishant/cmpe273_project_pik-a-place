function index() {
	
	alert("12133");
	var firstname=document.getElementById("firstname").value;
    var lastname=document.getElementById("lastname").value;
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    var cpassword=document.getElementById("cfpassword").value;
    var email=document.getElementById("email").value;
    
    var string={ firstname : String, lastname : String,
    		username : String,password : String, cpassword : String, email : String
    };
    
    
         $.ajax({ 
        	 
        	 alert("post");
             type: "POST",
             data:JSON.stringify(string),
             dataType: "JSON",
             url: "http://localhost:8080/LoginController",
             success: function(data){        
                alert(data);
             }
         });
    
     
   
    
    
}