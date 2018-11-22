var extraEnvResultsColumns = [
    { data: "envResultsId" },
    { data: "envResourceId" },
    {
        data: "testId",
        render: renderTestId
    },
    { data: "testNumber" },
    { data: "envName"},
    { data: "envResourceRole"},
    {
        data: "testRateMin",
        render: rateRender
    },
    {
        data: "testRateMax",
        render: rateRender
    },
    { data: "testRateErrorCount"},
    { data: "testRateSamples"},
    {
        data: "testRateGeometricMean",
        render: renderRoundedRate
    },
    {
        data: "testRateStandardDeviation",
        render: renderRounded
    },
    { data: "testRateSkipCount"},
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
    { data: "error"},
];