$(document).ready(function() {
    $('[data-datatables]').DataTable({
        columns: extraSutPropertiesColumns,
        ajax: {
            url: $('[data-datatables]').attr('data-api'),
            dataSrc:  ''
        },
        order: [[ 1, 'desc' ]],
        dom: "tpi",
        pfConfig: {
            emptyStateSelector: "#emptyState1",
            filterCaseInsensitive: true,
            filterCols: [
              null,
              null,
              null,
              {
                default: true,
                optionSelector: "#filter1",
                placeholder: "Filter By Product Name..."
              }, {
                optionSelector: "#filter2",
                placeholder: "Filter By Product Version..."
              }, {
                optionSelector: "#filter3",
                placeholder: "Filter By Test Name..."
              }, {
                optionSelector: "#filter4",
                placeholder: "Filter By Test Result..."
              }, {
                 optionSelector: "#filter5",
                 placeholder: "Filter By API Name..."
              }, {
                optionSelector: "#filter6",
                placeholder: "Filter By API Version..."
              }, {
                optionSelector: "#filter7",
                placeholder: "Filter By Protocol..."
              }, {
                optionSelector: "#filter8",
                placeholder: "Filter By Connection Count..."
              },
              null,
              {
                 optionSelector: "#filter9",
                 placeholder: "Filter By Durability..."
              },
              {
                optionSelector: "#filter10",
                placeholder: "Filter By Limit Destinations..."
              }, {
                 optionSelector: "#filter11",
                 placeholder: "Filter By Message Size..."
              },
              null,
              null,
              {
                optionSelector: "#filter12",
                placeholder: "Filter By SUT Tags..."
              }, {
                optionSelector: "#filter13",
                placeholder: "Filter By Test Tags..."
              }
            ],
            toolbarSelector: "#toolbar1",
            selectAllSelector: 'th:first-child input[type="checkbox"]',
            colvisMenuSelector: '.table-view-pf-colvis-menu'
          },
          select: {
              selector: 'td:first-child input[type="checkbox"]',
              style: 'multi',
              info: false
           },
    });

    document.getElementById("compareb1").addEventListener("click", myFunction);

    function myFunction() {
       var table = $("#testrestable").DataTable();

       var data = table.rows( { selected: true } ).data();

       if (data.length != 2) {
            alert("You can only compare 2 records at a time")
            return
       }

        window.location.href = 'compare-tests.html?t0=' + data[0].testId + '&n0=' + data[0].testNumber + '&t1=' +
            data[1].testId + '&n1=' + data[1].testNumber;
    }
});