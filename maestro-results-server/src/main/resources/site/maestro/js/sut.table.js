
$(document).ready(function() {
    $('#suttable').DataTable({
        columns: [
            { data: "sutId" },
            { data: "sutName" },
            { data: "sutVersion" },
            { data: "sutJvmInfo" },
            { data: "sutOther"},
            { data: "sutTags"}
        ],
        ajax: {
            url: '/api/sut/',
            dataSrc:  ''
        }
    });
});