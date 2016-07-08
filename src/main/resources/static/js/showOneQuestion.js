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
    var smdQuestion = new SimpleMDE({
        autofocus : true,
        element : $('#question-description-textarea')[0],
        hideIcons: ["horizontal-rule"],
        blockStyles: { italic: "_" },
        indentWithTabs: false,
        initialValue : $(".question-description" ).text(),
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

    var smdAnswer;

    $('#edit-title-input-div').hide();
    $('#edit-question-form-div').hide();
    $('.edit-answer-form-div').hide();

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

    $('.edit-btn.answer').click(function() {
        var answerIndex =  $(this).val();
        smdAnswer = new SimpleMDE({
            autofocus : true,
            element : $('#answer-description-textarea-' +  answerIndex)[0],
            hideIcons: ["horizontal-rule"],
            blockStyles: { italic: "_" },
            indentWithTabs: false,
            initialValue : $("#answer-description-div-" + answerIndex ).text(),
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
        var description = $("#answer-description-div-" + answerIndex ).text();
        smdAnswer.value(description);
        $(".edit-btn.answer").attr("disabled", true);
        $('#edit-answer-form-div-' +  answerIndex ).show();
        $('#answer-description-div-' +  answerIndex ).hide();
    });

    $(".cancel-edit-answer-btn").click( function() {
        var answerIndex = $(this).val();
        $(".edit-btn.answer").attr("disabled", false);
        $('#edit-answer-form-div-' +  answerIndex ).hide();
        $('#answer-description-div-' +  answerIndex ).show();
        smdAnswer.toTextArea();
        smdAnswer = null;
    });

    $("#edit-question-btn").click( function() {
        var description = $(".question-description" ).text();
        $("#edit-question-btn" ).disabled = true;
        showTitleInput();
        showDescriptionInput();
        smdQuestion.value(description);
        hideQuestionInfoDiv();
    });

    $("#cancelEditBtn").click( function() {
        $("#edit-question-btn" ).disabled = false;
        $('#edit-title-input-div').hide();
        $(".question-title" ).show();
        $('.question-description').show();
        $('#edit-question-form-div').hide();
        $("#questionInfo").show();
    });
    
    var dates = $('.date-text');
    for (x in dates){
    	oldDate = $('.date')[x].innerText;
    	if (oldDate){
	    	currentDate = formatDate(oldDate);
	    	dates[x].innerText= currentDate;
    	}
    }

    $('.date > .date-text').each(function(index, date){
        var newDate = formatDate($(date).text());
        $(date).text(newDate);
    });

    $('.add-comment-cont > a.show-hide-comments').on('click', function(){
        $('.all-comments-question').toggle();
    });
    $('.add-comment-cont > a.open-add-comment').on('click', function(){
        $('.question-comment-cont').toggle();
    });

});

function hideQuestionInfoDiv() {
    $("#questionInfo").hide();
}

function showTitleInput() {
    var title = $(".question-title" ).text();
    $(".question-title" ).hide();
    $('#questionTitleInput').val(title);
    $('#edit-title-input-div').show();
}

function showDescriptionInput() {
    $('.question-description').hide();
    $('#edit-question-form-div').show();
    var tags = $("#tag-div" ).text().replace(/(\r\n|\n|\r)/gm,"").replace(/\s/g, "");
    $('#tag-edit-input').val(tags);
}

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
