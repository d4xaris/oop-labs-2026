package calc.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Operation")
class OperationTest {

    @ParameterizedTest(name = "{0} {1} {2} = {3}")
    @CsvSource({
            "6,  ADD, 4,  10",
            "6,  SUB, 4,  2",
            "4,  SUB, 6,  -2",
            "6,  MUL, 4,  24",
            "6,  MUL, 0,  0",
            "8,  DIV, 2,  4",
            "7,  DIV, 2,  3",
            "1,  DIV, 5,  0",
            "-7, DIV, 2,  -3",
            "-6, MUL, -4, 24"
    })
    void appliesCorrectly(long a, Operation op, long b, long expected) {
        assertEquals(expected, op.apply(a, b));
    }

    @Test
    @DisplayName("division by zero throws ArithmeticException")
    void divisionByZero() {
        assertThrows(ArithmeticException.class, () -> Operation.DIV.apply(5, 0));
    }

    @Test
    @DisplayName("overflow throws ArithmeticException (needs Math.*Exact in apply)")
    void overflowIsDetected() {
        assertAll(
                () -> assertThrows(ArithmeticException.class, () -> Operation.ADD.apply(Long.MAX_VALUE, 1)),
                () -> assertThrows(ArithmeticException.class, () -> Operation.SUB.apply(Long.MIN_VALUE, 1)),
                () -> assertThrows(ArithmeticException.class, () -> Operation.MUL.apply(Long.MAX_VALUE, 2)));
    }
}