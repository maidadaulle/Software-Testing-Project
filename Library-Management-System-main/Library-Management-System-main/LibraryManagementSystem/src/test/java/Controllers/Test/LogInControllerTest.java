package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controllers.FileController;
import Controllers.LogInController;
import Models.User;
import Models.StandardViewResponse;
import Models.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class LogInControllerTest {

        private LogInController logInController;

        @BeforeEach
        public void setUp() {
            // Initialize FileController with sample data
            FileController.users = new ArrayList<>();
            FileController.users.add(new User("Maida", "password123", Roles.Librarian));
            FileController.users.add(new User("Tea", "securePass", Roles.ADMIN));

            // Initialize the LogInController
            logInController = new LogInController();
        }

        @Test
        public void testLogInWithValidCredentials() {
            StandardViewResponse<User> response = logInController.OnLogInBtnClick("Maida", "password123");
            assertNotNull(response.getUser());
            assertEquals("Maida", response.getUser().getUsername());
            assertEquals("", response.getErrorMessage());
        }

        @Test
        public void testLogInWithInvalidUsername() {
            StandardViewResponse<User> response = logInController.OnLogInBtnClick("InvalidUser", "password123");
            assertNull(response.getUser());
            assertEquals("Wrong Credentials!", response.getErrorMessage());
        }

        @Test
        public void testLogInWithInvalidPassword() {
            StandardViewResponse<User> response = logInController.OnLogInBtnClick("Maida", "wrongPassword");
            assertNull(response.getUser());
            assertEquals("Wrong Password!", response.getErrorMessage());
        }

        @Test
        public void testLogInWithEmptyUsername() {
            StandardViewResponse<User> response = logInController.OnLogInBtnClick("", "password123");
            assertNull(response.getUser());
            assertEquals("Username cannot be null!", response.getErrorMessage());
        }

        @Test
        public void testLogInWithEmptyPassword() {
            StandardViewResponse<User> response = logInController.OnLogInBtnClick("Maida", "");
            assertNull(response.getUser());
            assertEquals("Password cannot be null!", response.getErrorMessage());
        }
    }

