package ru.t1.bank.utils;

import java.time.LocalDate;
import java.util.Optional;

public class DateConverter {

    public static Optional<LocalDate> converterLocalDate(String date) {
        try {
            String splitDate[] = date.split("-");
            LocalDate localDate = LocalDate.of(
                    Integer.parseInt(splitDate[3]),
                    Integer.parseInt(splitDate[1]),
                    Integer.parseInt(splitDate[0]));
            return Optional.of(localDate);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
