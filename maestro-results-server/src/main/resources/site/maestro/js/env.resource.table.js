
$(document).ready(function() {
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
        ajax: {
            url: '/api/env/resource',
            dataSrc:  ''
        }
    });
});