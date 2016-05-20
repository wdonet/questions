var converter = new showdown.Converter();
converter.setOption('tables', 'true');
converter.setOption('tasklists', 'true');
converter.setOption('strikethrough', 'true');
converter.setOption('literalMidWordUnderscores', 'true');
converter.setOption('simplifiedAutoLink', 'true');

var preview = function (txt) {
    return marked(txt);  //https://raw.githubusercontent.com/chjj/marked/master/marked.min.js
    //return converter.makeHtml(txt);  //https://cdn.rawgit.com/showdownjs/showdown/1.4.1/dist/showdown.min.js
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
   - [x] This task is done
   - [ ] This is still pending 3. third numeric bullet

## SECOND
   - `highlighted`
   - __Code__:
 ```
System.out.println("hello world");
 ```

### 3rd TABLE


 | Right | Left | Centered |
 | ---:|:--- |:---:|
 | 123.00 | abc | ds |
 | 1.00 | d | 12312312333123123 |
 | 23.00 | SDasdf | asdd |

#### Link
 ~~strikethrough~~
 [google](http://google.com)
 Auto link? http://gmail.com
*/
