$(document).ready(function(){

    $('.suggestions-cont').attr('hidden', true)

    var getQuery = function() {
        return $('.input-search-question')[0].value;
    };

    $('.input-search-question').on('keypress', function(key){
        if (key.keyCode == 13) {
            $($('.search-submit')[0]).click()
        }
    });

    $('.search-submit').on("click", function(){
        $('.suggestions-cont').toggle(false);
        var query = getQuery();
        var url = '/question/search?query=' + query;
        $.get(url, function(data, status) {
            $('.suggestions-cont').toggle(true);
            $('.suggestions-cont').children().remove();
            console.log("Data: " + data[0] + "\nStatus: " + status);
            if (Array.isArray(data) && data.length > 0) {
                data.forEach(function(e){
                    var span = '<span class="tags"></span>';
                    var spans = ''; //todo
                    //if (Array.isArray(data.tags)) {
                    //    data.tags.forEach(function(t){
                    //        $(span).clone().append('<a href="/question/tag/' + t.id + '">' + t.name + '</a>');
                    //    });
                    //}
                    var html = '<li>' +
                        '<a class="respuesta-titulo" href="/question/' + e.id + '">' + e.title + '</a>' +
                        '<div class="respuestas-total">- ' + e.totalAnswers + ' Answers</div>' +
                        '<div class="tags-cont">' +
                        '    <div class="tag-icon"><img src="img/tag-icon.png">Tags:</div>' + spans +
                        '    <div class="owner">asked by &nbsp; <span>' + e.user.fullName + '</span></div>' +
                        '</div>' +
                        '</li>';
                    $('.suggestions-cont').append(html);
                });
            }
            else {
                $('.suggestions-cont').append('<li><div class="respuestas-total">No matches.</div></li>');
            }
        });
    });

});