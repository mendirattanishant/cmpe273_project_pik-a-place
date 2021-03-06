<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="Database.FilteredUserPhotoPlace" %>
    <%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
     <title>Recommendation</title>
     <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
     <meta charset="utf-8">
     <style>
       html, #map_canvas {
         margin: 0;
         padding: 0;
         height: 100%;
       }
       body
   		 {
        background:url('${pageContext.request.contextPath}/resources/images/map1.jpg') no-repeat center center fixed;
        background-size: cover;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        margin: 0;
        padding: 0;
    	}
     </style>
      <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    
<script src="https://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false"></script>
<script type="text/javascript">    

/*
var locations = [
                    ['<b>Customer</b><br>Address', 52.6699927, -0.7274620, 1, 'http://maps.google.com/mapfiles/ms/icons/blue.png'],
                    ['<b>Leicester</b><br>Unit B, St Margarets Way, Leicester<br>0116 262 7355', 52.646179, -1.14004, 2],
                    ['<b>Nottingham</b><br>Victoria Retail Park, Netherfield, Nottingham<br>0115 940 0811', 52.961685, -1.06394, 3],
                    ['<b>Nuneaton</b><br>Newtown Road Nuneaton Warwickshire<br>02476 642220', 52.5245, -1.46764, 4],
                    ['<b>Peterborough</b><br>Mallory Road, Boongate, Peterborough, Cambridgeshire<br>01733 561357', 52.574116, -.219535, 5],
                    ['<b>Wellingborough</b><br>Victoria Retail Park, Whitworth Way, London Road, Wellingborough<br>01933 276225', 52.289585, -.68429, 6]     
                ];      
*/
// Store Name[0],delivery[1],Address[2],Delivery Zone[3],Coordinates[4] data from FusionTables pizza stores example
var locations = [
[ "John's Pizza, visitor: Aparna","no","400 University Ave, Palo Alto, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.121277,37.386799,0 -122.158012,37.4168,0 -122.158012,37.448151,0 -122.142906,37.456055,0 -122.118874,37.45224,0 -122.107544,37.437793,0 -122.102737,37.422526,0 -122.113037,37.414618,0 -122.121277,37.386799,0   </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.447038,-122.160575","http://maps.google.com/mapfiles/ms/icons/blue.png"],
["JJs Express, visitor: Nishant","yes","1000 Santa Cruz Ave, Menlo Park, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.200928,37.438611,0 -122.158012,37.4168,0 -122.158012,37.448151,0 -122.142906,37.456055,0 -122.144623,37.475948,0 -122.164192,37.481125,0 -122.189255,37.478673,0 -122.208481,37.468319,0 -122.201271,37.438338,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.448638,-122.187176","http://maps.google.com/mapfiles/ms/icons/green.png"],
["John Paul's Pizzeria, visitor: Raunaq","no","1100 El Camino Real, Belmont, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.304268,37.516534,0 -122.300835,37.505096,0 -122.262383,37.481669,0 -122.242813,37.502917,0 -122.244186,37.534232,0 -122.269249,37.550021,0 -122.291222,37.545122,0 -122.302895,37.537499,0 -122.304268,37.516534,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.520436,-122.275978","http://maps.google.com/mapfiles/ms/icons/red.png"],
["JJs Express, visitor: Bharti","yes","300 E 4th Ave, San Mateo, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.304268,37.516534,0 -122.348557,37.538044,0 -122.359886,37.56363,0 -122.364006,37.582405,0 -122.33654,37.589207,0 -122.281609,37.570433,0 -122.291222,37.545122,0 -122.302895,37.537499,0 -122.304268,37.516534,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.564435,-122.322080","http://maps.google.com/mapfiles/ms/icons/green.png"],
["John's Pizza, visitor: Arjun","yes","1400 Broadway Ave, Burlingame, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.374306,37.548933,0 -122.348557,37.538044,0 -122.359886,37.56363,0 -122.364006,37.582405,0 -122.33654,37.589207,0 -122.359543,37.59764,0 -122.372246,37.604712,0 -122.417564,37.594648,0 -122.374306,37.548933,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.584935,-122.366182","http://maps.google.com/mapfiles/ms/icons/blue.png"],
["JJs Express, visitor: Aparna","yes","700 San Bruno Ave, San Bruno, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.462883,37.628916,0 -122.445374,37.639247,0 -122.426147,37.648762,0 -122.405205,37.642238,0 -122.400055,37.628644,0 -122.392159,37.610696,0 -122.372246,37.604712,0 -122.417564,37.594648,0 -122.462196,37.628644,0  </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.630934,-122.406883","http://maps.google.com/mapfiles/ms/icons/green.png"],
["JJs Express, visitor: Nishant","yes","300 Beach St, San Francisco, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.43576,37.790795,0 -122.449493,37.801646,0 -122.425461,37.809784,0 -122.402115,37.811411,0 -122.390442,37.794593,0 -122.408295,37.79188,0 -122.434387,37.789167,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.807628,-122.413782","http://maps.google.com/mapfiles/ms/icons/green.png"],
["JJs Express, visitor: Raunaq","yes","1400 Haight St, San Francisco, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.463398,37.760266,0 -122.477349,37.774785,0 -122.427349,37.774785,0 -122.429237,37.763658,0 -122.46357,37.760808,0</coordinates></LinearRing></outerBoundaryIs></Polygon>","37.770129,-122.445082","http://maps.google.com/mapfiles/ms/icons/green.png"],
["JJs Express, visitor: Bharti","yes","2400 Mission St, San Francisco, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.418766,37.747779,0 -122.425289,37.768951,0 -122.406063,37.769901,0 -122.406063,37.749679,0 -122.418251,37.747508,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.758630,-122.419082","http://maps.google.com/mapfiles/ms/icons/green.png"],
["JJs Express, visitor: Arjun","yes","500 Castro St, Mountain View, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.121277,37.386799,0 -122.108917,37.362244,0 -122.077675,37.3385,0 -122.064285,37.378615,0 -122.069778,37.3898,0 -122.076645,37.402619,0 -122.078705,37.411619,0 -122.113037,37.414618,0 -122.121277,37.386799,0  </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.390040,-122.081573","http://maps.google.com/mapfiles/ms/icons/green.png"],
["John's Pizza, visitor: Aparna","no","100 S Murphy Ave, Sunnyvale, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.047119,37.33113,0 -122.065315,37.332495,0 -122.077675,37.3385,0 -122.064285,37.378615,0 -122.036819,37.385162,0 -122.006607,37.382162,0 -122.00386,37.342048,0 -122.047119,37.331403,0  </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.377441,-122.030071","http://maps.google.com/mapfiles/ms/icons/blue.png"],
["John's Pizza, visitor: Nishant","no","1200 Curtner Ave, San Jose, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-121.935196,37.345051,0 -121.931076,37.294267,0 -121.871338,37.293721,0 -121.806793,37.293174,0 -121.798553,37.361426,0 -121.879578,37.36088,0 -121.934509,37.345597,0 -121.935196,37.345051,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.288742,-121.890765","http://maps.google.com/mapfiles/ms/icons/blue.png"],
["John's Pizza, visitor: Raunaq","yes","700 Blossom Hill Rd, San Jose, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-121.935883,37.253287,0 -121.931076,37.294267,0 -121.871338,37.293721,0 -121.806793,37.293174,0 -121.790657,37.234702,0 -121.852455,37.223221,0 -121.935539,37.253014,0 </coordinates></LinearRing></outerBoundaryIs></Polygon>","37.250543,-121.846563","http://maps.google.com/mapfiles/ms/icons/blue.png"],
["John's Pizza, visitor: Bharti","yes","100 N Milpitas Blvd, Milpitas, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-121.947556,37.435612,0 -121.934509,37.476493,0 -121.893311,37.469409,0 -121.852798,37.429615,0 -121.843872,37.400165,0 -121.887817,37.3898,0 -121.959915,37.420345,0 -121.959915,37.427979,0 -121.948929,37.435612,0 -121.947556,37.435612,0</coordinates></LinearRing></outerBoundaryIs></Polygon>","37.434113,-121.901139","http://maps.google.com/mapfiles/ms/icons/blue.png"],
["John's Pizza, visitor: Arjun","yes","3301 Mowry Blvd, Fremont, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.02343,37.52198,0 -122.023773,37.558731,0 -121.989784,37.573426,0 -121.959572,37.566351,0 -121.944466,37.544305,0 -121.967125,37.520891,0 -122.023087,37.522525,0</coordinates></LinearRing></outerBoundaryIs></Polygon>","37.552773,-121.985153","http://maps.google.com/mapfiles/ms/icons/blue.png"]
["John's Pizza, visitor: kkk","yes","3300 Mowry Blvd, Fremont, CA","<Polygon><outerBoundaryIs><LinearRing><coordinates>-122.02343,37.52198,0 -122.023773,37.558731,0 -121.989784,37.573426,0 -121.959572,37.566351,0 -121.944466,37.544305,0 -121.967125,37.520891,0 -122.023087,37.522525,0</coordinates></LinearRing></outerBoundaryIs></Polygon>","37.552773,-121.985153","http://maps.google.com/mapfiles/ms/icons/blue.png"]

];
// alert(locations.length);

