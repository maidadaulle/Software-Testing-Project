package views;

import Controllers.BillController;
import Controllers.BookController;
import Models.Author;
import Models.Book;
import Models.Category;
import Models.Gender;
import Views.PrintBillView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.util.WaitForAsyncUtils.sleep;

public class PrintBillViewTest extends ApplicationTest {

    private PrintBillView printBillView;
    private TextField totalPriceField;
    private TextField isbnField;
    private TextField copiesField;
    private TextField priceField;
    private Button addFieldButton;
    private Button finishTransactionButton;
    private Button backButton;

    private BookController bookController;
    private BillController billController;
    private ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    public void start(Stage stage) {
        // Set up mock controllers
        bookController = Mockito.mock(BookController.class);
        billController = Mockito.mock(BillController.class);

        // Set up the PrintBillView
        printBillView = new PrintBillView(null);
        stage.setScene(printBillView.showView(stage));
        stage.show();
    }

    @BeforeEach
    public void setup() {
        // Setup before each test
        totalPriceField = lookup("#totalPriceField").query();
        isbnField = lookup("#isbnField").query();
        copiesField = lookup("#copiesField").query();
        priceField = lookup("#priceField").query();
        addFieldButton = lookup("#addFieldButton").query();
        finishTransactionButton = lookup("#finishTransactionButton").query();
        backButton = lookup("#backButton").query();
        categories.add(new Category("Category 1"));
    }

    @Test
    public void testAddBookToBill() {
        // Setup mock behavior
        when(bookController.findBook("1234567890123")).thenReturn(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"",new Date(System.currentTimeMillis() / (24 * 60 * 60 * 1000L) * (24 * 60 * 60 * 1000L))));

        // Simulate entering ISBN, copies, and clicking Add button
        clickOn(isbnField).write("1234567890123");
        clickOn(copiesField).write("2");
        clickOn(addFieldButton);

        // Verify that total price field is updated
        assertTrue(totalPriceField.getText().length() > 0);
    }

    @Test
    public void testAddBookWithInvalidISBN() {
        // Setup mock behavior for invalid ISBN
        when(bookController.findBook("invalid-isbn")).thenReturn(null);

        // Simulate entering an invalid ISBN and clicking Add button
        clickOn(isbnField).write("invalid-isbn");
        clickOn(copiesField).write("2");
        clickOn(addFieldButton);

        // Check if an alert is triggered
        assertTrue(lookup(".alert").queryAll().size() > 0); // Verify that the alert is visible

        // Optionally, verify the alert content (error message)
        String alertMessage = lookup(".alert .header-text").queryText().getText();
        assertEquals("Check ISBN Field!", alertMessage);
    }

    @Test
    public void testAddBookWithEmptyCopies() {
        // Setup mock behavior for valid ISBN but empty copies
        when(bookController.findBook("1234567890123")).thenReturn(new Book("ISBN","Book ",new Author("name","surname", Gender.Male),categories,"",9,8,10,"",new Date(System.currentTimeMillis() / (24 * 60 * 60 * 1000L) * (24 * 60 * 60 * 1000L))));

        // Simulate entering valid ISBN but empty copies
        clickOn(isbnField).write("1234567890123");
        clickOn(copiesField).write("");
        clickOn(addFieldButton);

        // Check if the alert is triggered due to empty copies
        assertTrue(lookup(".alert").queryAll().size() > 0); // Verify that the alert is visible

        // Verify the error message
        String alertMessage = lookup(".alert .header-text").queryText().getText();
        assertEquals("Number of copies can't be empty!", alertMessage);
    }

    @Test
    public void testFinishTransaction() {
        // Setup mock behavior for the finish transaction action
        //when(billController.createBill(Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(true);

        // Simulate clicking the Finish Transaction button
        clickOn(finishTransactionButton);

        // Wait a little to allow alerts to appear
        sleep(500);

        // Verify if the alert for bill completion shows up
        assertTrue(lookup(".alert").queryAll().size() > 0); // Verify that the alert is visible

        // Optionally, check the message on the alert
        String alertMessage = lookup(".alert .header-text").queryText().getText();
        assertEquals("Bill Printed!", alertMessage);
    }

    @Test
    public void testBackButton() {
        // Simulate clicking the Back button
        clickOn(backButton);

        // Check if the user is redirected to the previous screen
        assertTrue(lookup("#previousScreen").query() != null); // Assuming the previous screen has this ID
    }
}
