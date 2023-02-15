var stompClient = null;
let ounId = parseInt($("#userId").val());

function addProtein(trainingLogId) {
	const data = {
		tid: trainingLogId,
		uid: ounId
	}
	$.ajax({
		url: "http://localhost:8080/rest/add", // 通信先のURL
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
		}
	}).fail(function() { console.log("fail") });
}

function delProtein(trainingLogId) {
	const data = {
		tid: trainingLogId,
		uid: ounId
	}
	$.ajax({
		url: "http://localhost:8080/rest/del", // 通信先のURL
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
		}
	}).fail(function() { console.log("fail") })
}

function showMessage(payload) {
	if (payload.userId != ounId) {
		const cloneRow = $($('#noticeRow').html());

		//make .notice icon
		cloneRow.find(".noticeIcon").attr("src", "/images/profile/" + payload.iconPath);

		//make .noticeMsessage
		let message = payload.trainingName + ":";
		const trainingSetList = payload.trainingSetList;
		for (let i = 0; i < trainingSetList.length; i++) {
			let set = trainingSetList[i];
			message += set.weight + "kg×" + set.rep
			if (i != trainingSetList.length - 1) {
				message += ",";
			}
		}


		cloneRow.find(".noticeMessage").text(message);
		cloneRow.find(".noticeButton").addClass("trl" + payload.trainingLogId);
		let conlist = payload.contributorList;
		if (conlist.includes(ounId)) {
			cloneRow.find(".noticeButton").on("click", function() {
				delProtein(payload.trainingLogId)
			});
			cloneRow.find(".noticeButton").attr("src", "/images/icon/proteinAdded.png");
		} else {
			cloneRow.find(".noticeButton").on("click", function() {
				addProtein(payload.trainingLogId)
			});
			cloneRow.find(".noticeButton").attr("src", "/images/icon/protein.png");
		}

		$("#noticeTable")
			.prepend(cloneRow)
			.hide()
			.fadeIn(500);
	}
}

$(function(){
	var socket = new SockJS('/endpoint');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/notice', function(payload) {
			showMessage(JSON.parse(payload.body));
		});
		stompClient.subscribe(`/user/${frame.headers['user-name']}/private`, function(payload) {
			showMessage(JSON.parse(payload.body));
		});
	});
	
});