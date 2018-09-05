$(document).ready(function() {
    var id = getUrlVars()["test-id"]

    var url = $('[receiver-rate-graph]').attr('graph-api') + id

    axios.get(url).then(function (response) {
        var chartData = response.data

        var c3ChartDefaults = $().c3ChartDefaults();
        var lineChartConfig = c3ChartDefaults.getDefaultLineConfig();
        lineChartConfig.bindto =  '#line-chart-5';

        // Latency distributions per test
        lineChartConfig.data = {
            json: chartData,
            keys: {
                x: 'Test Number', // it's possible to specify 'x' when category axis
                value: ['Rate Geometric Mean']
            },
            axis: {
                x: {
                    type: 'category',
                    label: {
                        text: 'Rate',
                    },
                },
                y: {
                    label: {
                        text: 'Test Iteration',
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



