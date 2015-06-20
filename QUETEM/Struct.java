package QUETEM;

import java.util.*;

class Struct {
    private HashMap<String, Variable> vars;

    public Struct() {
        this.vars = new HashMap<>();
    }

    public String type() {
        String className;
        for (Variable var: vars.values()) {
            className = var.type().getName();
            return className.substring(className.lastIndexOf(".") + 1);
        }

        return "null";
    }

    public void replaceVars(HashMap<String, Variable> vars) {
        this.vars = vars;
    }

    public void newVar(String name, Variable v) {
		this.vars.put(name, v);
	}

	public boolean hasVar(String name) {
		return this.vars.containsKey(name);
	}

	public Variable getVar(String name) {
		return this.vars.get(name);
	}

    public void printVars() {
        System.out.println(this.type() + " {");
        for(Map.Entry<String, Variable> entry: vars.entrySet()) {
            System.out.println("\t" + entry.getKey() + " = " + entry.getValue().toString());
        }
        System.out.println("}");
    }
}
