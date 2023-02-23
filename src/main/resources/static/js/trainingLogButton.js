
$(document).ready(function() {
	$('tr.trainingLog').click(function(e) {
	console.log(e);
		window.location.href = "/training/log/edit/"+$(this).data("id");
	});
});
