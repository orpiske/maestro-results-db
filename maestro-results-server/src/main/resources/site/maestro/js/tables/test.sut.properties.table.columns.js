var dbColumns = [
    { data: null,
      className: "table-view-pf-select",
      render: function (data, type, full, meta) {
        // Select row checkbox renderer
        var id = "select" + meta.row;
        return '<label class="sr-only" for="' + id + '">Select row ' + meta.row +
          '</label><input type="checkbox" id="' + id + '" name="' + id + '">';
      },
      sortable: false
    },
    {
        data: "testId",
        render: function (data, type, full, meta) {
            return '<a href=\"view-single-test-results.html?test-id=' + data + '\">' + data + '</a>';
        }
    },
    { data: "testNumber" },
    { data: "sutName" },
    { data: "sutVersion" },

    { data: "testName" },
    {
        data: "testResult",
        render: resultRender
    },


    { data: "apiName" },
    { data: "apiVersion" },

    { data: "messagingProtocol" },

    { data: "connectionCount" },
    {
        data: "testTargetRate",
        render: rateRender
    },

    { data: "durable" },
    { data: "limitDestinations" },
    { data: "messageSize" },
    {
        data: "maxAcceptableLatency",
        function (data, type, full, meta) {
            return  '<span> ' + data + ' ms</span>'
        }
    },
    {
        data: "testDate",
        render: simpleDateRender
    },
    { data: "sutTags" },
    { data: "testTags" }
];