package views;

import Controllers.BookController;
import Controllers.FileController;
import Models.*;
import Views.AddBookView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;

public class AddBookViewTest extends ApplicationTest {

    private Stage stage;
    private AddBookView addBookView;
    private User mockUser;

    @BeforeEach
    public void setUp() throws InterruptedException {
        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("rexhens");
        when(mockUser.getPassword()).thenReturn("Rexhens1@");
        when(mockUser.getUserRole()).thenReturn(Roles.Manager);

        if (FileController.authors == null) {
            FileController.authors = new ArrayList<>();
        }
        if (FileController.categories == null) {
            FileController.categories = new ArrayList<>();
        }
        if (FileController.books == null) {
            FileController.books = new ArrayList<>();
        }

        if (FileController.authors.isEmpty()) {
            FileController.authors.add(new Author("Author", "One", Gender.MALE));
            FileController.writeAuthors();
            FileController.authors.add(new Author("Author", "Two", Gender.MALE));
            FileController.writeAuthors();
            Thread.sleep(1000);
        }

        if (FileController.categories.isEmpty()) {
            FileController.categories.add(new Category("Fiction"));
            FileController.writeCategories();
            FileController.categories.add(new Category("Drama"));
            FileController.writeCategories();
            Thread.sleep(1000);
        }

        FileController.writeBooks();
        Thread.sleep(1000);

        Platform.runLater(() -> {
            addBookView = new AddBookView(mockUser);
            stage = new Stage();
            Scene scene = addBookView.addBook(stage);
            stage.setScene(scene);
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testAddBookWithEmptyFields() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("ISBN can't be empty!"));
    }

    @Test
    public void testAddBookWithInvalidISBN() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 1 111 111 111");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#booktitle").write("Test Book");
        clickOn("#authorComboBox").clickOn("Author One");
        clickOn("#categoryCheckBox_Fiction");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("100");
        clickOn("#originalPrice").write("150");
        clickOn("#sellingPrice").write("200");
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("ISBN format is incorrect!"));
    }

    @Test
    public void testAddBookWithNoAuthors() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 3 16 148410 0");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#booktitle").write("Test Book");
        clickOn("#categoryCheckBox_Fiction");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("100");
        clickOn("#originalPrice").write("150");
        clickOn("#sellingPrice").write("200");
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("Select at least one author!"));
    }

    @Test
    public void testAddBookWithNoCategory() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 3 16 148410 0");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#booktitle").write("Test Book");
        clickOn("#authorComboBox").clickOn("Author One");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("100");
        clickOn("#originalPrice").write("150");
        clickOn("#sellingPrice").write("200");
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("Select at least one category!"));
    }

    @Test
    public void testAddBookWithDuplicateISBN() {
        WaitForAsyncUtils.waitForFxEvents();
        FileController.books.add(new Book(
                "978 3 16 148410 0",
                "Existing Book",
                FileController.authors.get(0),
                new ArrayList<>(FileController.categories),
                "Supplier",
                100,
                150,
                200,
                "cover.jpg"
        ));
        FileController.writeBooks();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 3 16 148410 0");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#booktitle").write("Duplicate Book");
        clickOn("#authorComboBox").clickOn("Author One");
        clickOn("#categoryCheckBox_Fiction");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("100");
        clickOn("#originalPrice").write("150");
        clickOn("#sellingPrice").write("200");
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("Book with this ISBN already exists!"));
    }

    @Test
    public void testAddBookWithInvalidPrice() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 3 16 148410 1");
        clickOn("#booktitle").write("Test Book with Invalid Price");
        clickOn("#authorComboBox").clickOn("Author One");
        clickOn("#categoryCheckBox_Fiction");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("-100");
        clickOn("#originalPrice").write("-150");
        clickOn("#sellingPrice").write("-200");
        verifyThat("#purchasePrice", TextInputControlMatchers.hasText("-100"));
        verifyThat("#originalPrice", TextInputControlMatchers.hasText("-150"));
        verifyThat("#sellingPrice", TextInputControlMatchers.hasText("-200"));
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("Price can't be negative!"));
        verifyThat("#coverImageInput", NodeMatchers.isVisible());
        clickOn("#coverImageInput").write("cover.jpg");
    }

    @Test
    public void testAddBookWithInvalidPriceLetter() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 3 16 148410 5");
        clickOn("#booktitle").write("Test Book with Invalid Price");
        clickOn("#authorComboBox").clickOn("Author One");
        clickOn("#categoryCheckBox_Fiction");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("q");
        clickOn("#originalPrice").write("w");
        clickOn("#sellingPrice").write("e");
        verifyThat("#purchasePrice", TextInputControlMatchers.hasText("q"));
        verifyThat("#originalPrice", TextInputControlMatchers.hasText("w"));
        verifyThat("#sellingPrice", TextInputControlMatchers.hasText("e"));
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("Price can't have letters!"));
        verifyThat("#coverImageInput", NodeMatchers.isVisible());
        clickOn("#coverImageInput").write("cover.jpg");
    }

    @Test
    public void testAddBookWithEmptyBookTitle() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#isbn1").write("978 3 16 148410 0");
        clickOn("#booktitle").write("");
        clickOn("#authorComboBox").clickOn("Author One");
        clickOn("#categoryCheckBox_Fiction");
        clickOn("#supplier").write("Test Supplier");
        clickOn("#purchasePrice").write("100");
        clickOn("#originalPrice").write("150");
        clickOn("#sellingPrice").write("200");
        clickOn("#registerBookButton");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#errorLabel", LabeledMatchers.hasText("You need to insert a title!"));
    }

    @Test
    public void testBackButtonTransition() {
        Button backButton = lookup("#backButton").query();
        clickOn(backButton);
        sleep(1000);
        Platform.runLater(() -> {
            assertTrue(stage.getScene().getRoot() instanceof BorderPane,
                    "The scene should have transitioned to EmployeeHomePage.");
        });
        sleep(1000);
    }
}
