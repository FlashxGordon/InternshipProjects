package com.sa22.services;

import com.sa22.data.DataReader;
import com.sa22.data.DataWriter;
import com.sa22.entities.Order;
import com.sa22.services.interfaces.CommonPaths;
import com.sa22.services.interfaces.OrderServiceInterface;
import com.sa22.services.interfaces.ScannerInterface;
import com.sa22.services.utils.StringSplitter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderServiceInterface, CommonPaths, ScannerInterface {
    @Override
    public List<Order> mapOrder(String filePath) {

        List<Order> allOrders = new ArrayList<>();
        List<String> mapOrder = DataReader.readAllData(orderPath);
        for (String s : mapOrder) {
            String[] splitElementArr = s
                    .replace("[", "")
                    .replace("]", "")
                    .split("_");
            Order order = new Order(splitElementArr[0], splitElementArr[1],
                    LocalDate.parse(splitElementArr[2]), LocalDate.parse(splitElementArr[3]),
                    LocalDate.parse(splitElementArr[4]), splitElementArr[5]);
            allOrders.add(order);
        }

        return allOrders;
    }


    public String getBorrowDate() {

        return LocalDate.now().toString();
    }


    public String getDueDate() {

        LocalDate dueDate = LocalDate.now().plusDays(30);

        return String.valueOf(dueDate);
    }

    public void createOrderByBook() {


    }

    private void populateClientList(List<String> orderList, String clientIdChoice) {
        List<String> clientIdList = DataReader.readAllData(clientPath);

        for (int i = 0; i < clientIdList.size(); i++) {
            //StringSplitter class used to split the string at "_"
            String[] tempArr = StringSplitter.splitStringOfData(clientIdList.get(i));
            if (clientIdChoice.equals(tempArr[0]) && Integer.parseInt(clientIdChoice) <= clientIdList.size()) {
                orderList.add(clientIdList.get(i));
            }
        }
    }

    private void populateBookList(List<String> orderList, String bookIdChoice) {
        List<String> inventoryIdList = DataReader.readAllData(bookPath);

        for (int i = 0; i < inventoryIdList.size(); i++) {
            //StringSplitter class used to split the string at "_"
            String[] tempArr = StringSplitter.splitStringOfData(inventoryIdList.get(i));
            if (bookIdChoice.equals(tempArr[0]) && Integer.parseInt(bookIdChoice) <= inventoryIdList.size()) {
                orderList.add(inventoryIdList.get(i));
                orderList.add(getBorrowDate());
                orderList.add(getDueDate());
            }
        }
    }
}