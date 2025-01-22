package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controllers.Changepasscontroller;
import Controllers.FileController;
import Models.Gender;
import Models.Roles;
import Models.StandardViewResponse;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ChangepasscontrollerTest {

    private Changepasscontroller changePassController;
    private List<User> users;

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User("John", "Doe", "john_doe", Roles.Librarian, "password123", 3000, "123456789", LocalDate.now(), Gender.MALE, 1);
        changePassController = new Changepasscontroller();
        users = new ArrayList<>();
        users.add(new User("John", "Doe", "john_doe", Roles.Librarian, "password123", 3000, "123456789", LocalDate.now(), Gender.MALE, 1));
        users.add(new User("Jane", "Doe", "jane_doe", Roles.Manager, "password456", 3500, "987654321", LocalDate.now(), Gender.Female, 2));
        users.add(new User("Alice", "Smith", "alice_smith", Roles.Admin, "password789", 4000, "555123456", LocalDate.now(), Gender.Female, 3));
        FileController.users = (ArrayList<User>) users;
    }

    @Test
    void testFindUserById_UserDoesNotExist() {
        User result = changePassController.findUserById(5);
        assertNull(result, "User should not be found for the given ID.");
    }

    @Test
    void testFindUserById_EmptyList() {
        FileController.users = new ArrayList<>();
        User result = changePassController.findUserById(1);
        assertNull(result, "User should not be found when the list is empty.");
    }

    @Test
    void testIsSpecialChar_ReturnsTrue_WhenCharacterIsSpecial() {
        char specialChar = '!';
        boolean result = Changepasscontroller.isSpecialChar(specialChar);
        assertTrue(result, "The character should be recognized as a special character.");
    }

    @Test
    void testIsSpecialChar_ReturnsFalse_WhenCharacterIsNotSpecial() {
        char nonSpecialChar = 'A';
        boolean result = Changepasscontroller.isSpecialChar(nonSpecialChar);
        assertFalse(result, "The character should not be recognized as a special character.");
    }

    @Test
    void testIsSpecialChar_ReturnsTrue_WhenCharacterIsUnderscore() {
        char underscoreChar = '_';
        boolean result = Changepasscontroller.isSpecialChar(underscoreChar);
        assertTrue(result, "The character should be recognized as a special character.");
    }

    @Test
    void testIsSpecialChar_ReturnsFalse_WhenCharacterIsDigit() {
        char digitChar = '5';
        boolean result = Changepasscontroller.isSpecialChar(digitChar);
        assertFalse(result, "The character should not be recognized as a special character.");
    }

    @Test
    void testIsSpecialChar_ReturnsFalse_WhenCharacterIsLetter() {
        char letterChar = 'b';
        boolean result = Changepasscontroller.isSpecialChar(letterChar);
        assertFalse(result, "The character should not be recognized as a special character.");
    }

    @Test
    void testChangePass_PasswordTooShort() {
        String shortPassword = "Short1!";
        StandardViewResponse<User> response = changePassController.changepass(shortPassword, testUser.getId());
        assertEquals("Password Minimum length is 8!", response.getErrorMessage(), "Password should have a minimum length of 8.");
    }

    @Test
    void testChangePass_PasswordTooLong() {
        String longPassword = "ThisIsAVeryLongPassword123!";
        StandardViewResponse<User> response = changePassController.changepass(longPassword, testUser.getId());
        assertEquals("Password Maximum length is 20!", response.getErrorMessage(), "Password should have a maximum length of 20.");
    }

    @Test
    void testChangePass_MissingUppercase() {
        String passwordWithoutUppercase = "nouppercase1!";
        StandardViewResponse<User> response = changePassController.changepass(passwordWithoutUppercase, testUser.getId());
        assertEquals("Password must have at least 1 upper case!", response.getErrorMessage(), "Password should have at least 1 uppercase letter.");
    }

    @Test
    void testChangePass_MissingLowercase() {
        String passwordWithoutLowercase = "NOLOWERCASE1!";
        StandardViewResponse<User> response = changePassController.changepass(passwordWithoutLowercase, testUser.getId());
        assertEquals("Password must have at least 1 lower case!", response.getErrorMessage(), "Password should have at least 1 lowercase letter.");
    }

    @Test
    void testChangePass_MissingSpecialChar() {
        String passwordWithoutSpecialChar = "NoSpecialChar1";
        StandardViewResponse<User> response = changePassController.changepass(passwordWithoutSpecialChar, testUser.getId());
        assertEquals("Password must contain at least 1 special character!", response.getErrorMessage(), "Password should contain at least 1 special character.");
    }

    @Test
    void testChangePass_MissingDigit() {
        String passwordWithoutDigit = "NoDigit!";
        StandardViewResponse<User> response = changePassController.changepass(passwordWithoutDigit, testUser.getId());
        assertEquals("Password must contain at least 1 digit!", response.getErrorMessage(), "Password should contain at least 1 digit.");
    }

    @Test
    void testChangePass_InvalidUserId() {
        String validPassword = "ValidPass123!";
        int invalidUserId = -1;
        StandardViewResponse<User> response = changePassController.changepass(validPassword, invalidUserId);
        assertNotNull(response.getErrorMessage(), "Error message should not be null.");
    }
}
