package QUETEM;

/*
Command types:

VARIABLE DECLARATION:
let <vars>: <type> // do we still need to assign a type?
[code|var1|var2|...|type|lineNumber]

VARIABLE ASSIGNMENT
<var> = <expression|function call>
[code|varToAssign|expression|lineNumber] // function call?

PRINT(ln)
print(<text|$<vars>$>)
[code|text|var1|text|var2|...|lineNumber]

SCAN(ln)
scan(<vars>);
[code|var1|var2|var3|...|lineNumber]

IF/ELSIF/ELSE
if/elsif (<condition>) {
[code|condition|sizeUntilEnd|thisSize|lineNumber]
else
[code|sizeUntilEnd|thisSize|lineNumber]

WHILE
while (<condition>) {
[code|condition|size|lineNumber]

FOR
for (<init>; <condition>; <increment>) {
[code|init|condition|increment|size|lineNumber]

*/

class Command {

}
