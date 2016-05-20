var preview = function (txt) {
    return marked(txt);
};

$(document).ready(function(){
    $('.source-code').on('keypress focusout keyup', function(key){
        var txt = $('.source-code')[0].value;
        $('.markdown').html(preview(txt));
        var shouldShow = $('.preview').text() != "";
        $('.preview').toggle(shouldShow);
    });

});

/*
 # Try this FIRST:
 1. first numeric bullet
   - option 1
   - option 2
     - 2.2 correctly indented
 2. second numeric bullet
   - option 3
     - option 4
 3. third numeric bullet

 ## SECOND
   - `highlighted`
   - Code:
 ```
 System.out.println("hello world");
 ```

 ### TABLA


 | Right | Left | Centered |
 | ---:|:--- |:---:|
 | 123.00 | abc | ds |
 | 1.00 | d | 12312312333123123 |
 | 23.00 | SDasdf | asdd |

 #### Link
 [google](http://google.com)
*/
