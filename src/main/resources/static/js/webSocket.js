var stompClient = null;
let ounId = $("#userId").val();

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
}

function connect() {
	var socket = new SockJS('/endpoint');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(payload) {
			showGreeting(JSON.parse(payload.body));
		});
	});
}
function addProtein(trainingLogId,userId) {
	const data = {
		tid: trainingLogId,
		uid: userId
	}
	$.ajax({
		url: "http://localhost:8080/rest/add", // 通信先のURL
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify(data),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "text", // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(res) {
		console.log(res);
		$(`.trl${trainingLogId}`).off();
		$(`.trl${trainingLogId}`).on("click", function() { delProtein(trainingLogId,userId) });
		$(`.trl${trainingLogId}`).attr("src", "/images/icon/proteinAdded.png");
		console.log($(`.trl${trainingLogId}`))
	}).fail(console.log("fail"))
}

function delProtein(trainingLogId,userId) {
	const data = {
		tid: trainingLogId,
		uid: userId
	}
	$.ajax({
		url: "http://localhost:8080/rest/del", // 通信先のURL
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify(data),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "text", // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(res) {
		console.log(res)
		$(`.trl${trainingLogId}`).off();
		$(`.trl${trainingLogId}`).on("click", function() { addProtein(trainingLogId,userId) });
		$(`.trl${trainingLogId}`).attr("src", "/images/icon/protein.png");
	}).fail(console.log("fail"))
}

function showGreeting(payload) {
	if (payload.userId != ounId) {
		const cloneRow = $($('#noticeRow').html());

		//make .notice icon
		cloneRow.find(".noticeIcon").attr("src", "/images/profile/" + payload.iconPath);

		//make .noticeMsessage
		let message = payload.trainingName + ":";
		const trainingSetList = payload.trainingSetList;
		for (let i = 0; i < trainingSetList.length; i++) {
			let set = trainingSetList[i];
			message += set.weight + "kg×" + set.rep + "rep"
			if (i != trainingSetList.length - 1) {
				message += ",";
			}
		}


		cloneRow.find(".noticeMessage").text(message);
		//make .noticeButton
		cloneRow.find(".noticeButton").addClass("trl" + payload.trainingLogId);
		if (payload.contributorList.includes(ounId)) {
			cloneRow.find(".noticeButton").on("click", function() {
				delProtein(payload.trainingLogId,payload.userId)
			});
			cloneRow.find(".noticeButton").attr("src", "/images/icon/proteinAdded.png");
		} else {
			cloneRow.find(".noticeButton").on("click", function() {
				addProtein(payload.trainingLogId,payload.userId)
			});
			cloneRow.find(".noticeButton").attr("src", "/images/icon/protein.png");
		}

		$("#noticeTable")
			.prepend(cloneRow)
			.hide()
			.fadeIn(500);
	}
}

setTimeout("connect()", 500);