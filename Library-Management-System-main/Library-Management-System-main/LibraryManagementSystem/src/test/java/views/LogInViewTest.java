package views;

import Models.StandardViewResponse;
import Views.LogInView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;
import static org.testfx.util.WaitForAsyncUtils.sleep;

import Models.User;
import Controllers.LogInController;
import Models.Admin;

class LogInViewTest extends ApplicationTest {

    private LogInView logInView;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField systemField;
    private Button submitButton;
    private LogInController logInControllerMock;

    @Override
    public void start(Stage stage) {
        logInView = new LogInView();
        Scene scene = logInView.showLogInScene(stage);
        stage.setScene(scene);
        stage.show();
        waitForFxEvents();

        usernameField = lookup("#usernameField").query();
        passwordField = lookup("#passwordField").query();
        systemField = lookup("#systemField").query();
        submitButton = lookup("#submitButton").query();
        sleep(1000);
    }

    @Test
    public void testLogInWithCorrectCredentials() {
        logInControllerMock = Mockito.mock(LogInController.class);
        StandardViewResponse<User> response = new StandardViewResponse<>(new Admin("admin", "admin"), null);
        when(logInControllerMock.OnLogInBtnClick("admin", "admin")).thenReturn(response);

        usernameField.setText("admin");
        sleep(1000);
        passwordField.setText("admin");
        sleep(1000);

        clickOn(submitButton);
        sleep(1000);
        waitForFxEvents();

        assertTrue(systemField.getText().isEmpty());
    }

    @Test
    public void testLogInWithWrongPassword() {
        logInControllerMock = Mockito.mock(LogInController.class);
        StandardViewResponse<User> response = new StandardViewResponse<>(null, "Wrong Password!");
        when(logInControllerMock.OnLogInBtnClick("admin", "wrongpassword")).thenReturn(response);

        usernameField.setText("admin");
        sleep(1000);
        passwordField.setText("wrongpassword");
        sleep(1000);

        clickOn(submitButton);
        sleep(1000);
        waitForFxEvents();

        assertEquals("Wrong Password!", systemField.getText());
    }

    @Test
    public void testLogInWithEmptyUsername() {
        logInControllerMock = Mockito.mock(LogInController.class);
        StandardViewResponse<User> response = new StandardViewResponse<>(null, "Username cannot be null!");
        when(logInControllerMock.OnLogInBtnClick("", "password")).thenReturn(response);

        usernameField.setText("");
        sleep(1000);
        passwordField.setText("password");
        sleep(1000);

        clickOn(submitButton);
        sleep(1000);
        waitForFxEvents();

        assertEquals("Username can't be null", systemField.getText());
    }

    @Test
    public void testLogInWithEmptyPassword() {
        usernameField.setText("admin");
        sleep(1000);
        passwordField.setText("");
        sleep(1000);

        StandardViewResponse<User> response = new StandardViewResponse<>(null, "Password can't be null");

        LogInController logInController = Mockito.mock(LogInController.class);
        when(logInController.OnLogInBtnClick("admin", "")).thenReturn(response);

        clickOn(submitButton);
        sleep(1000);
        waitForFxEvents();

        assertEquals("Password can't be null", systemField.getText());
    }

    @Test
    public void testLogInWithEnterKey() {
        logInControllerMock = Mockito.mock(LogInController.class);
        StandardViewResponse<User> response = new StandardViewResponse<>(new Admin("admin", "admin"), null);
        when(logInControllerMock.OnLogInBtnClick("admin", "admin")).thenReturn(response);

        usernameField.setText("admin");
        sleep(1000);
        passwordField.setText("admin");
        sleep(1000);

        press(KeyCode.ENTER).release(KeyCode.ENTER);
        sleep(1000);
        waitForFxEvents();

        assertTrue(systemField.getText().isEmpty());
    }

    @Test
    public void testLogInWithWrongCredentials() {
        logInControllerMock = Mockito.mock(LogInController.class);
        StandardViewResponse<User> response = new StandardViewResponse<>(null, "Wrong Credentials!");
        when(logInControllerMock.OnLogInBtnClick("wronguser", "wrongpassword")).thenReturn(response);

        usernameField.setText("wronguser");
        sleep(1000);
        passwordField.setText("wrongpassword");
        sleep(1000);

        clickOn(submitButton);
        sleep(1000);
        waitForFxEvents();

        assertEquals("Wrong Credentials!", systemField.getText());
    }



    @Test
    public void testLogInWithCorrectCredentialsEmployee() {
        logInControllerMock = Mockito.mock(LogInController.class);
        StandardViewResponse<User> response = new StandardViewResponse<>(new Admin("damon", "Rexhens@2"), null);
        when(logInControllerMock.OnLogInBtnClick("damon", "Rexhens@2")).thenReturn(response);

        usernameField.setText("damon");
        sleep(1000);
        passwordField.setText("Rexhens@2");
        sleep(1000);

        clickOn(submitButton);
        sleep(1000);
        waitForFxEvents();

        assertTrue(systemField.getText().isEmpty());
    }

}
