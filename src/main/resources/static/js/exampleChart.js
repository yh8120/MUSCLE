let lineCtx = document.getElementById("lineChart");
// 線グラフの設定
let lineConfig = {
	type: 'line',
	data: {
		// ※labelとデータの関係は得にありません
		labels: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
		datasets: [{
			label: 'Red',
			data: [20, 35, 40, 30, 45, 35, 40],
			borderColor: '#f88',
		}, {
			label: 'Green',
			data: [20, 15, 30, 25, 30, 40, 35],
			borderColor: '#484',
		}, {
			label: 'Blue',
			data: [30, 25, 10, 5, 25, 30, 20],
			borderColor: '#48f',
		}],
	},
	options: {
		scales: {
			// Y軸の最大値・最小値、目盛りの範囲などを設定する
			y: {
				suggestedMin: 0,
				suggestedMax: 60,
				ticks: {
					stepSize: 20,
				}
			}
		},
		maintainAspectRatio: false,
	},
};
let lineChart = new Chart(lineCtx, lineConfig);

let barCtx = document.getElementById("barChart");
let barConfig = {
	type: 'bar',
	data: {
		labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
		datasets: [{
			data: [10, 19, 6, 8, 2, 11],
			label: 'label',
			backgroundColor: [  // それぞれの棒の色を設定(dataの数だけ)
				'#ff0000',
				'#0000ff',
				'#ffff00',
				'#008000',
				'#800080',
				'#ffa500',
			],
			borderWidth: 1,
		}]
	},
	options: {
		maintainAspectRatio: false,
	}
};
let barChart = new Chart(barCtx, barConfig);
