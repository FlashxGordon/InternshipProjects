package com.sa22.services.utils;

public class StringSplitter {

    /**
     * Method accepts string of data provided
     * and splits using the "_" delimiter
     * to produce an array of strings
     * @param stringOfData
     * @return Array of string data
     */

    public static String[] splitStringOfData(String stringOfData) {


        return stringOfData.split("_");

    }

}
