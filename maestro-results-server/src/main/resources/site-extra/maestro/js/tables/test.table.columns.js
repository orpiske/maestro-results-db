var extraTestTableColumns = [
    {
        data: "testId",
        render: renderTestId
    },
    { data: "testNumber" },
    { data: "testName" },
    {
        data: "testResult",
        render: resultRender
    },
    { data: "testParameterId"},
    { data: "sutId"},
    {
        data: "testReportLink",
        render: function (data, type, full, meta) {
            return '<a href=\"' + data + '\">Link</a>';
        }
    },
    { data: "testDataStorageInfo" },
    { data: "testTags" },
    {
        data: "testDate",
        render: simpleDateRender
    },
    { data: "testDuration"},
    {
        data: "testTargetRate",
        render: rateRender
    },
    { data: "maestroVersion"}
];