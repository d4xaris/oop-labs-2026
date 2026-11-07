package calc.model;

public enum NumBase {
    DEC(10), BIN(2), HEX(16);

    private final int radix;

    NumBase(int radix) {
        this.radix = radix;
    }

    public boolean isValidDigit(char c) {
        return Character.digit(c, radix) >= 0;
    }

    public long parse(String s) {
        return Long.parseLong(s, radix);
    }

    public String format(long value) {
        return Long.toString(value, radix).toUpperCase();
    }
}