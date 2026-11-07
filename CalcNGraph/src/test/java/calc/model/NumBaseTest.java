package calc.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("NumBase")
class NumBaseTest {

    @Nested
    @DisplayName("format()")
    class Format {

        @ParameterizedTest(name = "{0} -> DEC {1}, BIN {2}, HEX {3}")
        @CsvSource({
                "0,   0,   0,        0",
                "1,   1,   1,        1",
                "10,  10,  1010,     A",
                "15,  15,  1111,     F",
                "255, 255, 11111111, FF",
                "256, 256, 100000000, 100"
        })
        void formatsPositiveNumbers(long value, String dec, String bin, String hex) {
            assertAll(
                    () -> assertEquals(dec, NumBase.DEC.format(value)),
                    () -> assertEquals(bin, NumBase.BIN.format(value)),
                    () -> assertEquals(hex, NumBase.HEX.format(value)));
        }

        @ParameterizedTest(name = "{0} -> DEC {1}, BIN {2}, HEX {3}")
        @CsvSource({
                "-5,   -5,   -101,       -5",
                "-255, -255, -11111111, -FF"
        })
        void formatsNegativeNumbersWithMinusSign(long value, String dec, String bin, String hex) {
            assertAll(
                    () -> assertEquals(dec, NumBase.DEC.format(value)),
                    () -> assertEquals(bin, NumBase.BIN.format(value)),
                    () -> assertEquals(hex, NumBase.HEX.format(value)));
        }

        @Test
        @DisplayName("hex output uses upper-case letters")
        void hexIsUpperCase() {
            assertEquals("ABCDEF", NumBase.HEX.format(0xABCDEF));
        }
    }

    @Nested
    @DisplayName("parse()")
    class Parse {

        @ParameterizedTest(name = "{0} parses to {1}")
        @CsvSource({
                "DEC, 255,       255",
                "DEC, -42,       -42",
                "BIN, 101,       5",
                "BIN, -101,      -5",
                "HEX, FF,        255",
                "HEX, -FF,       -255",
                "HEX, 7FFFFFFFFFFFFFFF, 9223372036854775807"
        })
        void parsesValidInput(NumBase base, String text, long expected) {
            assertEquals(expected, base.parse(text));
        }

        @Test
        @DisplayName("rejects digits that do not belong to the base")
        void rejectsInvalidDigits() {
            assertAll(
                    () -> assertThrows(NumberFormatException.class, () -> NumBase.BIN.parse("102")),
                    () -> assertThrows(NumberFormatException.class, () -> NumBase.DEC.parse("1A")));
        }

        @Test
        @DisplayName("rejects numbers that do not fit into long")
        void rejectsOverflow() {
            assertAll(
                    () -> assertThrows(NumberFormatException.class,
                            () -> NumBase.DEC.parse("9223372036854775808")),
                    () -> assertThrows(NumberFormatException.class,
                            () -> NumBase.HEX.parse("8000000000000000")));
        }
    }

    @Nested
    @DisplayName("isValidDigit()")
    class ValidDigits {

        @Test
        void decimalAcceptsOnly0to9() {
            for (char c : "0123456789".toCharArray()) {
                assertTrue(NumBase.DEC.isValidDigit(c), "DEC should accept " + c);
            }
            for (char c : "ABCDEF".toCharArray()) {
                assertFalse(NumBase.DEC.isValidDigit(c), "DEC should reject " + c);
            }
        }

        @Test
        void binaryAcceptsOnly0And1() {
            assertTrue(NumBase.BIN.isValidDigit('0'));
            assertTrue(NumBase.BIN.isValidDigit('1'));
            for (char c : "23456789ABCDEF".toCharArray()) {
                assertFalse(NumBase.BIN.isValidDigit(c), "BIN should reject " + c);
            }
        }

        @Test
        void hexAcceptsAllDigitsAndLettersAtoF() {
            for (char c : "0123456789ABCDEF".toCharArray()) {
                assertTrue(NumBase.HEX.isValidDigit(c), "HEX should accept " + c);
            }
            assertFalse(NumBase.HEX.isValidDigit('G'));
            assertFalse(NumBase.HEX.isValidDigit('-'));
        }
    }

    @Test
    @DisplayName("format then parse returns the original value in every base")
    void roundTrip() {
        long[] samples = {0, 1, 7, 255, 4096, -1, -255, Long.MAX_VALUE, Long.MIN_VALUE};
        for (NumBase base : NumBase.values()) {
            for (long value : samples) {
                assertEquals(value, base.parse(base.format(value)),
                        base + " round trip failed for " + value);
            }
        }
    }
}