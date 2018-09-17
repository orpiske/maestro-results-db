var dbColumns = [
    {
        data: "testId",
        render: renderTestId
    },
    { data: "testNumber" },
    { data: "testName" },
    { data: "testResult" },
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
    { data: "testDate" },
    { data: "testDuration"},
    { data: "testTargetRate"},
    { data: "maestroVersion"}
];