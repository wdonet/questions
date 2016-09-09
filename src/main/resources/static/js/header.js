var MAX_LENGTH_TEXT_NOTIFICATION = 60;
var DEFAULT_ICON = " notification-unseen";

var notification_styles = {
    "NEW_QUESTION": {
        "iconClass": "fa fa-tags added-tag",
        "typeText": "Question added"
    },
    "ADD_ANSWER": {
        "iconClass": "fa fa-lightbulb-o improvement",
        "typeText": "Got an answer"
    },
    "IMPROVEMENT": {
        "iconClass": "fa fa-arrow-circle-up improvement",
        "typeText": "Question improved",
    },
    "ANSWER_ACCEPTED": {
        "iconClass": "fa fa-ban improvement",
        "typeText": "Answer accepted"
    },
    "QUESTION_VOTED_UP": {
        "iconClass": "fa fa-thumbs-o-up improvement",
        "typeText": "Question voted up"
    },
    "QUESTION_VOTED_DOWN": {
        "iconClass": "fa fa-thumbs-o-down close-improvement",
        "typeText": "Question voted down"
    },
    "ANSWER_VOTED_UP": {
        "iconClass": "fa fa-thumbs-up improvement",
        "typeText": "Answer voted up"
    },
    "ANSWER_VOTED_DOWN": {
        "iconClass": "fa fa-thumbs-down close-improvement",
        "typeText": "Answer voted down"
    },
    "ANSWER_FOR_TAGGED_QUESTION": {
        "iconClass": "fa fa-lightbulb-o added-tag",
        "typeText": "Answer for tagged question"
    },
    "CLOSE": {
        "iconClass": "fa fa-ban close-improvement",
        "typeText": ""
    }
};

$(function () {

    if (!$('.notifications-icon')[0]) {
        return;
    }

    var getDropContent = function (notificationsList) {
        var templateString = '<div class="notifications-header"><strong>Notifications</strong></div><ul>';
        notificationsList.forEach(function (notification) {
            var iconClass;
            var typeText;
            var notificationClass = !notification.uiNotified ? " notification-unseen" : "";
            var description = $.parseJSON(notification.description);
            if (notification_styles[notification.type]) {
                iconClass = notification_styles[notification.type].iconClass;
                typeText = notification_styles[notification.type].typeText;
            } else {
                iconClass = DEFAULT_ICON;
                typeText = '';
            }

            if (description.text.length >= MAX_LENGTH_TEXT_NOTIFICATION) {
                description.text =
                    description.text.substring(0, MAX_LENGTH_TEXT_NOTIFICATION - 3) + '...';
            }

            templateString +=
                '<li data-notification-id="' + notification.id +
                '" data-question-id="' + description.questionId +
                (description.answerId ? '" data-answer-id="' + description.answerId : "") +
                '" class="' + notificationClass +
                '">'
                + '<div><i class="' + iconClass + '"></i><strong>' + typeText + '</strong></div>'
                + '<div>' + description.text + '</div>'
                + '</li>';
        });
        templateString +=
            '</ul><div class="show-more-notifications center-text"><a href="/inbox">show all notifications</a></div>';

        return templateString;
    };

    $.get('/inbox/notifications', function (data) {
        var drop = new Drop({
            target: $('.notifications-icon')[0],
            content: '<div class="notification-container">' + getDropContent(data) + '</div>',
            classes: 'drop-theme-arrows',
            position: 'bottom center'
        });
        showOrHideNotificationsAlert(data);
        drop.once('open', function () {
            $('.drop-content').on('click', 'li', function () {
                $.ajax({
                           url: '/inbox/notifications/read/' + $(this).data('notification-id')
                                + '/',
                           type: 'POST'
                       }).done(function () {
                    window.location = '/question/' + $(this).data('question-id') +
                                      ($(this).data('answer-id') ? "#a-" + $(this).data('answer-id')
                                          : "");
                }.bind(this));
            });
        });
    });

    var getNotifications = function () {
        $.get('/inbox/notifications', function (data) {
            $('.notification-container').html(getDropContent(data));
            showOrHideNotificationsAlert(data);
        });
    };

    var showOrHideNotificationsAlert = function (notificationsList) {
        var filteredViewedNotifications = notificationsList.filter(function (notification) {
            return notification.uiNotified;
        });

        if (filteredViewedNotifications.length === notificationsList.length) {
            $('.notifications-icon').removeClass('new-notification');
        } else {
            $('.notifications-icon').addClass('new-notification');
        }
    };

    setInterval(getNotifications, 5000);
});