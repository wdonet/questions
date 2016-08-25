$(function () {

    if (!$('.notifications-icon')[0]) {
        return;
    }

    var getDropContent = function (notificationsList) {
        var templateString = '<div class="notifications-header"><strong>Notifications</strong></div><ul>';
        notificationsList.forEach(function (notification) {
            var statusClass;
            var typeText;
            switch (notification.type) {
                case 'IMPROVEMENT':
                    statusClass = 'fa fa-arrow-circle-up improvement';
                    typeText = '';
                    break;
                case 'CLOSE':
                    statusClass = 'fa fa-ban close-improvement';
                    typeText = '';
                    break;
                case 'NEW_QUESTION':
                    typeText = 'New question';
                    break;
                case "ANSWER_ACCEPTED":
                    typeText = "Answer accepted";
                    break;
                case "QUESTION_VOTED_UP":
                    typeText = "Question voted up";
                    break;
                case "QUESTION_VOTED_DOWN":
                    typeText = "Question voted down";
                    break;
                case "ANSWER_VOTED_UP":
                    typeText = "Answer voted up";
                    break;
                case "ANSWER_VOTED_DOWN":
                    typeText = "Answer voted down";
                    break;
                case "ANSWER_FOR_TAGGED_QUESTION":
                    typeText = "Answer for tagged question";
                    break;
                default:
                    statusClass = 'fa fa-tags added-tag';
                    typeText = '';
                    break;
            }
            var notificationClass;
            if (!notification.uiNotified) {
                notificationClass = ' notification-unseen';
            }
            if (notification.description.length >= 57) {
                notification.description = notification.description.substring(0, 57) + '...';
            }

            templateString +=
                '<li data-notification-id="' + notification.id + '" data-question-id="'
                + notification.question.id + '" class="' +
                notificationClass + '"><div><strong>' +
                typeText + '</strong></div><div>' + notification.description + '</div></li>';
        });
        templateString +=
            '</ul><div class="show-more-notifications center-text"><a href="/inbox">Show more content ...</a></div>';

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
                    window.location = '/question/' + $(this).data('question-id');
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