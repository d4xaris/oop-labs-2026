package calc.model;

public enum Operation {
    ADD { public long apply(long a, long b) { return Math.addExact(a, b); } },
    SUB { public long apply(long a, long b) { return Math.subtractExact(a, b); } },
    MUL { public long apply(long a, long b) { return Math.multiplyExact(a, b); } },
    DIV { public long apply(long a, long b) { return Math.divideExact(a, b); } };

    public abstract long apply(long a, long b);
}