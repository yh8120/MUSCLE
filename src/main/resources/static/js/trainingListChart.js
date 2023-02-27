let barCtx = document.getElementById("barChart");
let barType = 'bar';
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
				text: '総重量(kg)',
				color: 'white',
				font: {
					family: 'serif, sans-serif',
					size: 18,
				},
				padding: { bottom: 10 },
			},
			ticks: {
				color: 'white',
				align: 'end',
				crossAlign: 'center',
				font: {
					family: 'serif, sans-serif',
					size: 12,
				},
				padding: 10,
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
	label: '胸',
	data: [],
	backgroundColor: 'rgb(130, 18, 41)',
	stack: 'stack-1',
}, {
	label: '肩',
	data: [],
	backgroundColor: 'rgb(183, 78, 0)',
	stack: 'stack-1',
}, {
	label: '腕',
	data: [],
	backgroundColor: 'rgb(93, 145, 67)',
	stack: 'stack-1',
}, {
	label: '背中',
	data: [],
	backgroundColor: 'rgb(93, 145, 187)',
	stack: 'stack-1',
}, {
	label: '腹',
	data: [],
	backgroundColor: 'rgb(93, 86, 187)',
	stack: 'stack-1',
}, {
	label: '脚',
	data: [],
	backgroundColor: 'rgb(1, 51, 77)',
	stack: 'stack-1',
}, {
	label: 'その他',
	data: [],
	backgroundColor: 'rgb(170, 170, 170)',
	stack: 'stack-1',
}];

$(document).ready(function() {
	let exampleData = 1;

	$.ajax({
		url: "/rest/listchart", // 通信先のURL
		headers: headerSetting,
		type: "POST", // 使用するHTTPメソッド
		data: JSON.stringify(exampleData),
		contentType: 'application/json',
		// dataType:"json", // 応答のデータの種類
		dataType: "json" // 応答のデータの種類(xml/html/script/json/jsonp/text)
	}).done(function(data) {
		console.log(data)
		let label = data.map(log => log.registered);
		for (i = 0; i < 7; i++) {
			let hoo = [];
			for (let log of data) {
				let baa = 0;
				for (let part of log.trainingPartList) {
					if (part.id == i + 1) {
						baa = part.totalWeight;
					}
				}
				hoo.push(baa);
			}
			datasets[i].data = hoo;
		}

		console.log(datasets)
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