var stompClient = null;
let ounId = parseInt($("#userId").val());

function addProtein(trainingLogId) {
	const data = {
		trainingLogId: trainingLogId,
		userId: ounId
	}
	$.ajax({
		url: "https://localhost:8443/rest/add", // 通信先のURL
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify(data),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "json"
	}).done(function(status) {
		if (status.status == "succses") {
			$(`.trl${trainingLogId}`).off();
			$(`.trl${trainingLogId}`).on("click", function() { delProtein(trainingLogId) });
			$(`.trl${trainingLogId}`).attr("src", "/images/icon/proteinAdded.png").hide().fadeIn(300);
			console.log(status);
		}
	}).fail(function() { console.log("fail") });
}

function delProtein(trainingLogId) {
	const data = {
		trainingLogId: trainingLogId,
		userId: ounId
	}
	$.ajax({
		url: "https://localhost:8443/rest/del", // 通信先のURL
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify(data),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "json" // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(status) {
		if (status.status == "succses") {
			$(`.trl${trainingLogId}`).off();
			$(`.trl${trainingLogId}`).on("click", function() { addProtein(trainingLogId) });
			$(`.trl${trainingLogId}`).attr("src", "/images/icon/protein.png").hide().fadeIn(300);
			console.log(status);
		}
	}).fail(function() { console.log("fail") })
}

function showMessage(notice) {

	if (notice.userId != ounId) {
		const cloneRow = $($('#noticeRow').html());

		//make .notice icon
		cloneRow.find(".noticeIcon").attr("src", "/images/profile/" + notice.iconPath);

		//make .noticeMsessage
		let message = notice.body;
		cloneRow.find(".noticeMessage").text(message);

		if (notice.trainingLogId) {
			cloneRow.find(".noticeButton").addClass("trl" + notice.trainingLogId);
			let conlist = notice.contributorList;
			if (conlist.includes(ounId)) {
				cloneRow.find(".noticeButton").on("click", function() {
					delProtein(notice.trainingLogId)
				});
				cloneRow.find(".noticeButton").attr("src", "/images/icon/proteinAdded.png");
			} else {
				cloneRow.find(".noticeButton").on("click", function() {
					addProtein(notice.trainingLogId)
				});
				cloneRow.find(".noticeButton").attr("src", "/images/icon/protein.png");
			}

		}

		$("#noticeTable")
			.prepend(cloneRow)
			.hide()
			.fadeIn(500);
	}

}

$(function() {
	var socket = new SockJS('/endpoint');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		console.log(frame);
		stompClient.subscribe('/topic/notice', function(notice) {
			showMessage(JSON.parse(notice.body));
		});
		stompClient.subscribe(`/user/${frame.headers['user-name']}/private`, function(notice) {
			showMessage(JSON.parse(notice.body));
		});
	});

});