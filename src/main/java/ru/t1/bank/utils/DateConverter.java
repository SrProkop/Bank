package ru.t1.bank.utils;

import ru.t1.bank.exceptions.IncorrectDataException;

import java.time.LocalDate;

public class DateConverter {

    public static LocalDate converterLocalDate(String date) throws IncorrectDataException {
        try {
            String[] splitDate = date.split("-");
            return LocalDate.of(
                    Integer.parseInt(splitDate[2]),
                    Integer.parseInt(splitDate[1]),
                    Integer.parseInt(splitDate[0]));
        } catch (Exception e) {
            throw new IncorrectDataException("You input incorrect date. " +
                    "Format date: DD-MM-YYYY");
        }
    }

}
