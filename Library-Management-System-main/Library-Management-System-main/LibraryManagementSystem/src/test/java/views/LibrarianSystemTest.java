package views;
import Models.*;
import Controllers.BillController;
import Controllers.BookController;
import Views.EditLibrarianView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.WaitForAsyncUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class LibrarianSystemTest extends ApplicationTest {
    private static File file;
    private Librarian librarian;
    private EditLibrarianView view;
    @Override
    public void start(Stage stage) {
        Scene scene = view.editLibrarian(stage, librarian);
        stage.setScene(scene);
        stage.show();
    }
    @BeforeEach
    public void setUp() {
        file = new File("books.dat");
        BookController bookController = new BookController();
        BillController billController = new BillController();
        Author author = new Author("James", "Willi", Gender.Male);
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Comedy"));
        bookController.createBook("12345", "Test Book", author, categories, "Supplier", 10, 15, 20, "cover.jpg");
    }
    @AfterEach
    public void tearDown() {
        if (file != null && file.exists()) {
            try {
                FileWriter fileWriter = new FileWriter("books.dat");
                fileWriter.write("");

                addTestUser(new User("John", "Doe", "johndoe", Roles.Admin, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1));
                addTestUser(new User("Jane", "Doe", "janedoe", Roles.Manager, "password123", 75000, "111-222-3333", LocalDate.of(1990, 8, 25), Gender.Female, 3));
                fileWriter.close();
                // Delete the file after the test
                file.delete();
            } catch (IOException ignored) {
            }
        }
    }
    private void addTestUser(Object user) {
        System.out.println("Adding test user: " + user);
    }
    @Test
    public void testLibrarianCreateBookBill() {
        clickOn("#emailTF").write("john.doe@example.com");
        clickOn("#passwordTF").write("password1");
        clickOn("#loginButton");
        assertFalse(lookup("#failedLabel").tryQuery().isPresent());
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(window("Book View"), WindowMatchers.isShowing());
        clickOn("#addToCartButton");
        clickOn("#ISBNTF").write("2291372790");
        clickOn("#quantityTF").write("13");
        clickOn("#checkout");
        WaitForAsyncUtils.waitForFxEvents();
    }
    @Test
    public void testLibrarianCreateBookBill_failISBN() {
        clickOn("#emailTF").write("john.doe@example.com");
        clickOn("#passwordTF").write("password1");
        clickOn("#loginButton");
        assertFalse(lookup("#failedLabel").tryQuery().isPresent());
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(window("Book View"), WindowMatchers.isShowing());
        clickOn("#addToCartButton");
        clickOn("#ISBNTF").write("229372790");
        clickOn("#quantityTF").write("13");
        clickOn("#checkout");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(window("Cart"), WindowMatchers.isShowing());
    }
    @Test
    public void testLibrarianCreateBookBill_failStock() {
        clickOn("#emailTF").write("john.doe@example.com");
        clickOn("#passwordTF").write("password1");
        clickOn("#loginButton");
        assertFalse(lookup("#failedLabel").tryQuery().isPresent());
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(window("Book View"), WindowMatchers.isShowing());
        clickOn("#addToCartButton");
        clickOn("#ISBNTF").write("2291372790");
        clickOn("#quantityTF").write("19");
        clickOn("#checkout");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(window("Cart"), WindowMatchers.isShowing());
    }
    @Test
    public void testLoginFailure() {
        clickOn("#emailTF").write("johndoe@yahoo.com");
        clickOn("#passwordTF").write("wrongpassword");
        clickOn("#loginButton");
        assertTrue(lookup("#failedLabel").tryQuery().isPresent());
    }
}

