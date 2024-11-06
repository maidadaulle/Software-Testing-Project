package Controllers;

import Models.BillsType;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import static Controllers.BookController.isSameDay;

public class IncomesController {

    public double getDailyIncome() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Sold && isSameDay(bill.getCreatedDate(),new Date())) {

                result += bill.getTotalPrice();
                // System.out.println(book);

            }
        }
        return result;
    }

    public double getMonthlyIncome() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Sold && bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant())) {

                result += bill.getTotalPrice();
                // System.out.println(book);

            }
        }
        return result;
    }

    public double getYearlyIncome() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Sold && bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant())) {

                        result += bill.getTotalPrice();
                        // System.out.println(book);

            }
        }
        return result;
    }

    public Integer numberOfBooksSoldToday() {
        Integer result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            int i  = 0;
            if (bill.getType() == BillsType.Sold) {
                for (var book : books) {
                    if (isSameDay(book.getPurchasedDate(), new Date())) {
                        result+=quantities.get(i);
                        i++;
                        // System.out.println(book);
                    }
                }
            }
        }
        return result;
    }

    public Integer numberOfBooksSoldThisMonth() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        Integer result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Sold) {
                int i = 0;
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                        result+=quantities.get(i);
                        i++;

                    }
                }
            }
        }
        return result;
    }

    public Integer numberOfBooksSoldThisYear() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        Integer result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Sold) {
                int i = 0;
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                        result+=quantities.get(i);
                        i++;
                        // System.out.println(book);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Integer> getBillsOfSoldBooksThisYear() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        var result = new ArrayList<Integer>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Sold) {
                int i = 0;
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant()))
                        result.add(quantities.get(i++));
                    // System.out.println(book);
                }
            }
        }
        return result;
    }
    public ArrayList<Integer> getBillsOfSoldBooksThisMonth() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        var result = new ArrayList<Integer>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Sold) {
                int i = 0;
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant()))
                        result.add(quantities.get(i++));
                    // System.out.println(book);
                }
            }
        }
        return result;
    }
    public ArrayList<Integer> getBillsOfSoldBooksToday() {

        var result = new ArrayList<Integer>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Sold) {
                int i = 0;
                for (var book : books) {
                    if (isSameDay(book.getPurchasedDate(),new Date()))
                        result.add(quantities.get(i++));
                    // System.out.println(book);
                }
            }
        }
        return result;
    }
}
