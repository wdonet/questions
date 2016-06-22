$(document).ready(function(){
    var smde = new SimpleMDE({
        autofocus : true,
        element : $('.add-answer-input')[0],
        hideIcons: ["horizontal-rule"],
        blockStyles: { italic: "_" },
        indentWithTabs: false,
        tabSize: 4,
        insertTexts: {
            horizontalRule: ["", "\n\n-----\n\n"],
            image: ["![](http://", ")"],
            link: ["[", "](http://)"],
            table: ["", "\n\n| Column 1 | Column 2 | Column 3 |\n| -------- | -------- | -------- |\n| Text     | Text      | Text     |\n\n"],
        },
        renderingConfig: {
            codeSyntaxHighlighting: true
        },
        shortcuts: {
            drawTable: "Cmd-Alt-T"
        }

    });

    // For question
    $('div.question-description').html(smde.markdown($('div.question-description').text()));

    // For answers
    $('div.answers').each(function(index, child) {
        $(child).html(smde.markdown($(child).text()));
    });

    $('form textarea[name="description"]').on('keydown', function(event) {
        if (event.keyCode == 13 && !event.shiftKey && $(this).text()) {
            $(this).parent().submit();
            return false;
        }
    });

    $('#answer-comment-form').submit(function() {
        location.reload();
    });

    $('.date > .date-text').each(function(index, date){
        var newDate = formatDate($(date).text());
        $(date).text(newDate);
    });

    $('.add-comment-cont > a.show-hide').on('click', function(){
        $('.comments-question-cont').toggle();
    });
    $('.add-comment-cont > a.add-comment').on('click', function(){
        $('.question-comment-cont').toggle();
    });

});

function formatDate(originalDate){
    var tempDate = originalDate.split('-');
    tempDate.pop();
    var date = new Date(tempDate.join('-'));
    date = date.toString().split(" ");
    var month = date[1];
    var day = date[2];
    var time = date[4].split(':');
    time.pop();
    time = time.join(':');
    return " " + month + " " + day + " at " + time;
}
