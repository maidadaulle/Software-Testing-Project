package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Models.Bill;
import Models.BillsType;
import Models.Book;

public class BillController {

    public void addBill(Bill b) {
        FileController.transactions.add(b);
    }

    public void createBill(int ID, ArrayList<Book> books, ArrayList<Integer> quantity, int totalPrice, BillsType type) {
        Bill b = new Bill(ID, books, quantity, totalPrice, type);
        printBill(b);
    }

    public void printBill(Bill b) {
        try {
            File print;
            if (b.getType() == BillsType.Sold) {
                print = new File("Bills/soldBooks/Bill" + b.getBillNumber() + ".txt");
            } else {
                print = new File("Bills/boughtBooks/Bill" + b.getBillNumber() + ".txt");
            }
            try (
                PrintWriter o = new PrintWriter(print);) {
                o.print(b);
                addBill(b);
                System.out.println(FileController.transactions.get(b.getBillNumber()-1));
            }
            System.out.println("Bill " + b.getBillNumber() + " printed successfully.");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e1) {
            System.out.println(e1.getMessage());
        }
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
