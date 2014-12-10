<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<<<<<<< HEAD

=======
<!-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successful Login</title>
</head>
<body bgcolor="A3B3B7">
<font size="5">

</font>
<center>
<h1>SUCCESS </h1>
</center>
</body>
</html> -->
>>>>>>> parent of ce8a79c... Delete Success.jsp

<html>

<head>
    <title>Home</title>
 

    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<<<<<<< HEAD
   
=======
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Maps.js"></script>
    
    <script language="javascript" type="text/javascript">
    var imageData= new Array(1000);
	
	createTwoDimensionalArray(3);
	
	var maxIndex = 0;
	
    jQuery.ajax({
        type: "POST",
        url: "http://localhost:8080/syncPhotos",
        data:"1375341852757639",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
				
        	//var i = 0;
        	
        	maxIndex = data.length;
        	for(var i=0; i< data.length; i++)
        	{
        		
        		imageData[i][0] = "\\resources\\img\\downloadedImages\\1375341852757639\\" + data[i];
        		imageData[i][1] = "";
        		imageData[i][2] = "";
        		//i++;
        	}
        	
        	if(maxIndex > 0)
        	{
        		document.getElementById('picDisplay').style.display = 'block';
        		document.getElementById('picSmall').style.display = 'block';
        			
        	}
        	
        	
        	
        },

        error: function (jqXHR, status, textStatus) {
        // error handler

        alert("error occured");

        }

        });

    
   	// Our index, boundry and scroll tracking variables
		
		var imageIndexFirst = 0;
		
		var imageIndexLast = 3;
		
		var continueScroll = 0;
		
		
		
		var minIndex = 0;
		
		
		// This function creates our two dimensional array
		
		function createTwoDimensionalArray(arraySize) {
		
		    for (i = 0; i < imageData.length; ++ i)
		
		        imageData[i] = new Array(arraySize);
		
		}
		
		
		// This function preloads the thumbnail images
		
		function preloadThumbnails() {
		
		    imageObject = new Image();
		
		    for (i = 0; i < imageData.length; ++ i)
		
		        imageObject.src = imageData[i][0];
		
		}
		
		
		
		// This function changes the text of a table cell
		
		function changeCellText(cellId,myCellData){
		
		    document.getElementById(cellId).innerHTML = myCellData;
		
		}
		
		
		// This function changes the images
		
		function changeImage(ImageToChange,MyimageData){
		
		    document.getElementById(ImageToChange).setAttribute('src',MyimageData)
		
		}
		
		
		// This function changes the image alternate text
		
		function changeImageAlt(ImageToChange,imageData){
		
		    document.getElementById(ImageToChange).setAttribute('alt',imageData)
		
		}
		
		
		// This function changes the image alternate text
		
		function changeImageTitle(ImageToChange,imageData){
		
		    document.getElementById(ImageToChange).setAttribute('title',imageData)
		
		}
		
		
		// This function changes the image onmouseover
		
		function changeImageOnMouseOver(ImageToChange,imageIndex){
		
		    document.getElementById(ImageToChange).setAttribute('onmouseover','handleThumbOnMouseOver(' + imageIndex + ');')
		
		}
		
		
		// This function hanles calling on change function
		
		// for a thumbnail onmouseover event
		
		function handleThumbOnMouseOver(imageIndex){
		
		    changeImage('imageLarge',imageData[imageIndex][0]);
		
		    changeCellText('imageTitleCell',imageData[imageIndex][1]);
		
		    changeCellText('imageDescriptionCell',imageData[imageIndex][2]);
		
		    changeImageAlt('imageLarge',imageData[imageIndex][1] + ' - ' + imageData[imageIndex][2]);
		
		    changeImageTitle('imageLarge',imageData[imageIndex][1] + ' - ' + imageData[imageIndex][2]);
		
		}
		
		
		// This function handles the scrolling in both directions
		
		function scrollImages(scrollDirection) {
		
		// We need a variable for holding our working index value
		
		    var currentIndex;
		
		// Determine which direction to scroll - default is down (left)
		
		    if (scrollDirection == 'up')
		
		    {
		
		// Only do work if we are not to the last image
		
		        if (imageIndexLast != maxIndex)
		
		        {
		
		// We set the color to black for both before we begin
		
		// If we reach the end during the process we'll change the "button" color to silver
		
		            document.getElementById('scrollPreviousCell').setAttribute('style','color: Black')
		
		            document.getElementById('scrollNextCell').setAttribute('style','color: Black')
		
		// Move our tracking indexes up one
		
		            imageIndexLast = imageIndexLast + 1;
		
		            imageIndexFirst = imageIndexFirst + 1;
		
		//  Change next "button" to silver if we are at the end
		
		            if (imageIndexLast == maxIndex)
		
		            {
		
		                document.getElementById('scrollNextCell').setAttribute('style','color: Silver')
		
		            }
		
		// Changescrollbar images in a set delay sequence to give a pseudo-animated effect
		
		            currentIndex = imageIndexLast;
		
		            changeImage('scrollThumb4',imageData[currentIndex][0]);
		
		            changeImageOnMouseOver('scrollThumb4',currentIndex);
		
		            currentIndex = imageIndexLast - 1;
		
		            setTimeout("changeImage('scrollThumb3',imageData[" + currentIndex + "][0])",25);
		
		            setTimeout("changeImageOnMouseOver('scrollThumb3'," + currentIndex + ")",25);
		
		            currentIndex = imageIndexLast - 2;
		
		            setTimeout("changeImage('scrollThumb2',imageData[" + currentIndex + "][0])",50);
		
		            setTimeout("changeImageOnMouseOver('scrollThumb2'," + currentIndex + ")",50);
		
		            currentIndex = imageIndexLast - 3;
		
		            setTimeout("changeImage('scrollThumb1',imageData[" + currentIndex + "][0])",75);
		
		            setTimeout("changeImageOnMouseOver('scrollThumb1'," + currentIndex + ")",75);
		
		// Wait and check to see if user is still hovering over button
		
		// This pause gives the user a chance to move away from the button and stop scrolling
		
		            setTimeout("scrollAgain('" + scrollDirection + "')",1000);
		
		        }
		
		    }
		
		    else
		
		    {
		
		// Only do work if we are not to the first image
		
		        if (imageIndexFirst != minIndex)
		
		        {
		
		// We set the color to black for both before we begin
		
		// If we reach the end during the process we'll change the "button" color to silver
		
		            document.getElementById('scrollPreviousCell').setAttribute('style','color: Black')
		
		            document.getElementById('scrollNextCell').setAttribute('style','color: Black')
		
		// Move our tracking indexes down one
		
		            imageIndexLast = imageIndexLast - 1;
		
		            imageIndexFirst = imageIndexFirst - 1;
		
		//  Change previous "button" to silver if we are at the beginning
		
		            if (imageIndexFirst == minIndex)
		
		            {
		
		                document.getElementById('scrollPreviousCell').setAttribute('style','color: Silver')
		
		            }
		
		// Change scrollbar images in a set delay sequence to give a pseudo-animated effect
		
		            currentIndex = imageIndexFirst;
		
		            changeImage('scrollThumb1',imageData[currentIndex][0]);
		
		            changeImageOnMouseOver('scrollThumb1',currentIndex);
		
		            currentIndex = imageIndexFirst + 1;
		
		            setTimeout("changeImage('scrollThumb2',imageData[" + currentIndex + "][0])",25);
		
		            setTimeout("changeImageOnMouseOver('scrollThumb2'," + currentIndex + ")",25);
		
		            currentIndex = imageIndexFirst + 2;
		
		            setTimeout("changeImage('scrollThumb3',imageData[" + currentIndex + "][0])",50);
		
		            setTimeout("changeImageOnMouseOver('scrollThumb3'," + currentIndex + ")",50);
		
		            currentIndex = imageIndexFirst + 3;
		
		            setTimeout("changeImage('scrollThumb4',imageData[" + currentIndex + "][0])",75);
		
		            setTimeout("changeImageOnMouseOver('scrollThumb4'," + currentIndex + ")",75);
		
		// Wait and check to see if user is still hovering over button
		
		// This pause gives the user a chance to move away from the button and stop scrolling
		
		            setTimeout("scrollAgain('" + scrollDirection + "')",1000);
		
		        }
		
		    }
		
		}
		
		
		// This function determines whether or not to keep scrolling
		
		function scrollAgain(scrollDirection)
		
		{
		
		    if (continueScroll == 1)
		
		    {
		
		        scrollImages(scrollDirection);
		
		    }
		
		}
		
		
		// This function kicks off scrolling down (left)
		
		function scrollPrevious() {
		
		    continueScroll = 1;
		
		    scrollImages('down');
		
		}
		
		
		// This function kicks off scrolling up (right)
		
		function scrollNext() {
		
		    continueScroll = 1;
		
		    scrollImages('up');
		
		}
		
		
		// This function stops the scrolling action
		
		function scrollStop() {
		
		    continueScroll = 0;
		
		}
		
		
		</script>
    
    
