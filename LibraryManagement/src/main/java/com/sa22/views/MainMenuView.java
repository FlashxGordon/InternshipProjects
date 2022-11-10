package com.sa22.views;

import com.sa22.data.DataReader;
import com.sa22.data.PsqlConnection;
import com.sa22.exceptions.WrongChoiceException;
import com.sa22.services.OrderServiceImpl;
import com.sa22.services.interfaces.CommonPaths;
import com.sa22.services.interfaces.ScannerInterface;

import java.util.List;

public class MainMenuView implements ScannerInterface, CommonPaths {

    public static final OrderServiceImpl order = new OrderServiceImpl();
    public static void selectChoice() throws WrongChoiceException {

        System.out.println("Hi there! Please select: ");
        System.out.println("1. Create order by book");
        System.out.println("2. Create order by author");
        System.out.println("3. Add Book");
        System.out.println("4. Display all orders");
        System.out.println("5. Display orders by client");
        System.out.println("6. Display orders before borrow date");
        System.out.println("7. Display orders after borrow date");
        System.out.println("8. Display orders on borrow date");
        System.out.println("9. Display orders before due date");
        System.out.println("10. Display orders after due date");
        System.out.println("11. Display orders on due date");
        System.out.println("12. Exit program.");
        int choice = Integer.parseInt(scanner.nextLine());
        checkChoice(choice);

    }

    public static void checkChoice(int choice) throws WrongChoiceException {

        if (choice > 11 || choice < 1) {
            throw new WrongChoiceException("\n" + "Invalid choice. Program terminated.");
        } else {
            switch (choice) {
                case 1:
                    PsqlConnection.connectToDB("SELECT * FROM client_table","client_name");
                    break;
                case 2:
;
//                case 3:
//                    displayAllOrders.displayAllOrders();
//                    break;
//                case 4:
//                    displayOrdersByClient.displayOrderByClient();
//                    break;
//                case 5:
//                    beforeBorrow.beforeBorrow();
//                    break;
//                case 6:
//                    afterBorrow.afterBorrow();
//                    break;
//                case 7:
//                    onBorrowDate.onBorrowDate();
//                    break;
//                case 8:
//                    beforeDueDate.beforeDueDate();
//                    break;
//                case 9:
//                    afterDueDate.afterDueDate();
//                    break;
//                case 10:
//                    onDueDate.onDueDate();
//                    break;
                case 11:
                    System.out.println("Sayonara!");
                    System.exit(0);
                    break;
            }
        }
    }
}