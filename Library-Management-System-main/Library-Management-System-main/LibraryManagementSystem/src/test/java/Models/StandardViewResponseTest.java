package Models;

import static org.junit.jupiter.api.Assertions.*;

import Models.StandardViewResponse;
import Models.User;
import Models.Roles;
import org.junit.jupiter.api.Test;

class StandardViewResponseTest {

        @Test
        public void testStandardViewResponseWithValidUser() {
            User user = new User("Maida", "password123", Roles.Librarian);
            StandardViewResponse<User> response = new StandardViewResponse<>(user, "");

            assertNotNull(response.getUser());
            assertEquals("Maida", response.getUser().getUsername());
            assertEquals("", response.getErrorMessage());
        }

        @Test
        public void testStandardViewResponseWithError() {
            StandardViewResponse<User> response = new StandardViewResponse<>(null, "Invalid credentials");
            assertNull(response.getUser());
            assertEquals("Invalid credentials", response.getErrorMessage());
        }

        @Test
        public void testStandardViewResponseWithEmptyErrorMessage() {
            StandardViewResponse<User> response = new StandardViewResponse<>(null, "");
            assertNull(response.getUser());
            assertEquals("", response.getErrorMessage());
        }

        @Test
        public void testStandardViewResponseWithNullUserAndMessage() {
            StandardViewResponse<User> response = new StandardViewResponse<>(null, null);
            assertNull(response.getUser());
            assertNull(response.getErrorMessage());
        }
    }


