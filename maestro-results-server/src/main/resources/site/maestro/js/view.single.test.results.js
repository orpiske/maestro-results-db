// Test statistics donut graph
$(document).ready(function () {
    var id = getUrlVars()["test-id"]
    var url = $('[stats]').attr('graph-api') + id
    var element = "#test-statistics";

    testStatisticsDonutGraph(id, url, element)
}
)

// Rate distribution graphs
$(document).ready(
    function() {
        var id = getUrlVars()["test-id"]
        var url = $('[receiver-rate-graph]').attr('graph-api') + id
        var element = '#line-chart-5';
        var value = ['Rate Geometric Mean', 'Combined Target Rate'];

        rateDistributionGraph(id, url, element, value)
    }
)

$(document).ready(
    function() {
        var id = getUrlVars()["test-id"]
        var url = $('[sender-rate-graph]').attr('graph-api') + id
        var element = '#line-chart-4';
        var value = ['Rate Geometric Mean', 'Combined Target Rate'];

        rateDistributionGraph(id, url, element, value)
    }
)