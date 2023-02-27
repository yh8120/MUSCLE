let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
let headerSetting = {};
headerSetting[header] = token;
let trainingLogId = $("#trainingLogId").val();

$(document).ready(function() {
	$.ajax({
		url: "/rest/listchart", // 通信先のURL
		headers: headerSetting,
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify({ id: trainingLogId }),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "json" // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(data) {
		console.log(data)




	}).fail(function() { console.log("fail") });



	/*	$('#addTrainingSet').click(addTrainingSetRow);
		$('#removeTrainingSet').click(removeTrainingSetRow);
		$('.trainingSetRep').change(function() {
			const rep = $(this).val();
			const weight = $('.trainingSetWeight').val();
			let oneRepMax = Math.round((weight * rep / 40 + Number(weight)) * 10) / 10;
			$('.trainingSetOneRepMax').text(oneRepMax);
		});
	
		if (!$('#trainingSetListBody tr:last-child .trainingSetOrder').text()) {
			$('#addTrainingSet').trigger("click");
		}*/
});
