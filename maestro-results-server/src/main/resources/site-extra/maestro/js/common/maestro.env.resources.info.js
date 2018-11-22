function envResourceInfoTable(element, url) {

    var extraEnvResourceInfoColumns = [
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
        columns: extraEnvResourceInfoColumns,
        ajax: {
            url: url,
            dataSrc:  ''
        }
    });
}