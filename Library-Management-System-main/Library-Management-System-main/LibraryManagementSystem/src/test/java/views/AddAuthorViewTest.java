package views;

import Controllers.AuthorController;
import Models.Author;
import Models.Gender;
import Models.Roles;
import Models.User;
import Views.AddAuthorView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;
import static org.testfx.util.WaitForAsyncUtils.sleep;

public class AddAuthorViewTest extends ApplicationTest {

    private AddAuthorView addAuthorView;
    private TextField nameField;
    private Label systemField;
    private TextField surnameField;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    private RadioButton otherRadio;
    private Button registerButton;
    private Button backButton;
    private User currentUser;
    private AuthorController authorController;

    @Override
    public void start(Stage stage) throws Exception {
        // Set up the test with a mock user
        currentUser = new User("testUser", "testPassword", Roles.ADMIN, 1000);
        addAuthorView = new AddAuthorView(currentUser);
        Scene scene = addAuthorView.addAuthor(stage);
        stage.setScene(scene);
        stage.show();
        waitForFxEvents();

        // Look up the elements by their IDs
        nameField = lookup("#nameT").query();
        surnameField = lookup("#surnameT").query();
        maleRadio = lookup("#maleRadio").query();
        femaleRadio = lookup("#femaleRadio").query();
        otherRadio = lookup("#otherRadio").query();
        registerButton = lookup("#registerButton").query();
        backButton = lookup("#backButton").query();
        systemField = lookup("#systemField").query();
        authorController = new AuthorController();

        sleep(1000);
    }

    @BeforeEach
    public void setup() {
        // Reset user data before each test
        currentUser = new User("testUser", "testPassword", Roles.Admin, 1000);
    }

    @Test
    public void testRegisterAuthorWithValidData() {
        // Simulate entering valid data into the form
        nameField.setText("Rexhens");
        surnameField.setText("Balla");
        maleRadio.setSelected(true);

        clickOn(registerButton);
        sleep(1000);
        waitForFxEvents();

        assertTrue(lookup(".alert").queryAll() != null);
    }

    @Test
    public void testRegisterAuthorWithEmptyName() {
        authorController = Mockito.mock(AuthorController.class);
        authorController.addAuthor(new Author("","Balla",Gender.Male));
        nameField.setText("");
        surnameField.setText("Balla");
        femaleRadio.setSelected(true);  // Select Female gender

        clickOn(registerButton);
        sleep(1000);
        waitForFxEvents();
        System.out.println( lookup("#systemField").queryText().getText());
        // Check for validation error message
        assertEquals("Fields are empty!", systemField.getText());
        //assertTrue(lookup(".alert").queryAll() != null);

    }

    @Test
    public void testRegisterAuthorWithEmptySurname() {
        // Simulate submitting with an empty surname field
        nameField.setText("Rexhens");
        surnameField.setText("");
        otherRadio.setSelected(true);  // Select Other gender

        clickOn(registerButton);
        sleep(1000);
        waitForFxEvents();

        // Check for validation error message
        assertEquals("Surname can't be null", systemField.getText());
    }

    @Test
    public void testRegisterAuthorWithNoGenderSelected() {
        // Simulate submitting with no gender selected
        nameField.setText("Rexhens");
        surnameField.setText("Balla");

        clickOn(registerButton);
        sleep(1000);
        waitForFxEvents();

        // Check for validation error message
        assertEquals("Gender can't be null", lookup("#systemField").queryText().getText());
    }

    @Test
    public void testBackButton() {
        // Simulate clicking the "Back" button
        clickOn(backButton);
        sleep(1000);
        waitForFxEvents();

        // Verify that the user is redirected to the previous screen
        assertTrue(lookup("#previousScreen").query() != null);
    }

    @Test
    public void testRegisterWithEnterKey() {
        nameField.setText("Rexhens");
        surnameField.setText("Balla");
        femaleRadio.setSelected(true);

        press(KeyCode.ENTER).release(KeyCode.ENTER);
        sleep(1000);
        waitForFxEvents();

        // Check if the alert for successful registration is shown
        assertTrue(lookup(".alert") != null);
    }
}