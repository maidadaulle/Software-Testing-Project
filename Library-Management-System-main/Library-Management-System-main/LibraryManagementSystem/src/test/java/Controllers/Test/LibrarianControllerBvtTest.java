package Controllers.Test;

import static Controllers.LibrarianController.isValidPassword;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LibrarianControllerBvtTest {

    @Test
    @DisplayName("BVT Password Length Validation")
    void testPasswordLengthValidation_BoundaryValues() {
        // Valid password lengths
        assertTrue(isValidPassword("Valid1Password!").isEmpty());
        assertTrue(isValidPassword("Short12!").isEmpty());
        assertTrue(isValidPassword("ValidPass123!").isEmpty());

        // Invalid password lengths
        assertEquals("Password Minimum length is 8!", isValidPassword("short!")); 
        assertEquals("Password Maximum length is 20!", isValidPassword("averylongpassword1234!")); 
        assertEquals("Password Maximum length is 20!", isValidPassword("averylongpassword12345!")); 
        assertEquals("Password Maximum length is 20!", isValidPassword("toolongpassword123456!"));


    }


    @Test
    @DisplayName("BVT Password Content Validation")
    void testPasswordContentValidation_BoundaryValues() {
        // Valid passwords (all required content)
        assertTrue(isValidPassword("Valid1Password!").isEmpty());

        // Invalid passwords (missing required content)
        assertEquals("Password must have at least 1 upper case!", isValidPassword("lowercase1!"));
        assertEquals("Password must have at least 1 lower case!", isValidPassword("UPPERCASE1!"));
        assertEquals("Password must contain at least 1 digit!", isValidPassword("UppercaseSpecial!"));
        assertEquals("Password must contain at least 1 special character!", isValidPassword("Uppercase1lower"));
    }

    @Test
    @DisplayName("BVT Password Exact Length Content Validation")
    void testPasswordExactLengthContentValidation_BoundaryValues() {
        // Valid password with exact length and all required content
        assertTrue(isValidPassword("Valid1Password!").isEmpty());

        // Invalid passwords with exact length but missing some content
        assertFalse(isValidPassword("valid1password!").isEmpty()); 
        assertFalse(isValidPassword("VALID1PASSWORD!").isEmpty()); 
        assertFalse(isValidPassword("Valid1Password").isEmpty()); 
        assertFalse(isValidPassword("ValidPassword!").isEmpty()); 
    }

    @Test
    @DisplayName("BVT Password Length Validation - Expected Errors")
    void testPasswordLengthValidation() {
        // Valid passwords
        assertDoesNotThrow(() -> isValidPassword("Valid1Password!"));
        assertDoesNotThrow(() -> isValidPassword("Short1!"));
        assertDoesNotThrow(() -> isValidPassword("ValidPass123!"));

        // Invalid passwords 
        assertEquals("Password Minimum length is 8!", isValidPassword("short1!"));
        assertEquals("Password Maximum length is 20!", isValidPassword("averylongpassword12345!"));
        assertEquals("Password Maximum length is 20!", isValidPassword("toolongpassword123456!"));
    }

    @Test
    @DisplayName("BVT Password Content Validation - Expected Errors")
    void testPasswordContentValidation() {
        // Valid passwords 
        assertDoesNotThrow(() -> isValidPassword("Valid1Password!"));

        // Invalid passwords 
        assertEquals("Password must have at least 1 upper case!", isValidPassword("lowercase1!"));
        assertEquals("Password must have at least 1 lower case!", isValidPassword("UPPERCASE1!"));
        assertEquals("Password must contain at least 1 digit!", isValidPassword("UppercaseSpecial!"));
        assertEquals("Password must contain at least 1 special character!", isValidPassword("Uppercase1lower"));
    }

    @Test
    @DisplayName("BVT Password Exact Length Content Validation - Expected Errors")
    void testPasswordExactLengthContentValidation() {
        // Valid passwords 
        assertDoesNotThrow(() -> isValidPassword("Valid1Password!"));

        // Invalid passwords 
        assertEquals("Password must have at least 1 upper case!", isValidPassword("valid1password!"));
        assertEquals("Password must have at least 1 lower case!", isValidPassword("VALID1PASSWORD!"));
        assertEquals("Password must contain at least 1 special character!", isValidPassword("Valid1Password"));
        assertEquals("Password must contain at least 1 digit!", isValidPassword("ValidPassword!"));
    }

    @Test
    @DisplayName("BVT Password with Special Characters")
    void testPasswordWithSpecialCharacter() {
        // Valid password containing a special character
        assertTrue(isValidPassword("Valid1Password!").isEmpty()); 
        assertTrue(isValidPassword("Valid@Password1").isEmpty());
        assertTrue(isValidPassword("Password1#").isEmpty()); 

        // Invalid passwords with special characters that do not meet other conditions
        assertEquals("Password must have at least 1 upper case!", isValidPassword("password1@"));
        assertEquals("Password must have at least 1 lower case!", isValidPassword("PASSWORD1@"));
        assertEquals("Password must contain at least 1 digit!", isValidPassword("Password!"));
    }

}
