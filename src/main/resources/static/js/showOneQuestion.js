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

    // For Comments
    $('.comment-box-question').each(function(index, child) {
        $(child).html(smde.markdown($(child).text()));
    });

    $('form textarea[name="description"]').on('keydown', function(event) {
        if (event.keyCode == 13 && !event.shiftKey) {
            $(this).parent().submit();
        }
    });

    $('#answer-comment-form').submit(function() {
        location.reload();
    });

});
