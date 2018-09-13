function testStatisticsDonutGraph(id, url, element) {
    axios.get(url).then(function (response) {
        var chartData = response.data

        var c3ChartDefaults = $().c3ChartDefaults();
        var donutChartRightConfig = c3ChartDefaults.getDefaultRelationshipDonutConfig();
        donutChartRightConfig.bindto = element;
        donutChartRightConfig.tooltip = {show: true};
        donutChartRightConfig.data = {
            type: 'donut',

            columns: [
                ['Success', chartData.success],
                ['Failures', chartData.failures],
            ],
        };
        donutChartRightConfig.legend = {
            show: true,
            position: 'right'
        };

        donutChartRightConfig.size = {
            width: 251,
            height: 161
        };
        donutChartRightConfig.tooltip = {
            contents: $().pfDonutTooltipContents
        };

        var donutChartRightLegend = c3.generate(donutChartRightConfig);
        $().pfSetDonutChartTitle(element, chartData.success, "Successful Tests");

      })
      .catch(function (error) {
        console.log(error);
      });
}






