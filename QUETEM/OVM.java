package QUETEM;

/*******************************************************************************
Name: OVM.java
Authors: Acácia dos Campos da Terra - terra.acacia@gmail.com
         Gabriel Batista Galli - g7.galli96@gmail.com
         Vladimir Belinski - vlbelinski@gmail.com

Description: Class OVM (OQUE Virtual Machine) of OQUE, a programming language
             based on Java. Responsible for executing the code after it was
             successfully read and compiled by SourceScanner. Takes care of
             calling "main", the eventual other functions and handling their
             return values
*******************************************************************************/

import java.util.*;
import java.util.regex.*;

class OVM {

    public void run(HashMap<String, ArrayList<Command>> code) {
        ArrayList<Command> main = code.get("main");
    }
}
