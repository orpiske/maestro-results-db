$(document).ready(function() {
    // JSON data for Table View
    var dataSet = [{
          sutId: "1",
          sutName: "Artemis",
          sutVersion: "2.5.0",
          sutJvmInformation: "OpenJDK",
          sutOther: "4",
          sutTags: "aio,single"
        },
        {
          sutId: "2",
          sutName: "Artemis",
          sutVersion: "2.6.0",
          sutJvmInformation: "OpenJDK",
          sutOther: "4",
          sutTags: "aio,single"
        },
        {
          sutId: "3",
          sutName: "Artemis",
          sutVersion: "2.6.0",
          sutJvmInformation: "OpenJDK",
          sutOther: "4",
          sutTags: "aio,single"
        }
    ];

    $('#rtable').DataTable({
            columns: [
                { data: "sutId" },
                { data: "sutName" },
                { data: "sutVersion" },
                { data: "sutJvmInformation" },
                { data: "sutOther"},
                { data: "sutTags"}
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
                        placeholder: "Filter By Software Under Test..."
                    }
                ],
            },
            select: {
                selector: 'td:first-child input[type="checkbox"]',
                style: 'multi'
            },
    });


    var emptyTableViewUtil = function (config) {
        var self = this;

        this.dt = $(config.tableSelector).DataTable(); // DataTable
        this.deleteRows = $(config.deleteRowsSelector); // Delete rows control
        this.restoreRows = $(config.restoreRowsSelector); // Restore rows control

        // Handle click on delete rows control
        this.deleteRows.on('click', function() {
            self.dt.clear().draw();
            $(self.restoreRows).prop("disabled", false);
        });

        // Handle click on restore rows control
        this.restoreRows.on('click', function() {
            self.dt.rows.add(config.data).draw();
            $(this).prop("disabled", true);
        });

        // Initialize restore rows
        if (this.dt.data().length === 0) {
            $(this.restoreRows).prop("disabled", false);
        }
    };

    // Initialize empty Table View util
    new emptyTableViewUtil({
        data: dataSet,
        deleteRowsSelector: "#deleteRows1",
        restoreRowsSelector: "#restoreRows1",
        tableSelector: "#rtable"
    });

    /**
    * Utility to find items in Table View
    */
    var findTableViewUtil = function (config) {
        // Upon clicking the find button, show the find dropdown content
        $(".btn-find").click(function () {
            $(this).parent().find(".find-pf-dropdown-container").toggle();
        });

        // Upon clicking the find close button, hide the find dropdown content
        $(".btn-find-close").click(function () {
            $(".find-pf-dropdown-container").hide();
        });
    };

    // Initialize find util
    new findTableViewUtil();
});