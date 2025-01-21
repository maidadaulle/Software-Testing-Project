package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Models.Bill;
import Models.BillsType;
import Models.Book;

public class BillController {

    private Bill createdBill;
    private static int billNumberCounter = 0; 


    public void addBill(Bill b) {
        FileController.transactions.add(b);
    }

    public void createBill(int ID, ArrayList<Book> books, ArrayList<Integer> quantity, int totalPrice, BillsType type) {
        billNumberCounter = 0;
        billNumberCounter++;

        Bill b = new Bill(ID, books, quantity, totalPrice, type);
        createdBill = b;
        printBill(b);
    }

    public Bill getCreatedBill() {
        return createdBill;
    }


    public void printBill(Bill b) {
        if (b == null) {
            throw new IllegalArgumentException("Bill cannot be null");
        }
        try {
            File print;
            String directoryPath;
            if (b.getType() == BillsType.Sold) {
                directoryPath = "Bills/soldBooks";
            } else {
                directoryPath = "Bills/boughtBooks";
            }

            print = new File(directoryPath + "/Bill" + b.getBillNumber() + ".txt");

            System.out.println("Bill " + b.getBillNumber() + " printed successfully.");
            System.out.println("Directory path: " + directoryPath);
            System.out.println("File path: " + print.getAbsolutePath());

            try (PrintWriter o = new PrintWriter(print)) {
                o.print(b);
                addBill(b);
                System.out.println(FileController.transactions.get(b.getBillNumber() - 1)); }


        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());}
    }

    public int stringToInt(String x) {
        int q;
        try {
            q = Integer.parseInt(x);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
            return q = -2;
        }
        if (q <= 0) {
            return -1;
        }
        return q;
    }



}
