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

class BookControllerTest {
    private BookController bookController;
    private Book book1, book2;

    @BeforeEach
    void setUp() {

        bookController = new BookController();
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


}
