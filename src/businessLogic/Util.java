package businessLogic;

import java.util.Date;

import exceptions.InvalidDateException;

public final class Util {
	
	/**
	 * private constructor prevent from creation of object
	 */
	private Util() {}
	
	/**
	 * This method calculates the number of month difference of input dates.
	 * @param beginAt	is a Date object that is the beginning date.
	 * @param endAt		is a Date object that is the ending date.
	 * @return			integer number of month difference.
	 */
	@SuppressWarnings("deprecation")
	public static int calcMonth(Date beginAt, Date endAt) {
		try {
			boolean flag = beginAt.getTime() - endAt.getTime() >= 0;
			if (flag)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			System.out.println("invalid date");
		}
		int year = endAt.getYear() - beginAt.getYear();
		int month = endAt.getMonth() - beginAt.getMonth();
		
		return 12 * year + month;
	}
	
	/**
	 * This method calculates the number of years difference of input dates.
	 * @param beginAt	is a Date object that is the beginning date.
	 * @param endAt		is a Date object that is the ending date.
	 * @return			integer number of years difference.
	 */
	@SuppressWarnings("deprecation")
	public static int calcYear(Date beginAt, Date endAt) {
		try {
			boolean flag = beginAt.getYear() - endAt.getYear() >= 0;
			if (flag)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			System.out.println("invalid date");
		}
		int year = endAt.getYear() - beginAt.getYear();
		
		return year;
	}
	
	/**
	 * This method calculates the number of biweeks difference of input dates.
	 * @param beginAt	is a Date object that is the beginning date.
	 * @param endAt		is a Date object that is the ending date.
	 * @return			integer number of years difference.
	 */
	@SuppressWarnings("deprecation")
	public static int calcBiweek(Date beginAt, Date endAt) {
		try {
			boolean flag = beginAt.getYear() - endAt.getYear() >= 0;
			if (flag)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			System.out.println("invalid date");
		}
		int biweeks = (Util.getWeek(endAt) -Util.getWeek(beginAt)) / 2;
		
		return biweeks;
	}
	
	/**
	 * Returns the number of week since January 1, 1970, 00:00:00 GMTrepresented by this Date .
	 * @param date	is the current date.
	 * @return the number of weeks.
	 */
	private static int getWeek(Date date) {
		final double CONVENTION = 6.048e+8;
		int week = (int) (date.getTime() / CONVENTION);
		return week;
	}
}
