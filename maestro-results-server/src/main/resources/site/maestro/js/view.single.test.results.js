$(document).ready(function () {
    var id = getUrlVars()["test-id"]
    var url = $('[stats]').attr('graph-api') + id
    var element = "#test-statistics";

    testStatisticsDonutGraph(id, url, element)
}
)