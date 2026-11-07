package calc.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CalcEngine")
class CalcEngineTest {

    private CalcEngine calc;

    @BeforeEach
    void setUp() {
        calc = new CalcEngine();
    }

    // ---- helpers: make the tests read like pressing buttons ----

    private void type(String digits) {
        for (char c : digits.toCharArray()) calc.inputDigit(c);
    }

    private void op(Operation operation) {
        calc.setOperation(operation);
    }

    private void eq() {
        calc.calculate();
    }

    private void assertDisplay(String expected) {
        assertEquals(expected, calc.getDisplay());
    }

    private void divideByZero() {
        type("5");
        op(Operation.DIV);
        type("0");
        eq();
    }

    // ------------------------------------------------------------

    @Nested
    @DisplayName("Initial state")
    class InitialState {

        @Test
        void startsAtZeroInDecimal() {
            assertDisplay("0");
            assertEquals(NumBase.DEC, calc.getBase());
        }
    }

    @Nested
    @DisplayName("Digit input")
    class DigitInput {

        @Test
        void digitsAreAppended() {
            type("123");
            assertDisplay("123");
        }

        @Test
        void leadingZerosCollapse() {
            type("007");
            assertDisplay("7");
        }

        @Test
        void letterIsIgnoredInDecimal() {
            type("1A2");
            assertDisplay("12");
        }

        @Test
        void decimalRejectsDigitThatOverflowsLong() {
            type("9223372036854775807");
            type("9");
            assertDisplay("9223372036854775807");
        }
    }

    @Nested
    @DisplayName("Arithmetic: a op b =")
    class Arithmetic {

        @ParameterizedTest(name = "{0} {1} {2} = {3}")
        @CsvSource({
                "2,  ADD, 3,  5",
                "9,  SUB, 4,  5",
                "3,  SUB, 5,  -2",
                "6,  MUL, 7,  42",
                "0,  MUL, 9,  0",
                "20, DIV, 4,  5",
                "7,  DIV, 2,  3",
                "1,  DIV, 5,  0"
        })
        void computesResult(String a, Operation op, String b, String expected) {
            type(a);
            op(op);
            type(b);
            eq();
            assertDisplay(expected);
        }

        @Test
        void operatorKeyDoesNotChangeDisplay() {
            type("7");
            op(Operation.ADD);
            assertDisplay("7");
        }
    }

    @Nested
    @DisplayName("Chained operations")
    class Chaining {

        @Test
        void secondOperatorShowsIntermediateResult() {
            type("2");
            op(Operation.ADD);
            type("3");
            op(Operation.ADD);
            assertDisplay("5");
            type("4");
            eq();
            assertDisplay("9");
        }

        @Test
        void evaluatesLeftToRight() {
            type("10");
            op(Operation.SUB);
            type("2");
            op(Operation.MUL);
            type("3");
            eq();
            assertDisplay("24");
        }

        @Test
        void changingOperatorBeforeSecondNumberReplacesIt() {
            type("5");
            op(Operation.ADD);
            op(Operation.MUL);
            type("3");
            eq();
            assertDisplay("15");
        }

        @Test
        void resultCanBeUsedAsFirstOperandAfterEquals() {
            type("2");
            op(Operation.ADD);
            type("3");
            eq();
            op(Operation.ADD);
            type("4");
            eq();
            assertDisplay("9");
        }

        @Test
        void typingDigitAfterEqualsStartsNewNumber() {
            type("2");
            op(Operation.ADD);
            type("3");
            eq();
            type("7");
            assertDisplay("7");
        }

        @Test
        void equalsWithoutOperationKeepsValue() {
            type("42");
            eq();
            assertDisplay("42");
        }

        @Test
        void pressingEqualsTwiceDoesNotRepeatOperation() {
            type("2");
            op(Operation.ADD);
            type("3");
            eq();
            eq();
            assertDisplay("5");
        }
    }

    @Nested
    @DisplayName("Sign change (±)")
    class Negate {

        @Test
        void togglesSign() {
            type("5");
            calc.negate();
            assertDisplay("-5");
            calc.negate();
            assertDisplay("5");
        }

        @Test
        void zeroStaysZero() {
            calc.negate();
            assertDisplay("0");
        }

        @Test
        void negativeNumberCanBeEditedFurther() {
            type("5");
            calc.negate();
            type("3");
            assertDisplay("-53");
        }

        @Test
        void negativeOperandIsUsedInCalculation() {
            type("5");
            calc.negate();
            op(Operation.ADD);
            type("3");
            eq();
            assertDisplay("-2");
        }
    }

    @Nested
    @DisplayName("Reciprocal (1/X)")
    class Reciprocal {

        @Test
        void oneOverOneIsOne() {
            type("1");
            calc.reciprocal();
            assertDisplay("1");
        }

