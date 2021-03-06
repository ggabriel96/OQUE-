package QUETEM;

class BoolVar extends Variable<Boolean> {
    public static final boolean DEFAULT_VALUE = false;

    public BoolVar() {
        super(DEFAULT_VALUE);
    }

    public BoolVar(Boolean value) {
        super(value);
    }

    public void setValue(Variable other) {
        this.value = other.toBool();
    }

    public Variable inverted() {
        return new BoolVar(!this.value);
    }

    public Boolean toBool() {
        return this.value;
    }

    public Integer toInt() {
        if (this.value.equals(true)) {
            return new Integer(1);
        }
        else {
            return new Integer(0);
        }
    }

    public Double toDouble() {
        if (this.value.equals(true)) {
            return new Double(1.0);
        }
        else {
            return new Double(0.0);
        }
    }

    public Variable or(Variable other) {
        return new BoolVar(this.value || other.toBool());
    }

    // or
    public Variable plus(Variable other) {
        return this.or(other);
    }

    // nor
    public Variable minus(Variable other) {
        return this.or(other).inverted();
    }

    public Variable and(Variable other) {
        return new BoolVar(this.value && other.toBool());
    }

    // and
    public Variable times(Variable other) {
        return this.and(other);
    }

    // nand
    public Variable divided(Variable other) throws UatException {
        return this.and(other).inverted();
    }

    // xnor
    public Variable mod(Variable other) throws UatException {
        return this.pow(other).inverted();
    }

    // xor
    public Variable pow(Variable other) {
        return new BoolVar(this.value ^ other.toBool());
    }

    public Variable equals(Variable other) {
        return new BoolVar(this.value.equals(other.toBool()));
    }

    // <, <=, >= and > converts to int and then do the normal comparison
    public Variable lessThan(Variable other) {
        return this.toIntVar().lessThan(other.toIntVar());
    }

    public Variable lessEquals(Variable other) {
        return this.lessThan(other).or(this.equals(other));
    }

    public Variable greaterThan(Variable other) {
        return this.toIntVar().greaterThan(other.toIntVar());
    }

    public Variable greaterEquals(Variable other) {
        return this.greaterThan(other).or(this.equals(other));
    }
}
