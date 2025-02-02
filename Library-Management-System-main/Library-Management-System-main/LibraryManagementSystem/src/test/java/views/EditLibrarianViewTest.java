package views;

import Models.Gender;
import Models.Librarian;
import Models.User;
import Models.Roles;
import Views.EditLibrarianView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.time.LocalDate;

public class EditLibrarianViewTest extends ApplicationTest {
    private EditLibrarianView view;
    private Librarian librarian;
    private User adminUser;

    @BeforeEach
    public void setUp() {
        librarian = new Librarian(
                "John",
                "Doe",
                "jdoe",
                "password123",
                3000.0,
                "123456789",
                Gender.Male,
                LocalDate.of(1990, 1, 1),
                1
        );
        adminUser = new User(
                "admin",
                "admin",
                "adminUsername",
                Roles.Librarian,
                "adminPassword",
                5000.0,
                "987654321",
                LocalDate.of(1985, 5, 15),
                Gender.Male,
                0
        );
        view = new EditLibrarianView(adminUser);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = view.editLibrarian(stage, librarian);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSceneLoads() throws InterruptedException {
        Text text = lookup(".text").query();
        assertNotNull(text);
        assertEquals("Edit Librarian", text.getText());
        Thread.sleep(1000);
    }

    @Test
    public void testEditButtonSuccess() throws InterruptedException {
        TextField nameField = lookup(".text-field").query();
        nameField.setText("Jane");
        PasswordField passwordField = lookup(".password-field").query();
        passwordField.setText("newpassword");
        RadioButton maleRadioButton = lookup(".radio-button").query();
        maleRadioButton.setSelected(true);
        DatePicker birthDatePicker = lookup(".date-picker").query();
        birthDatePicker.setValue(LocalDate.of(1992, 5, 15));
        Button editButton = lookup(".button").query();
        clickOn(editButton);
        
        Thread.sleep(1000);

        Text successText = lookup(".success-message").query();
        assertNotNull(successText);
        assertEquals("Librarian was successfully edited", successText.getText());
    }

    @Test
    public void testBackButton() throws InterruptedException {
        Button backButton = lookup(".button").query();
        clickOn(backButton);
        
        Thread.sleep(1000);

        Text text = lookup(".text").query();
        assertEquals("Manage Librarians", text.getText());
    }

    @Test
    public void testEmptyFields() throws InterruptedException {
        TextField nameField = lookup(".text-field").query();
        nameField.setText("");
        Button editButton = lookup(".button").query();
        clickOn(editButton);
        
        Thread.sleep(1000);

        TextArea systemArea = lookup(".text-area").query();
        assertTrue(systemArea.getText().contains("Name cannot be empty"));
    }

    @Test
    public void testGenderSelection() throws InterruptedException {
        RadioButton femaleRadioButton = lookup(".radio-button:has-text('Female')").query();
        clickOn(femaleRadioButton);
        
        Thread.sleep(1000);

        assertTrue(femaleRadioButton.isSelected());
    }
}


