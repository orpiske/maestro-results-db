
$(document).ready(function() {
    var dataSet = [];

    axios.get('/api/env/resource')
      .then(function (response) {
        // handle success
        console.log(response);

        dataSet = response.data;

        $('#envrestable').DataTable({
            columns: [
                { data: "envResourceId" },
                { data: "envResourceName" },
                { data: "envResourceOsName" },
                { data: "envResourceOsArch" },
                { data: "envResourceOsVersion"},
                { data: "envResourceOsOther"},
                { data: "envResourceHwName"},
                { data: "envResourceHwModel"},
                { data: "envResourceHwCpu"},
                { data: "envResourceHwRam"},
                { data: "envResourceHwDiskType"},
                { data: "envResourceHwOther"},
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
        tableSelector: "#envrestable"
    });
});