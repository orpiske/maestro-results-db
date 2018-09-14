$(document).ready(function() {
    var id = getUrlVars()["test-id"]

    var dbColumns2 = [
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
    ];

    $('[data-envres-datatables]').DataTable({
        searching: false,
        paging: false,
        info: false,
        columns: dbColumns2,
        ajax: {
            url: $('[data-envres-datatables]').attr('data-api') + id + "/resources",
            dataSrc:  ''
        }
    });
 }

)
