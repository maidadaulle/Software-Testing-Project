package views;

import Controllers.BookController;
import Models.*;
import Views.PrintBillView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.framework.junit5.ApplicationTest;
import java.time.LocalDate;

public class PrintBillViewTest extends ApplicationTest {

    private PrintBillView printBillView;
    private BookController bookController;
    private User currentUser;

    @BeforeEach
    public void setUp() {
        currentUser = new User(
                "John",
                "Doe",
                "adminUsername",
                Roles.Librarian,
                "adminPassword",
                5000.0,
                "987654321",
                LocalDate.of(1985, 5, 15),
                Gender.Male,
                0
        );
        printBillView = new PrintBillView(currentUser);
        bookController = new BookController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = printBillView.showView(stage);
        stage.setScene(scene);
        stage.show();

        printBillView = new PrintBillView(new User("test", "password", Roles.Librarian));
        stage.setScene(printBillView.showView(stage));
        stage.show();
    }

    @Test
    public void testAddBookToBill() {
        TextField isbnField = lookup("#isbnF").queryAs(TextField.class);
        TextField copiesField = lookup("#copiesF").queryAs(TextField.class);
        Button addButton = lookup("#addField").queryAs(Button.class);

        String isbn = "9781234567897";
        String copies = "2";

        clickOn(isbnField).write(isbn);
        clickOn(copiesField).write(copies);
        clickOn(addButton);

        Label totalPriceLabel = lookup("#totalPriceLabel").queryAs(Label.class);
        assertNotNull(totalPriceLabel);
        assertTrue(totalPriceLabel.isVisible());

        Label bookTitleLabel = lookup("#bookTitleLabel").queryAs(Label.class);
        assertNotNull(bookTitleLabel);
        assertTrue(bookTitleLabel.getText().contains(bookController.findBook(isbn).getBookTitle()));
    }

    @Test
    public void testInvalidIsbnFormat() {
        TextField isbnField = lookup("#isbnF").queryAs(TextField.class);
        String invalidIsbn = "invalidisbn";

        clickOn(isbnField).write(invalidIsbn);

        String fieldStyle = isbnField.getStyle();
        assertTrue(fieldStyle.contains("-fx-text-fill: red;"));
    }
}
