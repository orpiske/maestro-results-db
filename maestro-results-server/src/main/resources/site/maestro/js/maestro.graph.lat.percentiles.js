$(document).ready(function() {
    var id = getUrlVars()["test-id"]

    var url = $('[graphs]').attr('graph-api') + id

    axios.get(url).then(function (response) {
        var chartData = response.data

        console.log(chartData);

        var c3ChartDefaults = $().c3ChartDefaults();
        var lineChartConfig = c3ChartDefaults.getDefaultLineConfig();
        lineChartConfig.bindto = '#line-chart-3';

        // Latency distributions per test
        lineChartConfig.data = {
            json: chartData,
            keys: {
                x: 'Test Number', // it's possible to specify 'x' when category axis
                value: ['90th percentile', '95th percentile', '99th percentile']
            },
            axis: {
                x: {
                    type: 'category',
                    label: {
                        text: 'Latency percentiles',
                    },
                },
                y: {
                    label: {
                        text: 'Milliseconds',
                    }
                }
            }
        };

        var lineChart = c3.generate(lineChartConfig);

      })
      .catch(function (error) {
        console.log(error);
      });


    }
)



