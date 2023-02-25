$(document).ready(function() {
	$('.chart-button').click(function(e) {
		$("#g-nav").toggleClass("panelactive");
	});
	$(window).resize(function() {
		if ($(window).width() > 992) {
			$("#g-nav").removeClass("panelactive");
		}
	});
});
