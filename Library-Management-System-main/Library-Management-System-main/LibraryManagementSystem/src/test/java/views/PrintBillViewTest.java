package views;

import Views.PrintBillView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrintBillViewTest extends ApplicationTest {

    private PrintBillView printBillView;
    private TextField totalPriceField;
    private TextField isbnField;
    private TextField copiesField;
    private TextField priceField;
    private Button addFieldButton;
    private Button finishTransactionButton;
    private Button backButton;

    @Override
    public void start(Stage stage) {
        // Setup the scene with the PrintBillView
        printBillView = new PrintBillView(null); // Pass the mock or actual user if needed
        stage.setScene(printBillView.showView(stage));
        stage.show();
    }

    @Test
    public void testAddBookToBill() {
        totalPriceField = lookup("#totalPriceField").query();
        isbnField = lookup("#isbnField").query();
        copiesField = lookup("#copiesField").query();
        priceField = lookup("#priceField").query();
        addFieldButton = lookup("#addFieldButton").query();

        // Simulate entering ISBN, copies and clicking Add
        clickOn(isbnField).write("1234567890123");
        clickOn(copiesField).write("2");
        clickOn(addFieldButton);

        // Verify total price field is updated
        assertTrue(totalPriceField.getText().length() > 0);
    }

    @Test
    public void testFinishTransaction() {
        finishTransactionButton = lookup("#finishTransactionButton").query();

        // Simulate clicking Finish Transaction
        clickOn(finishTransactionButton);

        // Check if an alert is triggered (this would typically be checked in a more sophisticated test setup)
    }

    @Test
    public void testBackButton() {
        backButton = lookup("#backButton").query();

        // Simulate clicking Back
        clickOn(backButton);

        // Check if the scene changes or any other behavior happens
    }
}
