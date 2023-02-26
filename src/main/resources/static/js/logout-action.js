
$(document).ready(function() {
	$('.logout').click(function(e) {
		if (!confirm('ログアウトしてよろしいですか？')) {
			e.preventDefault();
		}
	});
});
