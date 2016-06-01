$(function () {
    $('.tag-checkbox').click(function () {
        if (this.checked) {
            $.post('/subscription/tags/' + this.value);
        } else {
            $.ajax({
                url: '/subscription/tags/',
                type: 'DELETE'
            });
        }
    });
});