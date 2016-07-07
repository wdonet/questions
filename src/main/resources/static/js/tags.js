$(function () {
    $('.tag-checkbox').click(function () {
        var url = '/subscription/tags/' + this.value;
        if (this.checked) {
            $.post(url);
        } else {
            $.ajax({
                url: url,
                type: 'DELETE'
            });
        }
    });
});