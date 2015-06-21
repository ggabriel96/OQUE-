package QUETEM;
/*******************************************************************************
Name: UatException.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Class UatException of OQUE, a programming language based on Java.
             Contains the messages that will be shown to the programmer when an
             exception occurs.
*******************************************************************************/
class UatException extends Exception {
    private String message, line;
    private int number;

    public UatException(String code, String line) {
        this.number = -1;
        this.setLine(line);

        switch (code) {
        case "syntaxError":
			this.setMessage("Syntax error"); break;
        case "bracketNotFound":
            this.setMessage("Could not find closing bracket for the block"); break;
        case "usingReservedWords":
			this.setMessage("You cannot use OQUE's reserved words"); break;
        case "invalidType":
			this.setMessage("Invalid type"); break;
        case "invalidVarName":
			this.setMessage("Variable names cannot start with numbers or contain special characters"); break;
        case "invalidExp":
            this.setMessage("Invalid expression"); break;
        case "varNotFound":
			this.setMessage("Could not find variable"); break;
        case "unknownSymbol":
			this.setMessage("Unknown symbol"); break;
        case "divisionByZero":
			this.setMessage("Division by zero"); break;
        case "unknownEscape":
			this.setMessage("Unknown escape sequence"); break;
        case "cantAssign":
			this.setMessage("Invalid assignment of types"); break;
        case "missingParen":
            this.setMessage("Missing parenthesis"); break;
        case "notLooping":
            this.setMessage("You're not inside a loop"); break;
        case "invalidLoopComm":
            this.setMessage("You cannot use break/continue here"); break;
        case "mainNotFound":
            this.setMessage("Could not find any fn main() definition"); break;
        case "noRetExp":
            this.setMessage("A return command must have an expression"); break;
        case "wrongArgs":
            this.setMessage("The arguments of the function call are wrong"); break;
        case "fnNotFound":
            this.setMessage("Undefined function call"); break;
        }
    }

    public UatException(String code, String line, int number) {
        this(code, line);
        this.setNumber(number);
    }

    private void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }

    private void setLine(String line) {
        this.line = line;
    }
    public String getLine() {
        return this.line;
    }

    public void setNumber(int number) {
        if (this.number == -1) this.number = number;
    }
    public int getNumber() {
        return this.number;
    }
}
