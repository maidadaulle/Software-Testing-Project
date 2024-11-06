package Controllers;

import Models.BillsType;
import Models.Book;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import static Controllers.BookController.isSameDay;

public class CostsController {

    public ArrayList<Book> getBooksBoughtThisMonth() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        var result = new ArrayList<Book>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Bought) {
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant()))
                        result.add(book);
                    // System.out.println(book);
                }
            }
        }

        return result;
    }

    public ArrayList<Book> getBooksBoughtThisYear() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        var result = new ArrayList<Book>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Bought) {
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant()))
                        result.add(book);
                    // System.out.println(book);
                }
            }
        }
        return result;
    }
        public ArrayList<Integer> getBillsOfBoughtBooksThisYear() {
            Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
            var result = new ArrayList<Integer>();
            var billList = FileController.transactions;
            for (var bill : billList) {
                var books = bill.getBooks();
                var quantities = bill.getQuantity();
                if (bill.getType() == BillsType.Bought) {
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
    public ArrayList<Integer> getBillsOfBoughtBooksThisMonth() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        var result = new ArrayList<Integer>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Bought) {
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
    public ArrayList<Integer> getBillsOfBoughtBooksToday() {

        var result = new ArrayList<Integer>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Bought) {
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
    public ArrayList<Book> getBooksBoughtToday() {
        var result = new ArrayList<Book>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Bought) {
                for (var book : books) {
                    if (isSameDay(book.getPurchasedDate(), new Date())) {
                        result.add(book);
                        // System.out.println(book);
                    }
                }
            }
        }
        return result;
    }

    public double getDailyCost() {
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            if (bill.getType() == BillsType.Bought && isSameDay(bill.getCreatedDate(),new Date())) {
                        result += bill.getTotalPrice();
            }
        }
        return result;
    }

    public double getMonthlyCost() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            if (bill.getType() == BillsType.Bought && bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                result +=bill.getTotalPrice();

            }
        }
        return result;
    }

    public double getYearlyCost() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        double result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            if (bill.getType() == BillsType.Bought && bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                        result +=bill.getTotalPrice();

            }
        }
        return result;
    }

    public Integer numberOfBooksBoughtToday() {
        Integer result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            int i  = 0;
            if (bill.getType() == BillsType.Bought) {
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

    public Integer numberOfBooksBoughtThisMonth() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        Integer result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            int i = 0;
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Bought) {
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

    public Integer numberOfBooksBoughtThisYear() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        Integer result = 0;
        var billList = FileController.transactions;
        for (var bill : billList) {
            int i = 0;
            var books = bill.getBooks();
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Bought) {
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                        result+=quantities.get(i);
                        i++;
                        //System.out.println(book);
                    }
                }
            }
        }
        return result;
    }

}
