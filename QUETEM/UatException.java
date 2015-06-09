package QUETEM;

class UatException extends Exception {
    private String message, line;
    private int number;

    public UatException(String code, String line) {
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
			this.setMessage("Variable names cannot start with numbers or special characters"); break;
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
        }
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
        if (this.number == 0) this.number = number;
    }
    public int getNumber() {
        return this.number;
    }
}
