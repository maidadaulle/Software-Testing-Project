package Controllers.Test;

import Controllers.FileController;
import Controllers.StockController;
import Models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StockControllerTest {

    private StockController stockController;

    @BeforeEach
    void setUp() {
        stockController = new StockController();
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("12345", "Book 1", null, null, "Supplier 1", 10, 20, 30, "cover1", new Date()));
        books.add(new Book("67890", "Book 2", null, null, "Supplier 2", 10, 20, 30, "cover2", new Date()));
        FileController.books = books;
    }

    @Test
    public void testNeedRestockStockBelow5() {
        Book book = new Book("ISBN124", "Test Book 2", null, null, "Supplier", 10, 15, 20, "Cover2.jpg");
        book.setStock(4);
        FileController.books.add(book);
        ArrayList<String> restockList = stockController.needRestock();
        assertFalse(restockList.isEmpty(), "Books with stock below 5 should need restock.");
    }

    @Test
    public void testNeedRestockStockExactly5() {
        Book book = new Book("ISBN125", "Test Book 3", null, null, "Supplier", 10, 15, 20, "Cover3.jpg");
        book.setStock(5);  // Set stock to 5
        FileController.books.add(book);
        ArrayList<String> restockList = stockController.needRestock();
        assertFalse(restockList.isEmpty(), "Books with stock exactly 5 should need restock.");
    }

    @Test
    public void testNeedRestockStockAbove5() {
        Book book = new Book("ISBN126", "Test Book 4", null, null, "Supplier", 10, 15, 20, "Cover4.jpg");
        book.setStock(6);
        FileController.books.clear();
        FileController.books.add(book);
        System.out.println("Books in FileController: " + FileController.books.size());

        ArrayList<String> restockList = stockController.needRestock();
        System.out.println("Restock List: " + restockList);
        assertTrue(restockList.isEmpty(), "Books with stock above 5 should not need restock.");
    }


    @Test
    public void testUpdateStockAfterSoldEdgeCase() {
        Book book = new Book("ISBN126", "Test Book 4", null, null, "Supplier", 10, 15, 20, "Cover4.jpg");
        book.setStock(1);
        FileController.books.add(book);

        ArrayList<Book> booksToSell = new ArrayList<>();
        booksToSell.add(book);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);

        stockController.updateStockAfterSold(booksToSell, quantities);
        assertEquals(0, book.getStock(), "Stock should be 0 after selling 1 book.");
    }

    @Test
    public void testUpdateStockAfterSoldStockZero() {
        Book book = new Book("ISBN127", "Test Book 5", null, null, "Supplier", 10, 15, 20, "Cover5.jpg");
        book.setStock(0);
        FileController.books.add(book);

        ArrayList<Book> booksToSell = new ArrayList<>();
        booksToSell.add(book);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);

        stockController.updateStockAfterSold(booksToSell, quantities);
        assertEquals(0, book.getStock(), "Stock should remain 0 after attempting to sell from 0 stock.");
    }


    @Test
    public void testUpdateStockAfterBoughtInitialStock0() {
        Book book = new Book("ISBN128", "Test Book 6", null, null, "Supplier", 10, 15, 20, "Cover6.jpg");
        book.setStock(0);
        FileController.books.add(book);

        stockController.updateStockAfterBought(book, 10);
        assertEquals(10, book.getStock(), "Stock should be 10 after buying 10 books.");
    }

    @Test
    public void testUpdateStockAfterBoughtLargeQuantity() {
        Book book = new Book("ISBN129", "Test Book 7", null, null, "Supplier", 10, 15, 20, "Cover7.jpg");
        book.setStock(1000);
        FileController.books.add(book);

        stockController.updateStockAfterBought(book, 5000);
        assertEquals(6000, book.getStock(), "Stock should be 6000 after buying 5000 books.");
    }

    @Test
    public void testUpdateStockAfterBoughtNegativeQuantity() {
        Book book = new Book("ISBN130", "Test Book 8", null, null, "Supplier", 10, 15, 20, "Cover8.jpg");
        book.setStock(50);
        FileController.books.add(book);
        stockController.updateStockAfterBought(book, -10);

        assertEquals(50, book.getStock(), "Stock should remain unchanged when adding a negative quantity.");
    }

    @Test
    public void testUpdateStockAfterBoughtQuantityOverflow() {
        Book book = new Book("ISBN131", "Test Book 9", null, null, "Supplier", 10, 15, 20, "Cover9.jpg");
        book.setStock(Integer.MAX_VALUE - 1);
        FileController.books.add(book);
        stockController.updateStockAfterBought(book, 10);

        assertNotEquals(Integer.MIN_VALUE + 9, book.getStock(), "Stock should not overflow.");
        assertEquals(Integer.MAX_VALUE - 1, book.getStock(), "Stock should remain unchanged if an overflow is detected.");
    }

}
