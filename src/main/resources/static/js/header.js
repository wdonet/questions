$(function () {

    var getDropContent = function (notificationsList) {
        var templateString = '<div class="notifications-header"><strong>Notifications</strong></div><ul>';

        notificationsList.forEach(function (notification) {
            var statusClass;
            switch (notification.type) {
                case 'IMPROVEMENT':
                    statusClass = 'fa fa-arrow-circle-up improvement';
                    break;
                case 'CLOSE':
                    statusClass = 'fa fa-ban close-improvement';
                    break;
                case 'ADD':
                default:
                    statusClass = 'fa fa-tags added-tag';
                    break;
            }
            var notificationClass;
            if (!notification.uiNotified) {
                notificationClass = ' notification-unseen';
            }
            if (notification.description.length >= 57) {
                notification.description = notification.description.substring(0, 57) + '...';
            }

            templateString += '<li data-notification-id="' + notification.id + '" data-question-id="' + notification.question.id + '" class="' +
                notificationClass + '"><div><i class="' + statusClass + '"></i><strong>' +
                notification.description + '</strong></div><div> HERE WE HAVE A DESCRIPTION </div></li>';
        });
        templateString += '</ul><div class="show-more-notifications center-text"><a href="/inbox">Show more content ...</a></div>';

        return templateString;
    };

    $.get('/inbox/notifications', function (data) {
        var drop = new Drop({
            target: $('.notifications-icon')[0],
            content: '<div class="notification-container">' + getDropContent(data) + '</div>',
            classes: 'drop-theme-arrows',
            position: 'bottom center'
        });
        drop.once('open', function () {
            $('.drop-content').on('click', 'li', function () {
                $.ajax({
                    url: '/inbox/notifications/read/' + $(this).data('notification-id') + '/',
                    type: 'POST'
                }).done(function() {
                    window.location = '/question/' + $(this).data('question-id');
                }.bind(this));
            });
        });
    });

    var getNotifications = function () {
        $.get('/inbox/notifications', function (data) {
            $('.notification-container').html(getDropContent(data));
        });
    };

    setInterval(getNotifications, 5000);
});