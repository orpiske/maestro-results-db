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
    { data: "testResult" },


    { data: "apiName" },
    { data: "apiVersion" },

    { data: "messagingProtocol" },

    { data: "connectionCount" },
    { data: "testTargetRate" },

    { data: "durable" },
    { data: "limitDestinations" },
    { data: "messageSize" },
    { data: "maxAcceptableLatency" },
    {
        data: "testDate",
        render: function (data, type, full, meta) {
                return (new Date(data)).toLocaleString();
        }
    },
    { data: "sutTags" },
    { data: "testTags" }
];