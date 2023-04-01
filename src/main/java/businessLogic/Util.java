package businessLogic;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import UI.ErrorPage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JTable;

import exceptions.InvalidDateException;

public final class Util {

	/**
	 * private constructor prevent from creation of object
	 */
	private Util() {
	}

	/**
	 * This method converts a JTable instance into a File instance.
	 * @param 	table is a JTable instance.
	 * @param 	file is a File instance that is converted from JTable.
	 */
	public static void exportToExcel(JTable table, File file) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Sheet 1");

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < table.getColumnCount(); i++) {
				Cell headerCell = headerRow.createCell(i);
				headerCell.setCellValue(table.getColumnName(i));
			}

			for (int i = 0; i < table.getRowCount(); i++) {
				Row dataRow = sheet.createRow(i + 1);
				for (int j = 0; j < table.getColumnCount(); j++) {
					Object value = table.getValueAt(i, j);
					Cell dataCell = dataRow.createCell(j);

					// Set cell value based on data type
					if (value instanceof String) {
						dataCell.setCellValue((String) value);
					} else if (value instanceof Number) {
						dataCell.setCellValue(((Number) value).doubleValue());
					} else {
						dataCell.setCellValue(value != null ? value.toString() : "");
					}
				}
			}

			try (FileOutputStream outputStream = new FileOutputStream(file)) {
				workbook.write(outputStream);
			}
			System.out.println("Table data exported to Excel file successfully.");
		} catch (IOException e) {
			new ErrorPage(e);
			e.printStackTrace();
		} finally {
			//workbook.close();
		}
	}

	/**
	 * This method calculates the number of month difference of input dates.
	 * 
	 * @param beginAt is a Date object that is the beginning date.
	 * @param endAt   is a Date object that is the ending date.
	 * @return integer number of month difference.
	 */
	public static int calcMonth(LocalDate beginAt, LocalDate endAt) {
		try {
			if (ChronoUnit.DAYS.between(beginAt, endAt) < 0)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			new ErrorPage("Invalid date.", e);
			System.out.println("invalid date");
		}
		int year = endAt.getYear() - beginAt.getYear();
		int month = endAt.getMonthValue() - beginAt.getMonthValue();

		return 12 * year + month;
	}

	/**
	 * This method calculates the number of years difference of input dates.
	 * 
	 * @param beginAt is a LocalDate object that is the beginning date.
	 * @param endAt   is a LocalDate object that is the ending date.
	 * @return integer number of years difference.
	 */
	public static int calcYear(LocalDate beginAt, LocalDate endAt) {
		try {
			if (ChronoUnit.DAYS.between(beginAt, endAt) < 0)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			new ErrorPage("Invalid date.", e);
			System.out.println("invalid date");
		}
		int year = endAt.getYear() - beginAt.getYear();

		return year;
	}

	/**
	 * This method calculates the number of biweeks difference of input dates.
	 * 
	 * @param beginAt is a LocalDate object that is the beginning date.
	 * @param endAt   is a LocalDate object that is the ending date.
	 * @return integer number of years difference.
	 */
	public static int calcBiweek(LocalDate beginAt, LocalDate endAt) {
		try {
			if (ChronoUnit.DAYS.between(beginAt, endAt) < 0)
				throw new InvalidDateException();
		} catch (IllegalArgumentException e) {
			new ErrorPage("Invalid date.", e);
			System.out.println("invalid date");
		}
		int biweeks = (Util.getWeek(endAt) - Util.getWeek(beginAt) + calcYear(beginAt, endAt) * 52) / 2;

		return biweeks;
	}

	/**
	 * Returns the number of week since January 1, 1970, 00:00:00 GMTrepresented by
	 * this Date .
	 * 
	 * @param date is the current date.
	 * @return the number of weeks.
	 */
	private static int getWeek(LocalDate date) {
		final int CONVENTION = 7;
		int week = date.getDayOfYear() / CONVENTION;
		return week;
	}

	/**
	 * This method is used for salt + hash encrypt Technique.
	 * 
	 * @param password is user's password.
	 * @param salt     is a string generated randomly.
	 * @return the encrypted password in string.
	 */
	public static String encrypt(String password, String salt) {
		return "" + (password + salt).hashCode();
	}
}
