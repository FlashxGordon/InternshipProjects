package com.sa22.data;

import com.sa22.services.interfaces.ScannerInterface;

import java.time.LocalDate;

public class DateReader implements ScannerInterface {

    /**
     * Method takes in as a param the input from the user
     * as a String and converts into a LocalDate
     *
     * @param userDateInput
     * @return date
     */

    public LocalDate getDateInput(String userDateInput) {
        //Maybe move the user prompt into the OrderViews class
        System.out.println("Date format: YYYY/MM/DD (Example: 1996-05-18)");
        LocalDate date = null;
        try {
            date = LocalDate.parse(userDateInput);
        } catch (Exception dataFormatException) {
            System.out.println("Wrong date format. Try again.");
            System.exit(0);

        }
        return date;
    }


}
