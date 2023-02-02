var stompClient = null;
let userId = $("#userId").val();


console.log("userid:" + userId); ////////////////////////


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
function addProtein(trainingLogId) {
	const data = {
		tid: trainingLogId,
		uid: uid = userId
	}

	$.ajax({
		url: "http://localhost:8080/rest/addProtein", // 通信先のURL
		type: "PUT", // 使用するHTTPメソッド
		data: JSON.stringify(data),
		// dataType:"json", // 応答のデータの種類
		dataType: "text", // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(res) {
		console.log(res);
		$(`.trl${trainingLogId}`).off();
		$(`.trl${trainingLogId}`).on("click", delProtein(payload.trainingLogId));
		$(`.trl${trainingLogId}`).attr("src", "/images/icon/protein.png");
		alert("プロテインを送りました♪")
	}).fail(alert("プロテインは送れませんでした…"))
}

function delProtein(trainingLogId) {
	const data = {
		tid: trainingLogId,
		uid: uid = userId
	}
	$.ajax({
		url: "http://localhost:8080/rest/delProtein", // 通信先のURL
		type: "PUT", // 使用するHTTPメソッド
		data: JSON.stringify(data),
		// dataType:"json", // 応答のデータの種類
		dataType: "text", // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(res) {
		console.log(res);
		$(`.trl${trainingLogId}`).off();
		$(`.trl${trainingLogId}`).on("click", addProtein(payload.trainingLogId));
		$(`.trl${trainingLogId}`).attr("src", "/images/icon/proteinAdded.png");
		alert("プロテインを返してもらいました")
	}).fail(alert("プロテインを返してもらえませんでした…"))
}

function showGreeting(payload) {
	if (payload.userId == userId) {
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
		if (payload.contributorList.includes(userId)) {
			cloneRow.find(".noticeButton").on("click", function(){				
			delProtein(payload.trainingLogId)
			});
			cloneRow.find(".noticeButton").attr("src", "/images/icon/proteinAdded.png");
		} else {
			cloneRow.find(".noticeButton").on("click", function(){
				
			addProtein(payload.trainingLogId)
			});
			cloneRow.find(".noticeButton").attr("src", "/images/icon/protein.png");
		}

		console.log(cloneRow.find(".noticeButton"))///////////////////////////////////////

		$("#noticeTable")
			.prepend(cloneRow)
			.hide()
			.fadeIn(500);
	}
}

setTimeout("connect()", 1500);