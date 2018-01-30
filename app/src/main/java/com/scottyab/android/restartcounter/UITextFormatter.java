package com.scottyab.android.restartcounter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Place where all UI formatted strings should be done.
 * 
 * @author scottab
 * 
 */
public final class UITextFormatter {

	private static final DateFormat sDateFormat = new SimpleDateFormat(
			"dd-MM-yyyy", Locale.getDefault());

	private static final DateFormat sDateTimeFormat = new SimpleDateFormat(
			"HH:mm:ss dd-MM-yyyy", Locale.getDefault());

	/**
	 * Server time format 2014-01-01T10:11:46Z!
	 */
	private static final DateFormat sServerDateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

	private static final DateFormat sDateTimeFriendlyFormat = new SimpleDateFormat(
			"dd MMM HH:mm zzz", Locale.getDefault());

	private static final DecimalFormat sNumberFormat = new DecimalFormat("0.00");

	/**
	 * Format to date (no time is present)
	 * 
	 * @param date
	 * @return 12-03-2013
	 */
	public static String formatDate(final long date) {
		return sDateFormat.format(date);
	}

	public static String formatFriendlyDateTime(final long date) {
		return sDateTimeFriendlyFormat.format(date);
	}

	/**
	 * Returns formatted date and time
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(final long date) {
		return sDateTimeFormat.format(date);
	}

	public static String formatServerDateTime(final long date) {
		return sServerDateTimeFormat.format(date);
	}

	/**
	 * format to 2dp ie. 0.00
	 * 
	 * @param value
	 * @return
	 */
	public static String formatDecimal(final double value) {
		return sNumberFormat.format(value);
	}

	/**
	 * format to 2dp ie. 0.00
	 * 
	 * @param value
	 *            example "5.2"
	 * @return example 5.20
	 */
	public static String formatDecimal(final String value) {
		double myDouble = Double.valueOf(value);
		return formatDecimal(myDouble);
	}

	/**
	 * Formats millis to hrs, mins, secs, if a field is blank it (i.e 0 hrs) it
	 * will not be returned
	 * 
	 * @param milliseconds
	 * @return 3hrs, 2mins,
	 */
	public static String formatTimeDuration(long milliseconds) {

		int seconds = Math.round(milliseconds / 1000);
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;

		StringBuilder builder = new StringBuilder();
		// //only display if there is something to show
		if (hours > 0) {
			builder.append(hours + " hrs");
		}
		if (minutes > 0) {
			builder.append(minutes + " mins");
		}
		if (seconds > 0) {
			builder.append(seconds + " secs");
		}

		return builder.toString();
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	private UITextFormatter() {
	}
}
