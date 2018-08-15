
$(document).ready(function() {
    var dataSet = [];

    axios.get('/api/env/results')
      .then(function (response) {
        // handle success
        console.log(response);

        dataSet = response.data;

        $('#envresultstable').DataTable({
            columns: [
                { data: "envResultsId" },
                { data: "envResourceId" },
                { data: "testId" },
                { data: "testNumber" },
                { data: "envName"},
                { data: "envResourceRole"},
                { data: "testRateMin"},
                { data: "testRateMax"},
                { data: "testRateErrorCount"},
                { data: "testRateSamples"},
                { data: "testRateGeometricMean"},
                { data: "testRateStandardDeviation"},
                { data: "testRateSkipCount"},
                { data: "connectionCount"},
                { data: "latPercentile90"},
                { data: "latPercentile95"},
                { data: "latPercentile99"},
            ],
            data: dataSet,
            dom: "t",
            language: {
                zeroRecords: "No records found"
            },
            order: [[ 1, 'asc' ]],
            pfConfig: {
                emptyStateSelector: "#emptyState",
                filterCaseInsensitive: true,
                filterCols: [
                    null,
                    {
                        default: true,
                        optionSelector: "#filter",
                        placeholder: "Filter By Name..."
                    }
                ],
//                paginationSelector: "#pagination1",
//                toolbarSelector: "#toolbar1",
//                colvisMenuSelector: '.table-view-pf-colvis-menu'
            }
        });

//        console.log(dataSet);
      })
      .catch(function (error) {
        // handle error
        console.log(error);
      })
      .then(function () {
        // always executed
      });


    var emptyTableViewUtil = function (config) {
        var self = this;

        this.dt = $(config.tableSelector).DataTable(); // DataTable

        // Initialize restore rows
        if (this.dt.data().length === 0) {
            $(this.restoreRows).prop("disabled", false);
        }
    };

    // Initialize empty Table View util
    new emptyTableViewUtil({
        data: dataSet,
        tableSelector: "#envresultstable"
    });
});