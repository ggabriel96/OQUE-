package QUETEM;

import java.util.*;

/* Command types:

- int code
- String [fields]
- int lineNumber

FUNCTIONS
fn <name>(<params>) {
[name|param1|param2|...|return|size]

VARIABLE DECLARATION
let <vars>: <type> // do we still need to assign a type?
[type|var1|var2|...]

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

BREAK/CONTINUE/}
These only have the code and lineNumber set
[] // ?
*/

class Command {
    private int code, lineNumber;
    private ArrayList<String> fields;

    public Command(int code, ArrayList<String> fields, int lineNumber) {
        this.code = code;
        this.fields = fields;
        this.lineNumber = lineNumber;
    }

    public String get(int index) {
        return this.fields.get(index);
    }

    public int size() {
        return this.fields.size();
    }

    public int code() {
        return this.code;
    }

    public int lineNumber() {
        return this.lineNumber;
    }

    public String toString() {
        String toString = "[" + this.code + "|";

        if (this.fields != null) {
            for (String s: this.fields) {
                toString += s + "|";
            }
        }

        toString += this.lineNumber + "]";

        return toString;
    }
}
