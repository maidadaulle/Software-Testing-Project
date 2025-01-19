package Models;

import Controllers.FileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("12345", "Test Book", null, null, "Supplier", 10, 15, 20, "cover.jpg");
        FileController.books = new ArrayList<>();
    }

    @Test
    void testConstructorWithoutPurchasedDate() {
        Book testBook = new Book("54321", "Book1", null, null, "Supplier A", 5, 10, 15, "testCover.jpg");

        assertEquals("54321", testBook.getISBN(), "ISBN should be properly initialized.");
        assertEquals("Book1", testBook.getBookTitle(), "Book title should be properly initialized.");
        assertNull(testBook.getAuthor(), "Author should be null when not provided.");
        assertNull(testBook.getBookCategories(), "Book categories should be null when not provided.");
        assertEquals("Supplier A", testBook.getSupplier(), "Supplier should be properly initialized.");
        assertEquals(5, testBook.getPurchasedPrice(), "Purchased price should be properly initialized.");
        assertEquals(10, testBook.getOriginalPrice(), "Original price should be properly initialized.");
        assertEquals(15, testBook.getSellingPrice(), "Selling price should be properly initialized.");
        assertEquals("testCover.jpg", testBook.getCover(), "Cover should be properly initialized.");
        assertNull(testBook.getPurchasedDate(), "Purchased date should be null when not provided.");
    }

    @Test
    void testConstructorWithPurchasedDate() {
        Date testDate = new Date();
        Book testBook = new Book("54321", "Book2", null, null, "Supplier B", 5, 10, 15, "testCover.jpg", testDate);

        assertEquals("54321", testBook.getISBN(), "ISBN should be properly initialized.");
        assertEquals("Book2", testBook.getBookTitle(), "Book title should be properly initialized.");
        assertNull(testBook.getAuthor(), "Author should be null when not provided.");
        assertNull(testBook.getBookCategories(), "Book categories should be null when not provided.");
        assertEquals("Supplier B", testBook.getSupplier(), "Supplier should be properly initialized.");
        assertEquals(5, testBook.getPurchasedPrice(), "Purchased price should be properly initialized.");
        assertEquals(10, testBook.getOriginalPrice(), "Original price should be properly initialized.");
        assertEquals(15, testBook.getSellingPrice(), "Selling price should be properly initialized.");
        assertEquals("testCover.jpg", testBook.getCover(), "Cover should be properly initialized.");
        assertEquals(testDate, testBook.getPurchasedDate(), "Purchased date should be properly initialized.");
    }

    @Test
    void testGetSetISBN() {
        book.setISBN("98765");
        assertEquals("98765", book.getISBN(), "ISBN should be updated correctly.");
    }

    @Test
    void testGetSetBookTitle() {
        book.setBookTitle("book");
        assertEquals("book", book.getBookTitle(), "Book title should be updated correctly.");
    }

    @Test
    void testGetSetBookCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Fiction"));
        book.setBookCategories(categories);
        assertEquals(categories, book.getBookCategories(), "Book categories should be updated correctly.");
    }

    @Test
    void testGetSetSupplier() {
        book.setSupplier("Supplier c");
        assertEquals("Supplier c", book.getSupplier(), "Supplier should be updated correctly.");
    }


    @Test
    void testGetSetPurchasedPrice() {
        book.setPurchasedPrice(25);
        assertEquals(25, book.getPurchasedPrice(), "Purchased price should be updated correctly.");
    }

    @Test
    void testGetSetOriginalPrice() {
        book.setOriginalPrice(30);
        assertEquals(30, book.getOriginalPrice(), "Original price should be updated correctly.");
    }


    @Test
    void testGetSetSellingPrice() {
        book.setSellingPrice(35);
        assertEquals(35, book.getSellingPrice(), "Selling price should be updated correctly.");
    }


    @Test
    void testGetSetStock() {
        book.setStock(100);
        assertEquals(100, book.getStock(), "Stock should be updated correctly.");
    }

    @Test
    void testSetStockValidPositive() {
        book.setStock(10);
        assertEquals(10, book.getStock(), "Stock should be updated to 10.");
    }

    @Test
    void testSetStockValidZero() {
        book.setStock(0);
        assertEquals(0, book.getStock(), "Stock should be updated to 0.");
    }
    @Test
    void testSetCover() {
        String newCover = "newCover.jpg";
        book.setCover(newCover);
        assertEquals(newCover, book.getCover(), "The cover address should be updated.");
    }

    @Test
    void testSetAuthor() {
        Author newAuthor = new Author("George", "Orwell", Gender.Male);
        book.setAuthor(newAuthor);
        assertEquals(newAuthor, book.getAuthor(), "The author should be updated.");
    }

    @Test
    void testSetStockInvalidNegative() {
        book.setStock(-5);
        assertEquals(0, book.getStock(), "Stock should not be updated to a negative value.");
    }

    @Test
    void testSetPurchasedDateValid() {
        book.setPurchasedDate();
        Date today = new Date();
        assertEquals(today.toString(), book.getPurchasedDate().toString(), "Purchased date should be set to today.");
    }

    //Integration
    @Test
    void testBooksBoughtToday() {
        Book bookToday = new Book("11111", "Book", null, null, "Supplier", 10, 15, 20, "cover.jpg", new Date());
        FileController.books.add(bookToday);

        Date pastDate = Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Book bookPast = new Book("22222", "Book 2", null, null, "Supplier", 10, 15, 20, "cover.jpg", pastDate);
        FileController.books.add(bookPast);

        ArrayList<Book> todayBooks = Book.booksBoughtToday();
        assertEquals(1, todayBooks.size(), "Only books bought today should be returned.");
        assertEquals("Book", todayBooks.get(0).getBookTitle());
    }

    //Integration
    @Test
    void testBooksBoughtThisMonth() {
        FileController.books.clear();

        Book bookThisMonth = new Book("33333", "This Month Book", null, null, "Supplier", 10, 15, 20, "cover.jpg", new Date());
        FileController.books.add(bookThisMonth);
        ArrayList<Book> monthBooks = Book.booksBoughtThisMonth();

        assertEquals(1, monthBooks.size(), "Only books bought this month should be returned.");
        assertEquals("This Month Book", monthBooks.get(0).getBookTitle());
    }


    @Test
    void testToString() {
        String expected = "ISBN=12345, bookTitle=Test Book, author=null, bookCategories=null, supplier=Supplier, purchasedDate=null, purchasedPrice=10, originalPrice=15, sellingPrice=20, stock=0, cover=cover.jpg";
        assertEquals(expected, book.toString(), "The toString() method should return a properly formatted string.");
    }
}
