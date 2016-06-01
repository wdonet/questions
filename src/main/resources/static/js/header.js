var notifications = [{
    postId: 1,
    title: 'Hello World',
    description: 'This is the fucking description lorem mada fucka ipsum im!!!',
    viewed: false,
    status: 'improvement'
}, {
    postId: 2,
    title: 'Hello World 1',
    description: 'This is the fucking description 1',
    viewed: true,
    status: 'added-tag'
}, {
    postId: 3,
    title: 'Hello World 2',
    description: 'This is the fucking description 2',
    viewed: false,
    status: 'close-improvement'
}];

$(function () {

    var getDropContent = function (notificationsList) {
        var templateString = '<div class="notifications-header"><strong>Notifications</strong></div><ul>';

        notificationsList.forEach(function (notification) {
            var statusClass;
            switch (notification.status) {
                case 'improvement':
                    statusClass = 'fa fa-arrow-circle-up ' + notification.status;
                    break;
                case 'close-improvement':
                    statusClass = 'fa fa-ban ' + notification.status;
                    break;
                case 'added-tag':
                default:
                    statusClass = 'fa fa-tags ' + notification.status;
                    break;
            }
            var notificationClass;
            if (!notification.viewed) {
                notificationClass = ' notification-unseen';
            }
            if (notification.description.length >= 57) {
                notification.description = notification.description.substring(0, 57) + '...';
            }

            templateString += '<li data-post-id="' + notification.postId + '" class="' +
                notificationClass + '"><div><i class="' + statusClass + '"></i><strong>' +
                notification.title + '</strong></div><div>' + notification.description +
                '</div></li>';
        });
        templateString += '</ul><div class="show-more-notifications center-text"><a href="/inbox">Show more content ...</a></div>';

        return templateString;
    };
    var drop = new Drop({
        target: $('.notifications-icon')[0],
        content: getDropContent(notifications),
        classes: 'drop-theme-arrows',
        position: 'bottom center'
    });

    drop.once('open', function (event) {
        $('.drop-content ul').on('click', 'li', function (event) {
            window.location = '/question/' + $(this).data('post-id');
        });
    });








    //var checkForNotifications = function() {
    //    $.get('/inbox/notifications', function() {
    //
    //    });
    //};
    //
    //setInterval(checkForNotifications, 5000);

});