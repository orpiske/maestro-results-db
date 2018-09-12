$(document).ready(function() {
    var id = getUrlVars()["test-id"]

    var url = $('[graphs]').attr('graph-api') + id

    axios.get(url).then(function (response) {
        var chartData = response.data

        var c3ChartDefaults = $().c3ChartDefaults();
        var lineChartConfig = c3ChartDefaults.getDefaultGroupedBarConfig();
        lineChartConfig.bindto = '#bar-chart-3';

        // Latency distributions per test
        lineChartConfig.data = {
            json: chartData,
            keys: {
                value: ['90th percentile', '95th percentile', '99th percentile']
            },
            type: 'bar',
            groups: [
                ['Test Number']
            ],
        };

        lineChartConfig.axis = {
           y: {
               label: {
                   text: 'Milliseconds',
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



