package com.sa22.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {

    /**
     * Method takes in as param a file path and
     * returns list of data
     * @param path
     * @return ListOfData
     */
    public static List<String> readAllData(String path) {

        List<String> ListOfData = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(path));){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ListOfData.add(line);
            }
        } catch (IOException ioException) {
            System.out.println("Cannot display data names. File not found. Please check fila path.");
        }
        return ListOfData;
    }

}



