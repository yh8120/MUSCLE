$(document).ready(function() {
	$(".delete").on("click", function(e) {
		e.preventDefault();
		if (confirm('本当に削除してよろしいですか？')) {
			$("#form-delete").submit();
		}
	})

});