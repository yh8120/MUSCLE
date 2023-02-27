let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
let headerSetting = {};
headerSetting[header] = token;
let trainingId = $("#barChart").data("id");

let barCtx = document.getElementById("barChart");
let barType = 'line';
let barOptions = {
	plugins: {
		// グラフタイトル
		title: {
			display: false,
			text: '日毎挙上重量',
			color: 'white',
			padding: { top: 5, bottom: 20 },
			font: {
				family: 'serif, sans-serif',
				size: 24,
			},
		},
		// 凡例
		legend: {
			position: 'top',
			align: 'center',
			// 凡例ラベル
			labels: {
				color: 'white',
				font: {
					family: 'serif, sans-serif',
					size: 14,
				},
				boxWidth: 20,
				boxHeight: 8,
			},
			// 凡例タイトル
			title: {
				display: false,
				text: '部位',
				color: 'white',
				font: {
					family: 'serif, sans-serif',
					size: 18,
				},
				padding: { top: 40 },
			},
		},
		// ツールチップ
		tooltip: {
			backgroundColor: '#933',
		},
	},
	scales: {
		y: {
			// 最小値・最大値
			min: 0,
			title: {
				display: true,
				text: '挙上重量(kg)',
				color: 'white',
				font: {
					family: 'serif, sans-serif',
					size: 18,
				},
				padding: {bottom: 10},
			},
			ticks: {
				color: 'white',
				align: 'end',
				crossAlign: 'center',
				font: {
					family: 'serif, sans-serif',
					size: 12,
				},
				padding: 14,
			},
			grid: {
				// 目盛線＆グリッド線
				color: 'rgb(1, 51, 77)',
				display: true,
			},
		},
		x: {
			ticks: {
				color: 'white',
				align: 'center',
				crossAlign: 'center',
				font: {
					family: 'serif, sans-serif',
					size: 12,
				},
				padding: 20,
			},
			grid: {
				// 目盛線＆グリッド線
				color: 'rgb(1, 51, 77)',
				display: true,
			},
			type: 'time',
			time: {
				unit: 'day',
				displayFormats: {
					'day': 'M月D日'
				}
			},

		},
	},
	maintainAspectRatio: false,
};


let datasets = [{
	label: '1RM',
	data: [],
	borderColor: 'rgb(130, 18, 41)',
},
{
	label: '最大重量',
	data: [],
	borderColor: 'rgb(183, 78, 0)',
},
];

$(document).ready(function() {

	$.ajax({
		url: "/rest/logchart", // 通信先のURL
		headers: headerSetting,
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify({ id: trainingId }),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "json" // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(data) {
		let label = data.map(log => log.registered);

		datasets[0].data = data.map(log => log.oneRepMax);
		datasets[1].data = data.map(log => log.maxWeight);
		let barData = {};
		barData.labels = label;
		barData.datasets = datasets;

		let barChart = new Chart(barCtx, {
			type: barType,
			data: barData,
			options: barOptions,
		});


	}).fail(function() { console.log("fail") });


});