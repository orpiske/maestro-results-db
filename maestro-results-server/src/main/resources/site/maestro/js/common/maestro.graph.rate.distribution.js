function rateDistributionGraph(id, url, element, value) {
    axios.get(url).then(function (response) {
        var chartData = response.data

        var c3ChartDefaults = $().c3ChartDefaults();
        var lineChartConfig = c3ChartDefaults.getDefaultLineConfig();
        lineChartConfig.bindto =  element;

        // Latency distributions per test
        lineChartConfig.data = {
            json: chartData,
            keys: {
                x: 'Test Number',
                value: value
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