var geocoder = null;
var map = null;
var customerMarker = null;
var gmarkers = [];
var closest = [];
var directionsDisplay = new google.maps.DirectionsRenderer();
var directionsService = new google.maps.DirectionsService();
var map;
var imageData= new Array(1000);

createTwoDimensionalArray(3);

var maxIndex = 0;

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









function initialize() {
// alert("init");
  geocoder = new google.maps.Geocoder();
  map = new google.maps.Map(document.getElementById('map'), 
        {       
            zoom: 6,       
            center: new google.maps.LatLng(21.9747300,96.0835900),       
            mapTypeId: google.maps.MapTypeId.ROADMAP     
        });      
  var infowindow = new google.maps.InfoWindow();      
  var marker, i;      
  var bounds = new google.maps.LatLngBounds();
  document.getElementById('info').innerHTML = "found "+locations.length+" locations<br>";
  /*for (i = 0; i < locations.length; i++) {         
            var coordStr = locations[i][4];
	    var coords = coordStr.split(",");
	    var pt = new google.maps.LatLng(parseFloat(coords[0]),parseFloat(coords[1]));
            bounds.extend(pt);
            marker = new google.maps.Marker({         
                            position: pt,         
                            map: map,
			    icon: locations[i][5],
                            address: locations[i][2],
                            title: locations[i][0],
                            html: locations[i][0]+"<br>"+locations[i][2]+"<br><br><a href='javascript:getDirections(customerMarker.getPosition(),&quot;"+locations[i][2]+"&quot;);'>Get Directions</a>"
                            });                              
            gmarkers.push(marker);
            google.maps.event.addListener(marker, 'click', (function(marker, i) {         return function() 
            {           infowindow.setContent(marker.html);
                        infowindow.open(map, marker);         
            }       
        })
        (marker, i));     
    }*/
    //map.fitBounds(bounds);   

}

