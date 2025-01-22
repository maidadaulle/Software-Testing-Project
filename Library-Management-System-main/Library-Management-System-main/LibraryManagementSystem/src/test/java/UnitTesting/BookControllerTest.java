package UnitTesting;

import Controllers.BookController;
import Controllers.FileController;
import Models.*;
import javafx.scene.control.CheckBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookControllerTest {
    private BookController bookController;
    private FileController fileControllerMock;

    @BeforeEach
    void setUp() {
        bookController = new BookController();
        fileControllerMock = mock(FileController.class);
        FileController.books = new ArrayList<>();
        FileController.transactions = new ArrayList<>();
    }

    @Test
    void testAddBook() {
        Book book = mock(Book.class);
        bookController.addBook(book);

        assertEquals(1, FileController.books.size());
        assertTrue(FileController.books.contains(book));
    }

    @Test
    void testCreateBook() {
        Book book = mock(Book.class);
        Author author = mock(Author.class);
        ArrayList<Category> categories = new ArrayList<>();
        bookController.createBook("12345", "Test Book", author, categories, "Supplier", 100, 200, 300, "Address");

        assertEquals(1, FileController.books.size());
        assertNotNull(bookController.findBook("12345"));
    }

    @Test
    void testFindBook() {
        Book book = mock(Book.class);
        when(book.getISBN()).thenReturn("12345");
        bookController.addBook(book);

        Book foundBook = bookController.findBook("12345");
        assertNotNull(foundBook);
        assertEquals(book, foundBook);

        Book notFoundBook = bookController.findBook("54321");
        assertNull(notFoundBook);
    }

    @Test
    void testPriceValidation() {
        assertTrue(bookController.priceValidation("100"));
        assertFalse(bookController.priceValidation("abc"));
    }

    @Test
    void testVerifyISBN() {
        assertDoesNotThrow(() -> bookController.verifyISBN("978 0 393 04002 9"));
        assertThrows(InvalidIsbnFormatException.class, () -> bookController.verifyISBN("12345"));
    }


    @Test
    void testSelectedCategory() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        CheckBox unchecked = new CheckBox();
        CheckBox checked = new CheckBox();

        unchecked.setSelected(false);
        checked.setSelected(true);

        checkBoxes.add(unchecked);
        assertFalse(bookController.selectedCategory(checkBoxes));

        checkBoxes.add(checked);
        assertTrue(bookController.selectedCategory(checkBoxes));
    }

    @Test
    void testGetBooksSoldToday() {
        // Setup mock transactions and books
        Bill transaction = mock(Bill.class);
        Book book = mock(Book.class);
        when(book.getPurchasedDate()).thenReturn(new Date());
        when(transaction.getBooks()).thenReturn(new ArrayList<>(Collections.singletonList(book))); // Fix here
        when(transaction.getType()).thenReturn(BillsType.Sold);

        FileController.transactions.add(transaction);

        ArrayList<Book> booksSoldToday = bookController.getBooksSoldToday();
        assertEquals(1, booksSoldToday.size());
        assertTrue(booksSoldToday.contains(book));
    }

    @Test
    void testGetBooksSoldThisMonth() {
        // Similar setup as testGetBooksSoldToday
        Date withinLastMonth = Date.from(ZonedDateTime.now().minusDays(15).toInstant());
        Bill transaction = mock(Bill.class);
        Book book = mock(Book.class);
        when(book.getPurchasedDate()).thenReturn(withinLastMonth);
        when(transaction.getBooks()).thenReturn(new ArrayList<>(Collections.singletonList(book))); // Fix here
        when(transaction.getType()).thenReturn(BillsType.Sold);

        FileController.transactions.add(transaction);

        ArrayList<Book> booksSoldThisMonth = bookController.getBooksSoldThisMonth();
        assertEquals(1, booksSoldThisMonth.size());
        assertTrue(booksSoldThisMonth.contains(book));
    }

    @Test
    void testGetBooksSoldThisYear() {
        // Similar setup as testGetBooksSoldThisMonth
        Date withinLastYear = Date.from(ZonedDateTime.now().minusMonths(6).toInstant());
        Bill transaction = mock(Bill.class);
        Book book = mock(Book.class);
        when(book.getPurchasedDate()).thenReturn(withinLastYear);
        when(transaction.getBooks()).thenReturn(new ArrayList<>(Collections.singletonList(book))); // Fix here
        when(transaction.getType()).thenReturn(BillsType.Sold);

        FileController.transactions.add(transaction);

        ArrayList<Book> booksSoldThisYear = BookController.getBooksSoldThisYear();
        assertEquals(1, booksSoldThisYear.size());
        assertTrue(booksSoldThisYear.contains(book));
    }
}
