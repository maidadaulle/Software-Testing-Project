package Controllers.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import Controllers.BookController;
import Controllers.FileController;
import Models.Bill;
import Models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import Models.BillsType;
import Models.Author;
import Models.Category;
import Models.Gender;


class BookControllerTest {
    private BookController bookController;
    private Book book1, book2;

    @BeforeEach
    void setUp() {

        bookController = new BookController();
        FileController.books = new ArrayList<>();
    }

    // Boundary-Value Testing for price validation
    @Test
    @DisplayName("Boundary-Value-Testing-PRICEVALIDATION")
    void testPriceValidation_BoundaryValues() {
        assertTrue(bookController.priceValidation("0"));
        assertTrue(bookController.priceValidation("1"));
        assertTrue(bookController.priceValidation("2147483647"));

        assertFalse(bookController.priceValidation("-2147483649"));
        assertFalse(bookController.priceValidation("2147483648"));
        assertFalse(bookController.priceValidation("12.34"));
        assertFalse(bookController.priceValidation("abc"));
    }



    // Integration Test for createBook Method
    @Test
    @DisplayName("CreateBook- Integration Testing")
    void testCreateBook() {
        Author author = new Author("Ismail", "Kadare", Gender.Male);
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Fiction"));
        String ISBN = "123-456-789";
        String title = "The Great Novel";
        String supplier = "Famous Publisher";
        int purchasedPrice = 100;
        int originalPrice = 150;
        int sellingPrice = 120;
        String coverAddress = "coverImage.jpg";

        bookController.createBook(ISBN, title, author, categories, supplier, purchasedPrice, originalPrice, sellingPrice, coverAddress);

        assertEquals(1, FileController.books.size(), "The book should be added to the list.");
        Book createdBook = FileController.books.get(0);

        assertNotNull(createdBook, "The created book should not be null.");
        assertEquals(ISBN, createdBook.getISBN(), "The ISBN should match.");
        assertEquals(title, createdBook.getBookTitle(), "The book title should match.");
        assertEquals(author, createdBook.getAuthor(), "The author should match.");
        assertEquals(categories, createdBook.getBookCategories(), "The categories should match.");
        assertEquals(supplier, createdBook.getSupplier(), "The supplier should match.");
        assertEquals(purchasedPrice, createdBook.getPurchasedPrice(), "The purchased price should match.");
        assertEquals(originalPrice, createdBook.getOriginalPrice(), "The original price should match.");
        assertEquals(sellingPrice, createdBook.getSellingPrice(), "The selling price should match.");
        assertEquals(coverAddress, createdBook.getCover(), "The cover address should match.");
    }



}
