$(document).ready(function(){

    var getQuery = function() {
        return $('.input-search-question')[0].value;
    };

    $('.input-search-question').on('keypress', function(key){
        if (key.keyCode == 13) {
            $($('.search-submit')[0]).click()
        }
    });

    $('.search-submit').on("click", function(){
        $('.unanswered-q').toggle(false);
        $('.category').each(function(index, link){
            $(link).attr('class', '');
            $(link).addClass('category unlinked');
        });
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
                        '<a class="answer-title" href="/question/' + e.id + '">' + e.title + '</a>' +
                        '<div class="tags-cont">' +
                        '    <div class="tag-icon"><i class="fa fa-tags"></i>Categories:</div>' + spans +
                        '    <div class="respuestas-total"><i class="fa fa-comments-o"></i>- ' + e.totalAnswers + ' Answers</div>' +

                        '    <div class="owner"><i class="fa fa-user"></i>Asked By &nbsp; <span>' + e.user.fullName + '</span></div>' +
                        '</div>' +
                        '</li>';
                    $('.suggestions-cont').append(html);
                });
            }
            else {
                $('.suggestions-cont').append('<div class="no-results-cont"><div class="no-results-message">We are sorry you couldnt find what you where looking for...</div><p>Help us grow our database by creating your question </p><input class="add-button" type="submit" value="Ask Question">');
            }
        });
    });

});
