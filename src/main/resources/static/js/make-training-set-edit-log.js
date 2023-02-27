let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
let headerSetting = {};
headerSetting[header] = token;
let trainingLogId = $("#trainingLogId").val();

$(document).ready(function() {
	$.ajax({
		url: "/rest/editlog", // 通信先のURL
		headers: headerSetting,
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify({ id: trainingLogId }),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "json" // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(data) {
		for (let i = 0; i < data.trainingSetList.length; i++) {
			let trainingSet = data.trainingSetList[i];
			if (i!=0) {
				addTrainingSetRow();
			}
			
			$('#trainingSetListBody tr:last-child').find(".trainingSetWeight").val(trainingSet.weight);
			$('#trainingSetListBody tr:last-child').find(".trainingSetRep").val(trainingSet.rep);
			$('#trainingSetListBody tr:last-child').find(".trainingSetOneRepMax").text(trainingSet.oneRepMax);
			$('#trainingSetListBody tr:last-child').find(".trainingSetOneRepMaxForm").val(trainingSet.oneRepMax);

		}

	}).fail(function() { console.log("fail") });
});
