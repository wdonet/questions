$(document).ready(function(){
    //For question
    $('div.question-description').html(linkify($('div.question-description').text()));

    //For answers
    $('div.answers').each(function(index, child) {
        $(child).html(linkify($(child).text()))
    });

});