        @Test
        void oneOverMinusOneIsMinusOne() {
            type("1");
            calc.negate();
            calc.reciprocal();
            assertDisplay("-1");
        }

        @Test
        void integerArithmeticGivesZeroForLargerNumbers() {
            type("2");
            calc.reciprocal();
            assertDisplay("0");
        }

        @Test
        void zeroGivesError() {
            calc.reciprocal();
            assertDisplay("Error");
        }

        @Test
        void nextDigitStartsNewNumber() {
            type("2");
            calc.reciprocal();
            type("7");
            assertDisplay("7");
        }
    }

    @Nested
    @DisplayName("Backspace")
    class Backspace {

        @Test
        void removesLastDigit() {
            type("123");
            calc.backspace();
            assertDisplay("12");
        }

        @Test
        void lastDigitBecomesZero() {
            type("5");
            calc.backspace();
            assertDisplay("0");
        }

        @Test
        void repeatedBackspaceStopsAtZero() {
            type("12");
            for (int i = 0; i < 5; i++) calc.backspace();
            assertDisplay("0");
        }

        @Test
        void removingLastDigitOfNegativeNumberGivesZero() {
            type("5");
            calc.negate();
            calc.backspace();
            assertDisplay("0");
        }

        @Test
        void doesNothingRightAfterOperator() {
            type("5");
            op(Operation.ADD);
            calc.backspace();
            assertDisplay("5");
        }

        @Test
        void doesNothingOnResult() {
            type("2");
            op(Operation.ADD);
            type("3");
            eq();
            calc.backspace();
            assertDisplay("5");
        }
    }

    @Nested
    @DisplayName("Clear (C)")
    class Clear {

        @Test
        void resetsDisplay() {
            type("123");
            calc.clear();
            assertDisplay("0");
        }

        @Test
        void forgetsPendingOperation() {
            type("5");
            op(Operation.ADD);
            type("3");
            calc.clear();
            type("2");
            op(Operation.ADD);
            type("2");
            eq();
            assertDisplay("4");
        }

        @Test
        void keepsCurrentBase() {
            calc.setBase(NumBase.HEX);
            calc.clear();
            assertEquals(NumBase.HEX, calc.getBase());
        }
    }

    @Nested
    @DisplayName("Number systems")
    class Bases {

        @Test
        void switchingBaseConvertsCurrentValue() {
            type("255");
            calc.setBase(NumBase.HEX);
            assertDisplay("FF");
            calc.setBase(NumBase.BIN);
            assertDisplay("11111111");
            calc.setBase(NumBase.DEC);
            assertDisplay("255");
        }

        @Test
        void negativeValueIsConverted() {
            type("5");
            calc.negate();
            calc.setBase(NumBase.BIN);
            assertDisplay("-101");
        }

        @Test
        void binaryIgnoresDigitsAbove1() {
            calc.setBase(NumBase.BIN);
            type("1021");
            assertDisplay("101");
        }

        @Test
        void hexAcceptsLowerCaseAndShowsUpperCase() {
            calc.setBase(NumBase.HEX);
            type("ff");
            assertDisplay("FF");
        }

        @Test
        void hexArithmetic() {
            calc.setBase(NumBase.HEX);
            type("FF");
            op(Operation.ADD);
            type("1");
            eq();
            assertDisplay("100");
        }

        @Test
        void binaryArithmetic() {
            calc.setBase(NumBase.BIN);
            type("101");
            op(Operation.ADD);
            type("11");
            eq();
            assertDisplay("1000");
        }

        @Test
        void switchingBaseBetweenOperandsKeepsPendingOperation() {
            type("10");
            op(Operation.ADD);
            calc.setBase(NumBase.HEX);
            type("5");
            eq();
            assertDisplay("F");
        }

        @Test
        void hexRejectsDigitThatOverflowsLong() {
            calc.setBase(NumBase.HEX);
            type("7FFFFFFFFFFFFFFF");
            type("F");
            assertDisplay("7FFFFFFFFFFFFFFF");
        }
    }

    @Nested
    @DisplayName("Errors")
    class Errors {

        @Test
        void divisionByZeroShowsError() {
            divideByZero();
            assertDisplay("Error");
        }

        @Test
        void overflowShowsError() {
            type("9223372036854775807");
            op(Operation.ADD);
            type("1");
            eq();
            assertDisplay("Error");
        }

        @Test
        void inputIsIgnoredUntilCleared() {
            divideByZero();
            type("5");
            op(Operation.ADD);
            calc.negate();
            calc.reciprocal();
            calc.backspace();
            calc.setBase(NumBase.HEX);
            assertDisplay("Error");
            assertEquals(NumBase.DEC, calc.getBase());
        }

        @Test
        void clearRecoversFromError() {
            divideByZero();
            calc.clear();
            assertDisplay("0");
            type("2");
            op(Operation.ADD);
            type("2");
            eq();
            assertDisplay("4");
        }
    }
}