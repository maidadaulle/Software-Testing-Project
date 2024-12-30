package Controllers.Test;

import Controllers.BookController;
import Controllers.FileController;
import Models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookControllerTest {
    private BookController bookController;
    private Book book1, book2;

    @BeforeEach
    void setUp() {
        bookController = new BookController();

        book1 = new Book("123-456-789", "Book One", null, null, "Supplier A", 100, 80, 120, "Address A");
        book1.setPurchasedDate();

        book2 = new Book("987-654-321", "Book Two", null, null, "Supplier B", 200, 150, 250, "Address B");
        book2.setPurchasedDate();

        FileController.books = new ArrayList<>();
        FileController.books.add(book1);
        FileController.books.add(book2);

        FileController.transactions = new ArrayList<>();
    }

    //BOUNDARY VALUE TESTING
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





}
