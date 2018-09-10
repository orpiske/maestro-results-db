$(document).ready(function() {
    $('[data-datatables]').DataTable({
        columns: dbColumns,
        ajax: {
            url: $('[data-datatables]').attr('data-api'),
            dataSrc:  ''
        },
        dom: "t",
        order: [[ 1, 'asc' ]],
        pfConfig: {
            emptyStateSelector: "#emptyState1",
            filterCaseInsensitive: true,
            filterCols: [
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
                placeholder: "Filter By Protocol..."
              }, {
                optionSelector: "#filter4",
                placeholder: "Filter By Message Size..."
              }, {
                optionSelector: "#filter5",
                placeholder: "Filter By Connection Count..."
              }
            ],
            paginationSelector: "#pagination1",
            toolbarSelector: "#toolbar1",
            //selectAllSelector: 'th:first-child input[type="checkbox"]',
            colvisMenuSelector: '.table-view-pf-colvis-menu'
          },
    });

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

    new findTableViewUtil();
});