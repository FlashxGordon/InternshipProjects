//package com.sa22.services.utils;
//
//import com.sa22.data.DataReader;
//import com.sa22.services.interfaces.CommonPaths;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class DisplayBeforeDate implements CommonPaths {
//
//    public static void displayBeforeDate(String dateInput,
//                                         int positionInStringArr) {
//
//
//        /**
//         * Method takes in dateInput (given by the user via console)
//         * and int corresponding to the position of the date in the string array
//         * depending on whether the date is a borrow date or a due date
//         * it prints on a new line all orders borrow/due after a certain date
//         * @param dateInput
//         * @param positionInStringArr
//         *
//         *
//         */
//
//        List<String> listOfDates = DataReader.readAllData(orderPath);
//        //Parse String parameter into LocalDate
//        LocalDate dateInputDate = LocalDate.parse(dateInput);
//        for (int i = 0; i < listOfDates.size(); i++) {
//            String[] splitDates = listOfDates.get(i).split("_");
//            if (LocalDate.parse(splitDates[positionInStringArr]).isAfter(dateInputDate)) {
//                System.out.println(listOfDates.get(i));
//            }
//        }
//    }
//}