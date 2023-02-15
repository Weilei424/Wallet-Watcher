package test.java.unitTesting;


import org.junit.jupiter.api.Test;

import main.java.businessLogic.DateParser;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DateParserTester {

    Random random;

    private int getRandomInt(int min, int max) {
        random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    @Test
    public void testDateParsing() {
        String date = "2023-02-12";
        LocalDate localDate = DateParser.getDateFromString(date);
        assertEquals(2023, localDate.getYear());
        assertEquals(2, localDate.getMonthValue());
        assertEquals(12, localDate.getDayOfMonth());

        String date2 = "2023/02/12";
        LocalDate localDate2 = DateParser.getDateFromString(date2);
        assertEquals(2023, localDate2.getYear());
        assertEquals(2, localDate2.getMonthValue());
        assertEquals(12, localDate2.getDayOfMonth());

        String date3 = "2023-02-99";
        assertThrows(IllegalArgumentException.class, () -> DateParser.getDateFromString(date3));

        String date4 = "2023-Feb-12";
        LocalDate localDate4 = DateParser.getDateFromString(date4);
        assertEquals(2023, localDate4.getYear());
        assertEquals(2, localDate4.getMonthValue());
        assertEquals(12, localDate4.getDayOfMonth());

        String date5 = "2023-February-12";
        LocalDate localDate5 = DateParser.getDateFromString(date5);
        assertEquals(2023, localDate5.getYear());
        assertEquals(2, localDate5.getMonthValue());
        assertEquals(12, localDate5.getDayOfMonth());

        String date6 = "2023-NotAMonth-12";
        assertThrows(IllegalArgumentException.class, () -> DateParser.getDateFromString(date6));

        String date7 = "2023-13-12";
        assertThrows(IllegalArgumentException.class, () -> DateParser.getDateFromString(date7));

        String date8 = "12-02-2023";
        LocalDate localDate8 = DateParser.getDateFromString(date8);
        assertEquals(2023, localDate8.getYear());
        assertEquals(2, localDate8.getMonthValue());
        assertEquals(12, localDate8.getDayOfMonth());

        String date9 = "12-Feb-2023";
        LocalDate localDate9 = DateParser.getDateFromString(date9);
        assertEquals(2023, localDate9.getYear());
        assertEquals(2, localDate9.getMonthValue());
        assertEquals(12, localDate9.getDayOfMonth());

        String date10 = "12-15-2023";
        assertThrows(IllegalArgumentException.class, () -> DateParser.getDateFromString(date10));
    }

    @Test
    public void testValidDates() {
        assertTrue(DateParser.isDate("2023-02-12"));
        assertTrue(DateParser.isDate("2023/02/12"));
        assertFalse(DateParser.isDate("2023-02-99"));
        assertTrue(DateParser.isDate("2023-Feb-12"));
        assertTrue(DateParser.isDate("2023-February-12"));
        assertFalse(DateParser.isDate("2023-NotAMonth-12"));
        assertFalse(DateParser.isDate("2023-13-12"));
        assertTrue(DateParser.isDate("12-02-2023"));
        assertTrue(DateParser.isDate("12-Feb-2023"));
        assertFalse(DateParser.isDate("12-15-2023"));
    }

    @Test
    public void testRandomDates() {
        for (int i = 0; i < 10000; i++) {
            String date = getRandomInt(1000, 9999) + "-" + getRandomInt(1, 12) + "-" + getRandomInt(1, 28);
            assertTrue(DateParser.isDate(date));
        }
    }

}
