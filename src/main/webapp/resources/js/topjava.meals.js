const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function updateThisTable() {
    filterMeal();
}

function filterMeal() {
    filterform = $('#filterForm');
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: filterform.serialize()
    }).done(function (filteredMeals) {
        ctx.datatableApi.clear().rows.add(filteredMeals).draw();
        successNoty("Filtered");
    });
}

function resetFilter() {
    filterform = $('#filterForm');
    filterform.find(":input").val("");
    updateTable();
    successNoty("Reset filter");
}


