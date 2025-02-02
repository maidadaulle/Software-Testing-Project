/*package views;

import static org.junit.jupiter.api.Assertions.*;
import Models.Gender;
import Models.Roles;
import Models.User;
import Views.Changepassview;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDate;

public class ChangepassviewTest extends ApplicationTest {
    private User testUser;
    private Changepassview changepassview;

    @Override
    public void start(Stage stage) {
        testUser = new User(
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
        changepassview = new Changepassview(testUser);
        stage.setScene(changepassview.showview(stage));
        stage.show();
    }

    @Test
    public void testSuccessfulPasswordChange() {
        clickOn("#pass1").write("newValidPassword123");
        clickOn("Confirm");
        Label label1 = lookup("#label1").query();
        assertEquals("Password was successfully changed!", label1.getText());
        if (testUser.getUserRole() == Roles.Admin) {
            assertTrue(lookup("Admin Home Page").query().isVisible());
        } else {
            assertTrue(lookup("Employee Home Page").query().isVisible());
        }
    }

    @Test
    public void testInvalidPasswordChange() {
        clickOn("#pass1").write("short");
        clickOn("Confirm");
        Label label1 = lookup("#label1").query();
        assertEquals("Invalid password", label1.getText());
        assertNotEquals("Password was successfully changed!", label1.getText());
    }

    @Test
    public void testNavigateBackButton() {
        clickOn("Back");
        if (testUser.getUserRole() == Roles.Admin) {
            assertTrue(lookup("Admin Home Page").query().isVisible());
        } else {
            assertTrue(lookup("Employee Home Page").query().isVisible());
        }
    }
}*/
