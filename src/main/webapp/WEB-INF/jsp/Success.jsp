<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>

<head>
    <title>Home</title>
 

    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
   
    
    
    
</head>

<body>
<div id="status">
</div>



<div id="images"></div>


<script>

    // This is called with the results from from FB.getLoginStatus().
    var accessToken = ''
    function statusChangeCallback(response) {

        if (response.status === 'connected') {

            // Logged into your app and Facebook.
            accessToken = response.authResponse.accessToken;
            console.log("finally logging: " + response)

        } else if (response.status === 'not_authorized') {

            // The person is logged into Facebook, but not your app.

            document.getElementById('status').innerHTML = '' +

                    '';

        } else {

            // The person is not logged into Facebook, so we're not sure if

            // they are logged into this app or not.

            document.getElementById('status').innerHTML = ' ' +

                    '';

        }

    }



    // This function is called when someone finishes with the Login

    // Button.  See the onlogin handler attached to it in the sample

    // code below.

    function checkLoginState() {

        FB.getLoginStatus(function(response) {

            statusChangeCallback(response);

        });

    }



    window.fbAsyncInit = function() {

        FB.init({

            appId      : '364376597062363',

            cookie     : true,  // enable cookies to allow the server to access

            // the session

            xfbml      : true,  // parse social plugins on this page

            version    : 'v2.1' // use version 2.1

        });



        FB.getLoginStatus(function(response) {

            statusChangeCallback(response);

        });



    };



    // Load the SDK asynchronously

    (function(d, s, id) {

        var js, fjs = d.getElementsByTagName(s)[0];

        if (d.getElementById(id)) return;

        js = d.createElement(s); js.id = id;

        js.src = "//connect.facebook.net/en_US/sdk.js";

        fjs.parentNode.insertBefore(js, fjs);

    }(document, 'script', 'facebook-jssdk'));

    function fblogin() {

            FB.logout(function(){document.location.reload();});



        FB.login(function (response) {
            if (response.authResponse) {
                //checkLoginState();
                var link = '/me/permissions?access_token=' + response.authResponse.accessToken;
                accessToken = response.authResponse.accessToken;
                
                        //console.log("access - " + accessToken);
                     //////////////////////////  
                FB.api(link, function (res) {
                	jQuery.ajax({
            	        type: "POST",
            	        url: "http://localhost:8080/mainapp",
            	        data:JSON.stringify(accessToken),
            	        contentType: "application/json; charset=utf-8",
            	        dataType: "json",
            	        success: function (data, status, jqXHR) {
            	             
            	        	
            	        },
            	    
            	        error: function (jqXHR, status, textStatus) {            
            	             // error handler
            	        	 
            	             alert("error " + status + " Text" + textStatus);
            	             window.location.href="/Error"
            	        }

            	    });

                   //////////////////////
                    
                    
                    
                    

                    //testAPI();
                });
            } else {
                console.log('User cancelled login or did not fully authorize.');
            }
        }, {scope:'email,user_friends,user_photos,user_tagged_places'});
    }
    //
    //scope: 'user_photos,user_tagged_places'
    // Here we run a very simple test of the Graph API after login is

    // successful.  See statusChangeCallback() for when this call is made.
   
</script>


</html>
