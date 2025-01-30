package views;

import Controllers.*;
import Models.*;
import Views.AddLibrarianView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.function.BooleanSupplier;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

class AddLibrarianViewTest extends ApplicationTest {

    private Button registerButton;
    private TextField nameTxtField, surnameTxtField, usernameTxtField, passwordField, checkPasswordField, phoneNumField, salaryField;
    private Label errorMessageLabel;
    private Stage stage;
    private LibrarianController mockLibrarianController;
    private User mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("librarian");
        when(mockUser.getPassword()).thenReturn("librarian123");
        when(mockUser.getUserRole()).thenReturn(Roles.Librarian);

        mockLibrarianController = mock(LibrarianController.class);

        sleep(1000);
        Platform.runLater(() -> {
            AddLibrarianView addLibrarianView = new AddLibrarianView(mockUser);
            stage = new Stage();
            Scene scene = addLibrarianView.addLibrarian(stage);
            stage.setScene(scene);
            stage.show();
        });

        waitUntil(() -> stage != null && stage.isShowing());
    }

    public void waitUntil(BooleanSupplier condition) {
        long timeout = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < timeout) {
            if (condition.getAsBoolean()) {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new AssertionError("Condition not met within timeout");
    }

    @Test
    public void testAddLibrarianSuccess() {
        User mockUser = new User("librarian", "librarian123", Roles.Librarian);

        StandardViewResponse<User> successResponse = new StandardViewResponse<>(new User("librarian", "librarian123", Roles.Librarian), null);
        when(mockLibrarianController.addUser(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(successResponse);

        nameTxtField = lookup("#name-txt-field").query();
        surnameTxtField = lookup("#surname-txt-field").query();
        usernameTxtField = lookup("#username-txt-field").query();
        passwordField = lookup("#password-field").query();
        checkPasswordField = lookup("#check-password-field").query();
        phoneNumField = lookup("#phone-num-field").query();
        salaryField = lookup("#salary-field").query();

        nameTxtField.setText("John");
        surnameTxtField.setText("Doe");
        usernameTxtField.setText("johndoe");
        passwordField.setText("password123");
        checkPasswordField.setText("password123");
        phoneNumField.setText("1234567890");
        salaryField.setText("30000");

        registerButton = lookup("#register-librarian-btn").query();
        clickOn(registerButton);
        waitForFxEvents();

        Platform.runLater(() -> {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Librarian was successfully added!");
            successAlert.showAndWait();

            Button okButton = (Button) successAlert.getDialogPane().lookupButton(ButtonType.OK);
            waitUntil(() -> okButton != null && okButton.isVisible());
            clickOn(okButton);
        });

        waitUntil(() -> {
            Label label = (Label) stage.getScene().lookup("#error-message-label");
            return label != null && label.getText().equals("Librarian was successfully added!");
        });

        Label label = (Label) stage.getScene().lookup("#error-message-label");
        assertNotNull(label, "Error message label should be present.");
        assertEquals("Librarian was successfully added!", label.getText(), "Success message text should be correct.");
    }

    @Test
    public void testAddLibrarianInvalidPassword() {
        nameTxtField = lookup("#name-txt-field").query();
        surnameTxtField = lookup("#surname-txt-field").query();
        usernameTxtField = lookup("#username-txt-field").query();
        passwordField = lookup("#password-field").query();
        checkPasswordField = lookup("#check-password-field").query();

        nameTxtField.setText("John");
        surnameTxtField.setText("Doe");
        usernameTxtField.setText("johndoe");
        passwordField.setText("password123");
        checkPasswordField.setText("password456");

        registerButton = lookup("#register-librarian-btn").query();
        clickOn(registerButton);
        waitForFxEvents();

        errorMessageLabel = (Label) stage.getScene().lookup("#error-message-label");
        assertNotNull(errorMessageLabel, "Error message label should be present.");
        assertTrue(errorMessageLabel.getText().contains("Passwords do not match"), "Error message should indicate password mismatch.");
    }

    @Test
    public void testBackButtonTransition() {
        Button backButton = lookup("#back-to-homepage-btn").query();
        clickOn(backButton);
        sleep(1000);
        waitForFxEvents();

        Platform.runLater(() -> {
            assertTrue(stage.getScene().getRoot() instanceof BorderPane,
                    "The scene should have transitioned to AdminHomePage.");
        });
        sleep(1000);
        waitForFxEvents();
    }
}
