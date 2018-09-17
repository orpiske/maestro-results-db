var dbColumns = [
    {
        data: "testId",
        render: renderTestId

    },
    { data: "testNumber" },
    { data: "testResult",
        render: function (data, type, full, meta) {
            if (data == "success") {
                return '<span class="pficon pficon-ok"> ' + data + '</span>'
            }
            else {
                return '<span class="pficon pficon-warning-triangle-o"> ' + data + '</span>'
            }

        }
    },
    { data: "error" },
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
    {
        data: "testDate",
        render: function (data, type, full, meta) {
                return (new Date(data)).toLocaleString();
        }
    },
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