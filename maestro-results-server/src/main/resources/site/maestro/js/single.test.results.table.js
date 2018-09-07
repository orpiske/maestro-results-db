var dbColumns = [
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
    {
        data: "testRateGeometricMean",
        render: function (data, type, full, meta) {
            return Number(data).toFixed(2);
        }
    },
    {
        data: "testRateStandardDeviation",
        render: function (data, type, full, meta) {
             return Number(data).toFixed(2);
        }
    },
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
    {
        data: "testReportLink",
        render: function (data, type, full, meta) {
            return '<a href=\"' + data + '\">Link</a>';
        }
    },
    { data: "variableSize" }
];