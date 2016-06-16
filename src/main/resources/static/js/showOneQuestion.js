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
        if (event.keyCode == 13 && !event.shiftKey && $(this).text()) {
            $(this).parent().submit();
            return false;
        }
    });

    $('#answer-comment-form').submit(function() {
        location.reload();
    });

    $("#edit-question-btn").click( function() {
        
        document.getElementById("edit-question-btn").disabled = true;
    
        var tags = $("#tag-div" ).text().replace(/(\r\n|\n|\r)/gm,"").replace(/\s/g, "");
        var title = $(".question-title" ).text();
        var htmlTitle = '<input name="title" form="editQuestionForm" type="text" id="questionTitle" value="' + title +'">';
        $(".question-title" ).html(htmlTitle);

        var description = $(".question-description" ).text();
        var htmlDescription =
            '<form method="post" id="editQuestionForm" action="/question/update">' +
                '<textarea name="description" class="edit-question-input" type="textarea" id="question-description-textarea"></textarea>'+
                '<h2 class="tags-title-form">Tags:</h2>' +
                '<input name="tags" type="text" id="tag" value="' + tags +'">' +
                '<input class="add-button" type="submit" value="Edit">' +
            '</form>';
        $( ".question-description" ).html(htmlDescription);

        var smdeQuestion = new SimpleMDE({
            autofocus : true,
            element : $('#question-description-textarea')[0],
            hideIcons: ["horizontal-rule"],
            blockStyles: { italic: "_" },
            indentWithTabs: false,
            initialValue : description,
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
    });
    
    var dates = $('.date-text');
    for (x in dates){
    	oldDate = $('.date')[x].innerText;
    	if (oldDate){
	    	currentDate = formatDate(oldDate);
	    	dates[x].innerText= currentDate;
    	}
    }

});

function formatDate(originalDate){
	tempDate = originalDate.split('-');
	tempDate.pop();
	date = new Date(tempDate.join('-'));
	date = date.toString().split(" ");
	var month = date[1];
	var day = date[2];
	var time = date[4].split(':');
	time.pop();
	time = time.join(':');
	return " "+month + " " + day + " at " + time;
}
