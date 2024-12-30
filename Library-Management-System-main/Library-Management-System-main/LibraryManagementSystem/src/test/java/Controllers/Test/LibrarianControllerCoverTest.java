package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;
import Controllers.LibrarianController;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LibrarianControllerCoverTest {

    @Test
    void testValidPassword() {

        String result = LibrarianController.isValidPassword("Valid1Password@");
        assertEquals("", result, "Valid password should pass all checks.");
    }

    @Test
    void testShortPassword() {

        String result = LibrarianController.isValidPassword("Short1@");
        assertEquals("Password Minimum length is 8!", result, "Password should have a minimum length of 8.");
    }

    @Test
    void testLongPassword() {

        String result = LibrarianController.isValidPassword("ThisPasswordIsWayTooLong1@");
        assertEquals("Password Maximum length is 20!", result, "Password should not exceed 20 characters.");
    }

    @Test
    void testNoUpperCase() {

        String result = LibrarianController.isValidPassword("validpassword1@");
        assertEquals("Password must have at least 1 upper case!", result, "Password must have at least 1 uppercase letter.");
    }

    @Test
    void testNoLowerCase() {

        String result = LibrarianController.isValidPassword("VALIDPASSWORD1@");
        assertEquals("Password must have at least 1 lower case!", result, "Password must have at least 1 lowercase letter.");
    }

    @Test
    void testNoSpecialChar() {

        String result = LibrarianController.isValidPassword("ValidPassword1");
        assertEquals("Password must contain at least 1 special character!", result, "Password must contain at least 1 special character.");
    }

    @Test
    void testNoDigit() {

        String result = LibrarianController.isValidPassword("ValidPassword@");
        assertEquals("Password must contain at least 1 digit!", result, "Password must contain at least 1 digit.");
    }


    @Test
    void testSpecialCharValid() {

        boolean result = LibrarianController.isSpecialChar('@');
        assertTrue(result, "Character should be a special character.");
    }

    @Test
    void testSpecialCharInvalid() {

        boolean result = LibrarianController.isSpecialChar('a');
        assertFalse(result, "Character should not be a special character.");
    }

    @Test
    void testEdgeCaseSpecialChar() {

        boolean result = LibrarianController.isSpecialChar('!');
        assertTrue(result, "Edge case: '!' should be recognized as a special character.");
    }


}


