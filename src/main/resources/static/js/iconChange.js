$(document).ready(function() {
	let iconPath = "/images/profile/" + $("#iconPath").val()
		$("#iconImage").attr("src", iconPath);

	$("#iconPath").change(function() {
		let iconPath = "/images/profile/" + $(this).val();
		$("#iconImage").attr("src", iconPath);
	})
});