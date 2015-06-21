package QUETEM;
/*******************************************************************************
Name: Command.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Class Command of a fork of OQUE, a programming language based on
             Java. Contains implementation about the commands that can be found
             in the language.
*******************************************************************************/

import java.util.*;

/* Command types:

- int code
- String[] fields
- int lineNumber

FUNCTIONS
fn <name>(<params>) {
[10|name|param1|param2|...|linesToJump|lineNumber]

return
[11|lineNumber]
return <expression>
[11|expression|lineNumber]

VARIABLE DECLARATION
let <vars>
[20|type|var1'atr1|var2'atr2|...|lineNumber]

VARIABLE ASSIGNMENT
<var> = <expression|function call>
[30|varToAssign|expression|lineNumber]

PRINT(ln)
print(<text|$<vars>$>)
[41/41|text|`var1`|text|`var2`|...|lineNumber]

SCAN(ln)
scan(<vars>);
[50/51|var1|var2|var3|...|lineNumber]

IF/ELSIF/ELSE
if/elsif (<condition>) {
[60/61|condition|linesToJump|lineNumber]
else {
[62|linesToJump|lineNumber]

WHILE
while (<condition>) {
[70|condition|linesToJump|lineNumber]

FOR
for (<init>; <condition>; <increment>) {
[71|initCode|initCommand|condition|incrementCode|incrementCommand|linesToJump|lineNumber]

BREAK
[72|linesToJump|lineNumber]

CONTINUE
[73|initialLineOfLoop|lineNumber]

'}'
[12|initialLineOfBlock|lineNumber]
*/

class Command {
    private int code, lineNumber;
    private ArrayList<String> fields;

    public Command(int code, ArrayList<String> fields, int lineNumber) {
        this.code = code;
        this.fields = fields;
        this.lineNumber = lineNumber;
    }

    public Command(int code, String[] fields, int lineNumber) {
        this.code = code;
        this.fields = new ArrayList<String>();
        for (String f: fields) {
            this.fields.add(f);
        }
        this.lineNumber = lineNumber;
    }

    public void add(String s) {
        this.fields.add(s);
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

    public ArrayList<String> fields() {
        return this.fields;
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
