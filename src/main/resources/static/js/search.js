$(document).ready(function () {

    var getQuery = function () {
        return $('.input-search-question')[0].value;
    };

    $('.input-search-question').on('keypress', function (key) {
        if (key.keyCode == 13) {
            $($('.search-submit')[0]).click()
        }
    });

    $('.search-submit').on("click", function () {
        $('.to-hide-when-searching').toggle(false);
        $('.category').each(function (index, link) {
            $(link).attr('class', '');
            $(link).addClass('category unlinked');
        });
        var query = getQuery();
        var url = '/question/search?query=' + query;
        $.get(url, function (data, status) {
            $('.suggestions-cont').toggle(true);
            $('.suggestions-cont').children().remove();
            if (Array.isArray(data) && data.length > 0) {
                data.forEach(function (e) {
                    var html_tags = '';
                    if (Array.isArray(e.tags) && e.tags.length > 0) {
                        e.tags.forEach(function (tag) {
                            html_tags =
                                html_tags + '<span class="tags">&nbsp;<a href="/question/tag/'
                                + tag.id + '">' + tag.name + '</a></span>';
                        });
                    }
                    var photo = e.user.photoUri ? e.user.photoUri : '/img/user-research-uxteam.jpg';
                    var html = '<li>' +
                               '<a class="answer-title" href="/question/' + e.id + '">' + e.title
                               + '</a>' +
                               '<div class="tags-cont">' +
                               '    <div class="tag-icon"><i class="fa fa-tags"></i>Categories: </div>'
                               + html_tags +
                               '    <div class="answers-total"><i class="fa fa-comments-o"></i> - '
                               + e.totalAnswers + ' Answers </div>' +
                               '    <div class="owner">' +
                               '        <i class="fa fa-user"></i>Asked By ' +
                               '        <img src="' + photo + '">' +
                               '        <span> &nbsp; <span>' + e.user.fullName + '</span></div>' +
                               '    </div>' +
                               '</li>';
                    $('.suggestions-cont').append(html);
                });
            }
            else {
                $('.suggestions-cont').append(
                    '<div class="no-results-cont"><div class="no-results-message">We are sorry you couldnt find what you where looking for...</div>'
                    +
                    '<p>Help us grow our database by creating your question </p>' +
                    '<input class="add-button" type="submit" value="Ask Question" onclick="location.href=\'/ask\'">');
            }
        });
    });

    $("#select_all").change(function () {
        $(".notification_checkbox").prop("checked", this.checked);
        styleNotificationsActionsBar();
    });

    $(".notification_checkbox").change(function () {
        var checkedLength = $(".notification_checkbox:checked").length;
        var nonCheckedLength = $(".notification_checkbox:not(:checked)").length;

        $("#select_all").prop("indeterminate", checkedLength > 0 && nonCheckedLength > 0);
        if (checkedLength > 0 && nonCheckedLength == 0) {
            $("#select_all").prop("checked", true);
        } else if (checkedLength == 0 && nonCheckedLength > 0) {
            $("#select_all").prop("checked", false);
        }
        styleNotificationsActionsBar();
    });

    $("#deleteNotifications").click(deleteSelectedNotifications);

    styleNotificationsActionsBar();
});

function styleNotificationsActionsBar() {
    var checkedLength = $(".notification_checkbox:checked").length;

    if (checkedLength > 0) {
        $("#deleteNotifications").show();
    } else {
        $("#deleteNotifications").hide();
    }
}

function deleteSelectedNotifications() {

    var listIds = [];
    var lengthPrefix = "notification_".length;

    $(".notification_checkbox:checked").each(function (index) {
        var id = $(this).attr("id");

        listIds.push(parseInt(id.substr(lengthPrefix)));
    });

    $.ajax({
               url: '/inbox/notifications/' + listIds,
               type: 'DELETE',
               success: function (response) {
                   location.reload();
               }
           });

}