var fbID = "" ;

function getFBUserId()
{
	
	jQuery.ajax({
        type: "POST",
        url: "http://localhost:8080/fbUserId",
        data: "",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
        	
        	fbID = data;
        	return data;
        },

        error: function (jqXHR, status, textStatus) {
        // error handler

        alert("error occured");
		return null;
        }

        });


	
	
}

      function codeAddress() {
    	  
    	  document.getElementById('pics').style.display = 'none';
    	  document.getElementById('noImage').style.display = 'none';	
    	  
    	  
        var address = document.getElementById('address').value;
        geocoder.geocode( { 'address': address}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
             map.setZoom(9);
            
            
	    if (customerMarker) customerMarker.setMap(null);
            customerMarker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
 		var marker;
            var infowindow = new google.maps.InfoWindow();
	    /* closest = findClosestN(results[0].geometry.location,10);
            
            closest = closest.splice(0,3);
            calculateDistances(results[0].geometry.location, closest,3) */;
            getFBUserId();  
          var res = String(results[0].geometry.location).split(",");
          res[0] = res[0].replace("(","").trim();
          res[1] = res[1].replace(")","").trim();
          console.log("-" + res[0] + "----" + res[1] + "-");
            jQuery.ajax({
                type: "POST",
                url: "http://localhost:8080/placePhoto",
                data: JSON.stringify(results[0].geometry.location),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data, status, jqXHR) {
                		alert("Successfully retrieved Photos");
                	maxIndex = data.length;
                	for(var i=0; i< data.length; i++)
                	{
                		
                		imageData[i][0] = "\\resources\\img\\downloadedImages\\" + fbID + "\\place-" + res[0] + "-" + res[1] + "\\" + data[i]["photoId"] + ".jpg";
                		imageData[i][1] = "<table> <tr> <td> <b>Friend name</b>: " + data[i]["friendName"] + ", <b>At</b>: " + data[i]["name"] + "</td></tr>";
                		
                		imageData[i][1] += "<tr><td> <b>Address</b>: " + data[i]["street"] + ", " + data[i]["city"] + ", " + data[i]["country"] + ", " + data[i]["zip"] + "</td></tr>";
                		
						imageData[i][2] = "<table> <tr> <td> <b>Friend name</b>: " + data[i]["friendName"] + ", <b>At</b>: " + data[i]["name"] + "</td></tr>";
                		
                		imageData[i][2] += "<tr><td> <b>Address</b>: " + data[i]["street"] + ", " + data[i]["city"] + ", " + data[i]["country"] + ", " + data[i]["zip"] + "</td></tr>";
                		
                		                	    var pt = new google.maps.LatLng(data[i]["latitude"],data[i]["longitude"]);
                            //bounds.extend(pt);
                            marker = new google.maps.Marker({         
                                            position: pt,         
                                            map: map,
                			    icon: "http://maps.google.com/mapfiles/ms/icons/blue.png",
                                            address: data[i]["name"],
                                            title: data[i]["name"],
                                            html: imageData[i][1]
                                            });                              
                            gmarkers.push(marker);
                            google.maps.event.addListener(marker, 'click', (function(marker, i) {         return function() 
                            {           infowindow.setContent(marker.html);
                                        infowindow.open(map, marker);         
                            }       
                        })
                        (marker, i));     

                	}
                	
                	if(maxIndex > 0)
                	{
                		document.getElementById('pics').style.display = 'block';
                		document.getElementById('noImage').style.display = 'none';
                	}
                	else
                	{
                		document.getElementById('noImage').style.display = 'block';
                		document.getElementById('pics').style.display = 'none';
                	}
                	
                	
                	
                },

                error: function (jqXHR, status, textStatus) {
                // error handler

                alert("error occured");

                }

                });
          
          
          
          
          } else {
            alert('Error occured for the following reason: ' + status);
          }
        });
      }

