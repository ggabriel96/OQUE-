package QUETEM;

import java.util.*;

class Struct {
    private HashMap<String, Variable> vars;

    public void newVar(String n, Variable v) {
		this.vars.put(n, v);
	}

	public boolean hasVar(String name) {
		return this.vars.containsKey(name);
	}

	public Variable getVar(String name) {
		return this.vars.get(name);
	}

	public void setVar(Variable v, Variable other) {
		v.setValue(other);
	}

	public void setVar(Variable v, String value) {
		v.setValue(new StringVar(value));
	}
}
