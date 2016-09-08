$(document).ready(function () {
    $("#firstName, #lastName, #location").change(function () {
        var value = $(this).val().trim();
        updateUserField($(this).attr("id"), value);
    });
});

function updateUserField(fieldName, fieldValue) {

    var data = new Object();

    data[fieldName] = fieldValue;

    $("#" + fieldName).attr("disabled", true);

    $.ajax(
        {
            "type": "post",
            "url": "/profile/update/" + fieldName,
            "success": function (data) {
                $("#" + fieldName).removeAttr("disabled");
                $("a.user-info").html($("#firstName").val() + " " + $("#lastName").val());
            },
            "data": data
        }
    );
}