>>>>>>> parent of ce8a79c... Delete Success.jsp
    
    
    
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
<<<<<<< HEAD
                     //////////////////////////  
=======
                       
>>>>>>> parent of ce8a79c... Delete Success.jsp
                FB.api(link, function (res) {
                	jQuery.ajax({
            	        type: "POST",
            	        url: "http://localhost:8080/mainapp",
            	        data:JSON.stringify(accessToken),
            	        contentType: "application/json; charset=utf-8",
            	        dataType: "json",
            	        success: function (data, status, jqXHR) {
            	             
<<<<<<< HEAD
            	        	
=======
            	             alert(status);
            	             //window.location.href="/Recommendation"
>>>>>>> parent of ce8a79c... Delete Success.jsp
            	        },
            	    
            	        error: function (jqXHR, status, textStatus) {            
            	             // error handler
            	        	 
            	             alert("error " + status + " Text" + textStatus);
            	             window.location.href="/Error"
            	        }

            	    });

<<<<<<< HEAD
                   //////////////////////
=======
                   // showMap();
>>>>>>> parent of ce8a79c... Delete Success.jsp
                    
                    
                    
                    

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
<<<<<<< HEAD
   
</script>


=======
    function showMap() {

        $("#map").show( "slow", function() {
            // Animation complete.
        });

    }
    function testAPI() {

        console.log('Welcome, wc ');
        FB.api('/me', function(response) {
            console.log('Good to see you, ' + response.name + '.');
            console.log(response);
            var id = response.id;
            var name = response.name;
            var username = response.username;
            var email = response.email;

            var link = '/' + id + '/permissions?access_token=' + accessToken;

            FB.api(link, function (res) {
                console.log("permissons: ", res);
                link = '/' + id + '/photos?fields=images';
                var images = [];

                FB.api(link, function (response) {
                    //placing all pictures
                    for (var i = 0; i < response.data.length; i++) {
                        /*var img = "<img src=" + response.data[i].images[0].source + " class='small' />";
                        $("#images").append(img);
                        */
                       images.push(response.data[i].images[0].source);
                    }
                    console.log(response);
                    console.log(images);
                });

            });

        });


    }
</script>

<body >
</body>
>>>>>>> parent of ce8a79c... Delete Success.jsp
</html>
