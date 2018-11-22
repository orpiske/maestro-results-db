// Test results info table
$(document).ready(function() {
    var id = getUrlVars()["test-id"]
    var num = "0"

    var sutUrl = "/api/test/" + id + "/sut"
    getSutInfo(sutUrl, '')

    var testInfoUrl = "/api/test/" + id + "/number/" + num;
    getTestInfo(testInfoUrl, '')
})

// Test statistics donut graph
$(document).ready(function () {
    var id = getUrlVars()["test-id"]
    var url = $('[stats]').attr('graph-api') + id
    var element = "#test-statistics";

    testStatisticsDonutGraph(id, url, element)
})

// Env Resources Table
$(document).ready(function () {
    var id = getUrlVars()["test-id"]
    var url = $('[data-envres-datatables]').attr('data-api') + id + "/resources"

    envResourceInfoTable('[data-envres-datatables]', url)
})


// Rate distribution graphs
$(document).ready(
    function() {
        var id = getUrlVars()["test-id"]
        var url = $('[receiver-rate-graph]').attr('graph-api') + id
        var element = '#line-chart-5';
        var value = ['Rate Geometric Mean', 'Combined Target Rate'];

        rateDistributionGraph(url, element, value)
    }
)

$(document).ready(
    function() {
        var id = getUrlVars()["test-id"]
        var url = $('[sender-rate-graph]').attr('graph-api') + id
        var element = '#line-chart-4';
        var value = ['Rate Geometric Mean', 'Combined Target Rate'];

        rateDistributionGraph(url, element, value)
    }
)

// Grouped Percentile bar graph
$(document).ready(
    function() {
        var id = getUrlVars()["test-id"]
        var url = $('[graphs]').attr('graph-api') + id

        var element = '#bar-chart-3';
        var values = ['90th percentile', '95th percentile', '99th percentile'];
        var groups = ['Test Number'];
        var yLabel = 'Milliseconds';

        groupedBarGraph(url, element, values, groups, yLabel)
    }
)

// Data table
$(document).ready(
    function() {
        var id = getUrlVars()["test-id"]
        var url = $('[data-datatables]').attr('data-api') + "/" + id

        maestroDataTable('[data-datatables]', url, extraSingleTestResultColumns)
    }
)