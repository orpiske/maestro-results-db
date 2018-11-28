var extraSingleTestResultColumns = [
    { data: "testResult",
        render: resultRender
    },
    { data: "error" },
    { data: "envResourceName" },
    { data: "envResourceRole" },
    {
        data: "testRateMin",
        render: rateRender
    },
    {
        data: "testRateMax",
        render: rateRender
    },
    {
        data: "testRateStandardDeviation",
        render: renderRounded
    },
    {
        data: "testRateGeometricMean",
        render: renderRoundedRate
    },
    {
        data: "testCombinedTargetRate",
        render: rateRender
    },
    {
        data: "testTargetRate",
        render: rateRender
    },
    {
        data: "latPercentile90",
        render: percentileRender
    },
    {
        data: "latPercentile95",
        render: percentileRender
    },
    {
        data: "latPercentile99",
        render: percentileRender
    },
    { data: "messagingProtocol" },
    { data: "connectionCount" },
    { data: "variableSize" },
    { data: "limitDestinations" },
    { data: "messageSize" },
    {
        data: "maxAcceptableLatency",
        render: function (data, type, full, meta) {
            if (full.envResourceRole == "sender") {
                return "N/A"
            }

            return  '<span> ' + data + ' ms</span>'
        }
    },
    { data: "apiName" },
    { data: "apiVersion" },
    {
        data: "testDate",
        render: simpleDateRender
    },
    {
        data: "testReportLink",
        render: renderTestFull
    },
];