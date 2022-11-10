package com.sa22.data;

import com.sa22.services.interfaces.CommonPaths;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DataWriter implements CommonPaths {

    /**
     * Method takes in filePath and a List of data and
     * deposits the data inside the filePath
     *
     * @param filePath
     * @param DataList
     */

    public static void writeData(String filePath, List<String> DataList) {

        String stringList = Arrays.deepToString(new List[]{DataList})
                .replace("[", "")
                .replace("]","");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            writer.append(' ');
            writer.newLine();
            writer.append(stringList);
        } catch (IOException ioException) {
            System.out.println("Cannot write to file. File not found. Please check file path.");
        }
    }
}