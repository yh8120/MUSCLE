var linectx = document.getElementById('lineChart');
var lineChart = new Chart(linectx, {
  type: 'line',
  data: {
    labels: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    datasets: [{
      label: 'Red',
      data: [20, 35, 40, 30, 45, 35, 40],
      // データライン
      borderColor: '#f88',
    }, {
      label: 'Green',
      data: [20, 15, 30, 25, 30, 40, 35],
      borderColor: '#484',
    }, {
      label: 'Blue',
      data: [30, 25, 10, 5, 25, 30, -20],
      borderColor: '#48f',
    }],
  },
  options: {
    plugins: {
      // グラフタイトル
      title: {
        display: true,
        text: 'Sample Chart',
        color: 'black',
        padding: {top: 5, bottom: 5},
        font: {
          family: '"Arial", "Times New Roman"',
          size: 12,
        },
      },
      // 凡例
      legend: {
        position: 'right',
        align: 'start',
        // 凡例ラベル
        labels: {
          boxWidth: 20,
          boxHeight: 8,
        },
        // 凡例タイトル
        title: {
          display: true,
          text: 'Legend',
          padding: {top: 20},
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
        max: 60,
        // 軸タイトル
        title: {
          display: true,
          text: 'Scale Title',
          color: 'blue',
        },
        // 目盛ラベル
        ticks: {
          color: 'blue',
          stepSize: 20,
          showLabelBackdrop: true,
          backdropColor: '#ddf',
          backdropPadding: { x: 4, y: 2 },
          major: {
            enabled: true,
          },
          align: 'end',
          crossAlign: 'center',
          sampleSize: 4,
        },
        grid: {
          // 軸線
          borderColor: 'orange',
          borderWidth: 2,
          drawBorder: true,
          // 目盛線＆グリッド線
          color: '#080',
          display: true,
          // グリッド線
          borderDash: [3, 3],
          borderDashOffset: 0,
          // 目盛線
          drawTicks: true,
          tickColor: 'blue',
          tickLength: 10,
          tickWidth: 2,
          tickBorderDash: [2, 2],
          tickBorderDashOffset: 0,
        },
      },
      x: {
        grid: {
          borderColor: 'orange',
          borderWidth: 2,
        },
      },
    },
	maintainAspectRatio: false,
  },
});


let barCtx = document.getElementById("barChart");
let barType = 'bar';
let barData = {
	labels: ['Sun', 'Mon', 'Tue', 'Wed', 'The', 'Fri', 'Sut'],
	datasets: [{
		label: '胸',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(130, 18, 41)',
		stack: 'stack-1',
	}, {
		label: '肩',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(183, 78, 0)',
		stack: 'stack-1',
	}, {
		label: '腕',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(93, 145, 67)',
		stack: 'stack-1',
	}, {
		label: '背中',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(93, 145, 187)',
		stack: 'stack-1',
	}, {
		label: '腹',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(93, 86, 187)',
		stack: 'stack-1',
	}, {
		label: '脚',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(1, 51, 77)',
		stack: 'stack-1',
	}, {
		label: 'その他',
		data: [20, 35, 40, 20, 85, 63, 10],
		backgroundColor: 'rgb(170, 170, 170)',
		stack: 'stack-1',
	}],
};
let barOptions = {
	title: {              // タイトルの設定
		display: true,             // ★必須　表示設定 省略時は false
		position: "bottom",        // 表示位置 省略時は top、他に left, right が指定できる
		fontSize: 14,              // フォントサイズ 省略時は 12
		fontColor: "red",        // 文字の色 省略時は "#666"
		fontStyle: "bold",         // フォントタイプ 省略時は "bold"
		fontFamily: "sans-serif",  // フォントファミリ 省略時は "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif"
		text: 'タイトル'           // ★必須　タイトルの文字列
	},
	tooltips: {
		mode: 'label' //マウスオーバー時に表示されるtooltip
	},
	maintainAspectRatio: false,
};

let barChart = new Chart(barCtx, {
	type: barType,
	data: barData,
	options: barOptions,
});