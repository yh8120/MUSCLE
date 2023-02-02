function setCurrentLocation() {
	navigator.geolocation.getCurrentPosition(setLocation);

}
function setLocation(position) {

	$(".trainingLogLatitude").val(position.coords.latitude);
	$(".trainingLogLogitude").val(position.coords.longitude);
}

$(document).ready(function() {
	
	setCurrentLocation();
});
