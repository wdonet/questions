$(document).ready(function(){
    var smde = new SimpleMDE({
        element : $('.source-code')[0],
        autofocus : true,
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
   - _Code_:
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
