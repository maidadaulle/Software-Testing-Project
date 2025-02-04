package Controllers;

import Models.*;
import javafx.scene.control.CheckBox;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class BookController {

    public void addBook(Book b) {
        FileController.books.add(b);
    }

    public void createBook(String ISBN, String title, Author author, ArrayList<Category> categories, String supplier,
            int purchasedPrice, int originalPrice, int sellingPrice, String address) {
        Book book = new Book(ISBN, title, author, categories, supplier, purchasedPrice, originalPrice, sellingPrice,
                address);
        addBook(book);
    }

    public Book findBook(String ISBN) {
        for (Book b : FileController.books) {
            if (b.getISBN().equals(ISBN))
                return b;
        }
        return null;
    }

    public boolean priceValidation(String x) {
        try {
            @SuppressWarnings("unused")
            int n = Integer.parseInt(x);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void verifyISBN(String ISBN) throws InvalidIsbnFormatException {
        if (!ISBN.matches("^(?=[0-9\\s]{17}$)97[89]\\s[0-9]{1,5}\\s[0-9]+\\s[0-9]+\\s[0-9]{1}$")) {
            throw new InvalidIsbnFormatException("Invalid ISBN format: " + ISBN);
        }
    }



    public boolean selectedCategory(ArrayList<CheckBox> c) {
        for (CheckBox cc : c) {
            if (cc.isSelected()) {
                return true;
            }
        }
        return false;
    }

    static boolean isSameDay(Date date1, Date date2) {
        // Convert both dates to LocalDate
        java.time.LocalDate localDate1 = date1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate localDate2 = date2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        // Compare the dates
        return localDate1.isEqual(localDate2);
    }

    public ArrayList<Book> getBooksSoldToday() {
        var result = new ArrayList<Book>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Sold) {
                for (var book : books) {
                    if (isSameDay(book.getPurchasedDate(), new Date())) {
                        result.add(book);
                        System.out.println(book);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Book> getBooksSoldThisMonth() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        var result = new ArrayList<Book>();
        var billList = FileController.transactions;
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Sold) {
                for (var book : books) {
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                        result.add(book);
                        System.out.println(book);
                    }
                }
            }
        }
        return result;
    }

    public static ArrayList<Book> getBooksSoldThisYear() {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        var result = new ArrayList<Book>();
        var billList = FileController.transactions;

        // Iterate through the transactions
        for (var bill : billList) {
            var books = bill.getBooks();
            if (bill.getType() == BillsType.Sold) { // Only consider sold books
                for (var book : books) {
                    System.out.println("Checking book: " + book.getBookTitle() + " purchased on: " + book.getPurchasedDate());
                    // Check if book was sold within the last 12 months
                    if (book.getPurchasedDate().toInstant().isAfter(beforeMonth.toInstant())) {
                        result.add(book); // Add book to result if within the last 12 months
                        System.out.println("Added book: " + book.getBookTitle());
                    }
                }
            }
        }
        return result;
    }


}