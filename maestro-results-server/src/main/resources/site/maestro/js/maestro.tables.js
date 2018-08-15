$(document).ready(function() {
    $('[data-datatables]').DataTable({
        columns: dbColumns,
        ajax: {
            url: $('[data-datatables]').attr('data-api'),
            dataSrc:  ''
        }
    });
});