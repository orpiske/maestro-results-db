$(document).ready(function() {
    var tId0 = getUrlVars()["t0"]
    var tNum0 = getUrlVars()["n0"]
    var tId1 = getUrlVars()["t1"]
    var tNum1 = getUrlVars()["n1"]

    var url = $('[graphs]').attr('graph-api') + tId0 + '/' + tNum0 + '/' + tId1 + '/' + tNum1 + '/'

//    axios.get(url).then(function (response) {
//        var chartData = response.data
//
//        var c3ChartDefaults = $().c3ChartDefaults();
//        var lineChartConfig = c3ChartDefaults.getDefaultLineConfig();
//        lineChartConfig.bindto = '#line-chart-3';
//
//        // Latency distributions per test
//        lineChartConfig.data = {
//            json: chartData,
//            keys: {
//                x: 'Test Number', // it's possible to specify 'x' when category axis
//                value: ['90th percentile']
//            },
//            axis: {
//                x: {
//                    type: 'category',
//                    label: {
//                        text: 'Latency percentiles',
//                    },
//                },
//                y: {
//                    label: {
//                        text: 'Milliseconds',
//                    }
//                }
//            }
//        };
//
//        var lineChart = c3.generate(lineChartConfig);
//
//      })
//      .catch(function (error) {
//        console.log(error);
//      });

    axios.get(url).then(function (response) {
        var chartData = response.data

        var c3ChartDefaults = $().c3ChartDefaults();
        var lineChartConfig = c3ChartDefaults.getDefaultGroupedBarConfig();
        lineChartConfig.bindto = '#line-chart-3';

        // Latency distributions per test
        lineChartConfig.data = {
            json: chartData.Pairs,
            keys: {
                value: ['90th percentile', '95th percentile', '99th percentile']
            },
            type: 'bar',
            groups: [
                ['90th percentile', '95th percentile', '99th percentile']
            ],
        };

        lineChartConfig.legend = {
            position: 'right'
        }

        lineChartConfig.axis = {
            x: {
                            type: 'category',
                            categories: chartData.Categories
            },
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