function findClosestN(pt,numberOfResults) {
   var closest = [];
   document.getElementById('info').innerHTML += "processing "+gmarkers.length+"<br>";
   for (var i=0; i<gmarkers.length;i++) {
     gmarkers[i].distance = google.maps.geometry.spherical.computeDistanceBetween(pt,gmarkers[i].getPosition());
     document.getElementById('info').innerHTML += "process "+i+":"+gmarkers[i].getPosition().toUrlValue(6)+":"+gmarkers[i].distance.toFixed(2)+"<br>";
     gmarkers[i].setMap(null);
     closest.push(gmarkers[i]);
   }
   closest.sort(sortByDist);
   return closest;
}

function sortByDist(a,b) {
   return (a.distance- b.distance)
}
     
function calculateDistances(pt,closest,numberOfResults) {
  var service = new google.maps.DistanceMatrixService();
  var request =    {
      origins: [pt],
      destinations: [],
      travelMode: google.maps.TravelMode.DRIVING,
      unitSystem: google.maps.UnitSystem.METRIC,
      avoidHighways: false,
      avoidTolls: false
    };
  for (var i=0; i<closest.length; i++) request.destinations.push(closest[i].getPosition());
  service.getDistanceMatrix(request, function (response, status) {
    if (status != google.maps.DistanceMatrixStatus.OK) {
      alert('Error was: ' + status);
    } else {
      var origins = response.originAddresses;
      var destinations = response.destinationAddresses;
      var outputDiv = document.getElementById('side_bar');
      outputDiv.innerHTML = '';

      var results = response.rows[0].elements;
      for (var i = 0; i < numberOfResults; i++) {
        closest[i].setMap(map);
        outputDiv.innerHTML += "<a href='javascript:google.maps.event.trigger(closest["+i+"],\"click\");'>"+closest[i].title + '</a><br>' + closest[i].address+"<br>"
            + results[i].distance.text + ' appoximately '
            + results[i].duration.text + '<br><hr>';
      }
    }
  });
}


function mail() {
	
    	jQuery.ajax({
        type: "POST",
        url: "http://localhost:8080/mail",
        data:"",
        contentType: "application/json; charset=utf-8",
        dataType: "text",
        success: function (data, status, jqXHR) {
             alert(status);
             alert("mail send");
  
        },
    
        error: function (jqXHR, status, textStatus) {            
             // error handler
        	 
             alert("error");
             window.location.href="/Error"
        }

    });

}
function getDirections(origin, destination) {
  var request = {
      origin:origin,
      destination:destination,
      travelMode: google.maps.DirectionsTravelMode.DRIVING
  };
  directionsService.route(request, function(response, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setMap(map);
      directionsDisplay.setDirections(response);
      directionsDisplay.setPanel(document.getElementById('side_bar'));
    }
  });
}

