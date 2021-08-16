package com.testtask.delivery.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Утилитный класс для работы с датами.
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * Преобразование строки формата гггг-мм-дд в дату.
     * @param date дата в строковом формате.
     * @return дата в формате LocalDate, null в случае неудачи.
     */
    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
