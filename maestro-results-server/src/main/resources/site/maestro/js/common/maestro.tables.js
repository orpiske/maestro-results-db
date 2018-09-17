function maestroDataTable(element, url) {
    $(element).DataTable({
        columns: dbColumns,
        ajax: {
            url: url,
            dataSrc:  ''
        }
    });
};