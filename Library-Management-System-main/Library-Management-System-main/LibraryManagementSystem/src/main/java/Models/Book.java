package Models;

import Controllers.FileController;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 4936174995967658928L;
    private String ISBN;
    private String bookTitle;
    private Author author;
    private ArrayList<Category> bookCategories;
    private String supplier;
    private Date purchasedDate; // date on the last stock
    private int purchasedPrice; // price per piece for retail
    private int originalPrice; // price per piece from the distributor
    private int sellingPrice; // library price per piece to sell
    private int stock; // current stock
    private String cover;

    public Book(String ISBN, String bookTitle, Author author, ArrayList<Category> bookCategories, String supplier,
            int purchasedPrice, int originalPrice, int sellingPrice, String address) {
        this.bookTitle = bookTitle;
        this.ISBN = ISBN;
        this.author = author;
        this.bookCategories = bookCategories;
        this.supplier = supplier;
        this.purchasedPrice = purchasedPrice;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.cover = address;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public ArrayList<Category> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(ArrayList<Category> bookCategories) {
        this.bookCategories = bookCategories;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate() {
        this.purchasedDate = new Date();
    }

    public int getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(int purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public static ArrayList<Book> booksBoughtToday() {
        ArrayList<Book> result = new ArrayList<>();
        var books = FileController.books;

        for (Book book : books) {
            LocalDate purchasedLocalDate = book.getPurchasedDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (purchasedLocalDate.equals(LocalDate.now())) {
                result.add(book);
            }
        }
        return result;
    }

    public static ArrayList<Book> booksBoughtThisMonth() {
        ArrayList<Book> result = new ArrayList<>();
        var books = FileController.books;

        int currentMonth = LocalDate.now().getMonthValue();

        for (Book book : books) {
            LocalDate purchasedLocalDate = book.getPurchasedDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (purchasedLocalDate.getMonthValue() == currentMonth) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "ISBN=" + ISBN + ", bookTitle=" + bookTitle + ", author=" + author + ", bookCategories="
                + bookCategories + ", supplier=" + supplier + ", purchasedDate=" + purchasedDate + ", purchasedPrice="
                + purchasedPrice + ", originalPrice=" + originalPrice + ", sellingPrice=" + sellingPrice + ", stock="
                + stock + ", cover=" + cover;
    }
}