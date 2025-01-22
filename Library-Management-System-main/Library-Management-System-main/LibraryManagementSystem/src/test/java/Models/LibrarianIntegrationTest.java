package Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LibrarianIntegrationTest {

    private Librarian librarian;


    @BeforeEach
    void setUp() {
        librarian = new Librarian("john_doe", "password123");

    }

    @Test
    void testLibrarianIntegrationWithUser() {
        assertTrue(librarian instanceof User, "Librarian should inherit from User class");

        assertEquals("john_doe", librarian.getUsername(), "Username should be properly set.");
        assertEquals(Roles.Librarian, librarian.getUserRole(), "Role should be 'Librarian'.");
    }

}
