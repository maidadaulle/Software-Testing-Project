package views;

import Controllers.*;
import Models.*;
import Views.AddStockView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddStockViewTest extends ApplicationTest {

    private AddStockView addStockView;
    private Stage stage;
    private StockController stockController;
    private BookController bookController;
    private User mockUser;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getUsername()).thenReturn("rexhens");
        Mockito.when(mockUser.getPassword()).thenReturn("Rexhens1@");
        Mockito.when(mockUser.getUserRole()).thenReturn(Roles.Manager);

        bookController = Mockito.mock(BookController.class);
        stockController = Mockito.mock(StockController.class);

        addStockView = new AddStockView(mockUser, stage);
        stage.setScene(addStockView.showView());
        stage.show();
    }

    private void enterText(String query, String text) {
        TextField field = lookup(query).query();
        clickOn(field).write(text);
    }

    private Label getLabel(String query) {
        return lookup(query).query();
    }

    @Test
    public void testAutoSearchOnIsbnEntry() {
        when(bookController.findBook("978 3 16 148410 0")).thenReturn(new Book());

        enterText("#isbnTextField", "978 3 16 148410 0");
        WaitForAsyncUtils.waitForFxEvents();

        Label searchMessage = getLabel("#searchMessageLabel");

        assertNotNull(searchMessage, "The search message label should not be null");
        assertEquals("Book information loaded successfully!", searchMessage.getText(), "The success message text is incorrect");
    }

    @Test
    public void testAddStockFailure_InvalidIsbn() {
        enterText("#isbnTextField", "invalid_isbn");
        WaitForAsyncUtils.waitForFxEvents();

        Label errorMessage = getLabel("#searchMessageLabel");
        assertNotNull(errorMessage);
        assertEquals("Invalid ISBN format: invalid_isbn", errorMessage.getText());
    }

    @Test
    public void testAddStockFailure_InvalidQuantity() {
        enterText("#isbnTextField", "978 3 16 148410 0");
        enterText("#newStockTextField", "abc");
        clickOn("#addStockButton");
        WaitForAsyncUtils.waitForFxEvents();

        Label errorMessage = getLabel("#newStockMessageLabel");
        assertNotNull(errorMessage);
        assertEquals("Can't have letters in stock field!", errorMessage.getText());
    }

    @Test
    public void testAddStockFailure_EmptyQuantity() {
        enterText("#isbnTextField", "978 3 16 148410 0");
        enterText("#newStockTextField", "");
        clickOn("#addStockButton");
        WaitForAsyncUtils.waitForFxEvents();

        Label errorMessage = getLabel("#newStockMessageLabel");
        assertNotNull(errorMessage);
        assertEquals("Can't be empty!", errorMessage.getText());
    }

    @Test
    public void testBookNotFound() {
        when(bookController.findBook("978 3 16 148410 5")).thenReturn(null);

        enterText("#isbnTextField", "978 3 16 148410 5");
        WaitForAsyncUtils.waitForFxEvents();

        Label errorMessage = lookup("#searchMessageLabel").query();

        assertNotNull(errorMessage, "The error message label should not be null");
        assertEquals("This book doesn't exist in the database!", errorMessage.getText(), "The error message text is incorrect");
    }

    @Test
    public void testBackButtonTransition() {
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(stage.getScene().getRoot() instanceof BorderPane);
    }
}
