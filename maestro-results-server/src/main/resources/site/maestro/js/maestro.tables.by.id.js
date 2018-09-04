$(document).ready(function() {
    var id = getUrlVars()["test-id"]

    $('[data-datatables]').DataTable({
        columns: dbColumns,
        ajax: {
            url: $('[data-datatables]').attr('data-api') + "/" + id,
            dataSrc:  ''
        }
    });
});