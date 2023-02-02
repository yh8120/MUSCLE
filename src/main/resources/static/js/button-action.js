$(document).ready(function() {
	$('.delete').click(function(e) {
		if (!confirm('本当に削除してよろしいですか？')) {
			e.preventDefault();
		}
	});
});