google.maps.event.addDomListener(window, 'load', initialize);
</script> 
   </head>

            <body>
<table >
<tr>
	<td>
	<h1 align="left">
	<b>Search for a Place on the Map</b>
	</h1>
	 <input id="address" type="text" value="Palo Alto, CA"></input>
	<input type="button" value="Search" onclick="codeAddress();"></input>
     <br />
     <div id="info"></div>
	<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"> 
	</script> 
	<script type="text/javascript"> 
	_uacct = "UA-162157-1";
	urchinTracker();
	</script> 
     <div id="map" style="height: 500px; width:600px;"></div>
</td>
<td>
<div id="noImage" align="left" style="display:none">
No Images to display
</div>
<div id="pics" align="left" style="display:none"> 
<table border="0"  cellspacing="0" width="900px">
				
				    <tr>
				    	<td valign="top">
						<div align="right" style="float: left;">
						<a href="${pageContext.request.contextPath}/success"><img src="${pageContext.request.contextPath}/resources/images/back-button1.png" border="0"></a> 
						<a href="${pageContext.request.contextPath}/Logout"><img src="${pageContext.request.contextPath}/resources/images/logout-button-blue.png" border="0"></a> 	
						</div>
						</td>
				
				        <td align="center" colspan="6" style="padding-right: 100px; padding-left: 100px; color: white;" id="imageDescriptionCell">
				
				        </td>
				
				    </tr>
				
				    <tr>
				
				        <td align="center" colspan="6" >
				
				            <img height="500" src="${pageContext.request.contextPath}/resources/images/loading3.gif" style="border-right: 1px solid; border-top: 1px solid; border-left: 1px solid;
				
				border-bottom: 1px solid" width="500" id="imageLarge" alt="default" /></td>
				
				    </tr>
				
				    <tr>
				
				<td  align="center" colspan="6" style="font-weight: bold; font-size: 18pt; color: silver;" id="imageTitleCell">
				
				            </td>
				        
				
				    </tr>
				    <tr>
				
				        <td id="scrollPreviousCell" style="color: Silver" onmouseover="scrollPrevious();" onmouseout="scrollStop();">
				
				            &lt;&lt; Previous</td>
				
				        <td>
				
				            <img id="scrollThumb1" height="100" src="${pageContext.request.contextPath}/resources/images/loadingthumb.gif" style="border-right: 1px solid; border-top: 1px solid; border-left: 1px solid;
				
				border-bottom: 1px solid" width="100" onmouseover="handleThumbOnMouseOver(0);" /></td>
				        <td>
				
				            <img id="scrollThumb2" height="100" src="${pageContext.request.contextPath}/resources/images/loadingthumb.gif" style="border-right: 1px solid; border-top: 1px solid; border-left: 1px solid;
				
				border-bottom: 1px solid" width="100" onmouseover="handleThumbOnMouseOver(1);" /></td>
				       <td>
				
				            <img id="scrollThumb3" height="100" src="${pageContext.request.contextPath}/resources/images/loadingthumb.gif" style="border-right: 1px solid; border-top: 1px solid; border-left: 1px solid;
				
				border-bottom: 1px solid" width="100" onmouseover="handleThumbOnMouseOver(2);" /></td>
				
				        <td>
				
				            <img id="scrollThumb4" height="100" src="${pageContext.request.contextPath}/resources/images/loadingthumb.gif" style="border-right: 1px solid; border-top: 1px solid; border-left: 1px solid;
				
				border-bottom: 1px solid" width="100" onmouseover="handleThumbOnMouseOver(3);" /></td>
				
				        <td id="scrollNextCell" style="color: Black" onmouseover="scrollNext();" onmouseout="scrollStop();">
				
				            Next &gt;&gt;</td>
				
				    </tr>
		
				    
				</table>
</div>
</td>
<td valign="top">
<div align="left" style="float: left;">
						<a href="${pageContext.request.contextPath}/success"><img src="${pageContext.request.contextPath}/resources/images/Home1.png" border="0" width="100" height="30"></a> 
</div>
</td>
<td valign="top">
<div align="right" style="float: right;">
<a href="${pageContext.request.contextPath}/Logout"><img src="${pageContext.request.contextPath}/resources/images/logout1.jpeg" border="0" width="100" height="30"></a> 	
</div>
</td>
<a href="${pageContext.request.contextPath}/maps" onclick="mail();"><img src="${pageContext.request.contextPath}/resources/images/email.jpeg" border="0" width="60" height="60"></a>
</tr></table>
   </body>
 </html>