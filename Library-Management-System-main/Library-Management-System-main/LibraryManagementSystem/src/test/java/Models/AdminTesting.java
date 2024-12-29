package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTesting {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new Admin("Rexhens", "Test", "test",
                Roles.ADMIN, "password123", 50000,
                "1234567890", Gender.MALE, LocalDate.of(2004, 1, 1),
                5);
    }

    @Test
    void testConstructorInitialization() {
        assertEquals("Rexhens", user.getName());
        assertEquals("Test", user.getSurname());
        assertEquals("test", user.getUsername());
        assertEquals(Roles.ADMIN, user.getUserRole());
        assertEquals("password123", user.getPassword());
        assertEquals(50000, user.getSalary());
        assertEquals("1234567890", user.getPhoneNum());
        assertEquals(LocalDate.of(2004, 1, 1), user.getBirthDate());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals(5, user.getAccessLevel());
    }

    @Test
    void testStaticNoUsersIncrement() {
        int initialNoUsers = User.getNoUsers();
        User newUser = new Admin("testuser", "testpass", "newuser",
                Roles.ADMIN, "password123", 40000,
                "9876543210", Gender.MALE, LocalDate.of(1999, 1, 1),
                3);
        assertEquals(initialNoUsers + 1, User.getNoUsers());
    }

    @Test
    void testSettersAndGetters() {
        user.setName("TestName");
        assertEquals("TestName", user.getName());

        user.setSalary(60000);
        assertEquals(60000, user.getSalary());
    }

    @Test
    void testToStringMethod() {
        String userString = user.toString();
        assertTrue(userString.contains("Rexhens"));
        assertTrue(userString.contains("test"));
        assertTrue(userString.contains("ADMIN"));
    }


    @Test
    void testDifferentUsername() {
        int initialNoUsers = User.getNoUsers();
        User user2 = new Admin("Rexhens", "Test", "test2", // Different username
                Roles.ADMIN, "password123", 50000,
                "1234567890", Gender.MALE, LocalDate.of(2004, 1, 1),
                5);
        assertEquals(initialNoUsers + 1, User.getNoUsers());
    }
}

