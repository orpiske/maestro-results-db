function envResourceInfoTable(element, url) {

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

    $(element).DataTable({
        searching: false,
        paging: false,
        info: false,
        columns: dbColumns2,
        ajax: {
            url: url,
            dataSrc:  ''
        }
    });
}