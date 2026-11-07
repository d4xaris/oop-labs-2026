package calc.model;

public class CalcEngine {
    private NumBase base = NumBase.DEC;
    private String input = "0";
    private long acc = 0;
    private Operation pending = null;
    private boolean newInput = true;
    private boolean error = false;

    public NumBase getBase() {
        return base;
    }

    public String getDisplay() {
        return error ? "Error" : input;
    }

    private long current() {
        return base.parse(input);
    }

    public void setBase(NumBase newBase) {
        if (error) return;
        long value = current();
        base = newBase;
        input = base.format(value);
    }

    public void inputDigit(char c) {
        if (error) return;
        c = Character.toUpperCase(c);
        if (!base.isValidDigit(c)) return;

        String old = input;
        if (newInput || input.equals("0")) {
            input = String.valueOf(c);
        } else if (input.equals("-0")) {
            input = "-" + c;
        } else {
            input += c;
        }
        try {
            current();
        } catch (NumberFormatException e) {
            input = old;
            return;
        }
        newInput = false;
    }

    public void backspace() {
        if (error || newInput) return;
        input = input.substring(0, input.length() - 1);
        if (input.isEmpty() || input.equals("-")) {
            input = "0";
        }
    }

    public void clear() {
        input = "0";
        acc = 0;
        pending = null;
        newInput = true;
        error = false;
    }

    public void negate() {
        if (error || input.equals("0")) return;
        input = input.startsWith("-") ? input.substring(1) : "-" + input;
    }

    public void reciprocal() {
        if (error) return;
        long x = current();
        if (x == 0) {
            error = true;
            return;
        }
        input = base.format(1 / x);
        newInput = true;
    }

    public void setOperation(Operation op) {
        if (error) return;
        if (pending != null && !newInput) {
            evaluate();
        } else if (pending == null) {
            acc = current();
        }
        pending = op;
        newInput = true;
    }

    public void calculate() {
        if (error || pending == null) return;
        evaluate();
        pending = null;
    }

    private void evaluate() {
        try {
            acc = pending.apply(acc, current());
            input = base.format(acc);
        } catch (ArithmeticException e) {
            error = true;
        }
        newInput = true;
    }
}