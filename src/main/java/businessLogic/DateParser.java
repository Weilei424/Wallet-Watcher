package businessLogic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {

    private final static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private final static ArrayList<DateFormat> dateFormats = new ArrayList<>();

    static {
        // init regex
        StringBuilder monthRegexSB = new StringBuilder("(");

        for (String month : months) {
            monthRegexSB.append(month).append("|").append(month, 0, 3).append("|");
        }

        monthRegexSB.deleteCharAt(monthRegexSB.length() - 1);
        monthRegexSB.append(")");

        String monthRegex = monthRegexSB.toString();

        Pattern YYYY_MM_DD = Pattern.compile("^(?<year>\\d{4})[-\\/](?<month>\\d{1,2})[-\\/](?<day>\\d{1,2})$");
        dateFormats.add(new DateFormat(YYYY_MM_DD, false));
        Pattern YYYY_MMM_DD = Pattern.compile("^(?<year>\\d{4})[-\\/](?<month>" + monthRegex + ")[-\\/](?<day>\\d{1,2})$");
        dateFormats.add(new DateFormat(YYYY_MMM_DD, true));
        Pattern DD_MM_YYYY = Pattern.compile("^(?<day>\\d{1,2})[-\\/](?<month>\\d{1,2})[-\\/](?<year>\\d{4})$");
        dateFormats.add(new DateFormat(DD_MM_YYYY, false));
        Pattern DD_MMM_YYYY = Pattern.compile("^(?<day>\\d{1,2})[-\\/](?<month>" + monthRegex + ")[-\\/](?<year>\\d{4})$");
        dateFormats.add(new DateFormat(DD_MMM_YYYY, true));
        Pattern MMM_DD_YYYY = Pattern.compile("(?<month>" + monthRegex + ") (?<day>\\d{1,2}),? ?(?<year>\\d{4})");
        dateFormats.add(new DateFormat(MMM_DD_YYYY, true));
    }

    public static LocalDate getDateFromString(String input) throws IllegalArgumentException {
        try {
            for (DateFormat dateFormat : dateFormats) {
                Matcher matcher = dateFormat.getPattern().matcher(input);
                if (matcher.matches()) {
                    int year = Integer.parseInt(matcher.group("year"));

                    int month;
                    if (dateFormat.isDateString()) {
                        month = getMonthFromString(matcher.group("month"));
                    } else {
                        month = Integer.parseInt(matcher.group("month"));
                    }

                    int day = Integer.parseInt(matcher.group("day"));

                    return LocalDate.of(year, month, day);
                }
            }
        } catch (Exception ignored) {}
        throw new IllegalArgumentException("Input is not a supported date");
    }

    public static boolean isDate(String input) {
        try {
            getDateFromString(input);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private static int getMonthFromString(String month) {
        for (int i = 0; i < months.length; i++) {
            if (month.equalsIgnoreCase(months[i]) || month.equalsIgnoreCase(months[i].substring(0, 3))) return i + 1;
        }
        throw new IllegalArgumentException("Not a valid month.");
    }


    private static class DateFormat {

        private final Pattern datePattern;
        private final boolean dateIsString;

        public DateFormat(Pattern datePattern, boolean dateIsString) {
            this.datePattern = datePattern;
            this.dateIsString = dateIsString;
        }

        public Pattern getPattern() {
            return datePattern;
        }

        public boolean isDateString() {
            return dateIsString;
        }

    }

}
