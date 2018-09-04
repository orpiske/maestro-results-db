var dbColumns = [
    {
        data: "testId",
        render: function (data, type, full, meta) {
            return '<a href=\"view-single-test-results.html?test-id=' + data + '\">' + data + '</a>';
        }
    },
    { data: "testNumber" },
    { data: "sutId" },
    { data: "sutTags" },
    { data: "sutName" },
    { data: "sutVersion" },
    { data: "testResult" },
    { data: "error" },
    { data: "testValid" },
    { data: "envResourceName" },
    { data: "envResourceRole" },
    { data: "testRateMin" },
    { data: "testRateMax" },
    { data: "testRateGeometricMean" },
    { data: "testRateStandardDeviation" },
    { data: "testRateSkipCount" },
    { data: "latPercentile90" },
    { data: "latPercentile95" },
    { data: "latPercentile99" },
    { data: "connectionCount" },
    { data: "testDate" },
    { data: "testName" },
    { data: "testTags" },
    {
        data: "testReportLink",
        render: function (data, type, full, meta) {
            return '<a href=\"' + data + '\">Link</a>';
        }
    },
    { data: "variableSize" }
];