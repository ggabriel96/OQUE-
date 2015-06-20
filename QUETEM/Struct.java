package QUETEM;

import java.util.*;

class Struct {
    public static final String DEFAULT_FIELD = "nil";
    private HashMap<String, Variable> vars;

    public Struct() {
        this.vars = new HashMap<>();
    }

    public Struct(Variable v) {
        this();
        this.newVar(v);
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

    // DEFAULT_FIELD

    public Variable getDefault() {
        return this.vars.get(Struct.DEFAULT_FIELD);
    }

    public void setValue(Variable v) {
        this.getDefault().setValue(v);
    }

    public void newVar(Variable v) {
        this.vars.put(Struct.DEFAULT_FIELD, v);
    }
}
