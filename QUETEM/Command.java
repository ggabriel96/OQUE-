package QUETEM;

/* Command types:

- int code
- String [fields]
- int lineNumber

VARIABLE DECLARATION:
let <vars>: <type> // do we still need to assign a type?
[var1|var2|...|type]

VARIABLE ASSIGNMENT
<var> = <expression|function call>
[varToAssign|expression] // function call?

PRINT(ln)
print(<text|$<vars>$>)
[text|var1|text|var2|...]

SCAN(ln)
scan(<vars>);
[var1|var2|var3|...]

IF/ELSIF/ELSE
if/elsif (<condition>) {
[condition|sizeUntilEnd|thisSize]
else
[sizeUntilEnd|thisSize]

WHILE
while (<condition>) {
[condition|size]

FOR
for (<init>; <condition>; <increment>) {
[init|condition|increment|size]

BREAK/CONTINUE
[] // ?
*/

class Command {
    private int code, lineNumber;
    private String[] command;

    public Command(int code, String[] command, int lineNumber) {
        this.code = code;
        this.command = command;
        this.lineNumber = lineNumber;
    }

    public String get(int index) {
        return this.command[index];
    }

    public int length() {
        return this.command.length;
    }

    public int code() {
        return this.code;
    }

    public int lineNumber() {
        return this.lineNumber;
    }
}
