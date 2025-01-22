package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controllers.FileController;
import Controllers.LibrarianController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


class LibrarianControllerUnitTest {
    private LibrarianController librarianController;
    private FileController fileController;
    private Librarian librarian;


    @BeforeEach
    void setUp() {
        librarianController = new LibrarianController();
        fileController = new FileController();
        librarian = new Librarian();
        librarian.setUsername("librarian");
        librarian.setPassword("Librarian@123");
        librarian.setName("John");
        librarian.setSurname("Doe");
        librarian.setAccessLevel(1);
        fileController.addUser(librarian);
    }

    @Test
    void testAddUser_invalidPhoneNumber() {
        String name = "John";
        String surname = "Doe";
        String username = "johndoe";
        String password = "ValidP@ss1";
        String salary = "3000";
        String phoneNum = "1234567890"; 
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        int accessLevel = 1;
        String checkPassword = "ValidP@ss1";

        StandardViewResponse<User> response = librarianController.addUser(name, surname, username, password, salary, phoneNum, birthDate, gender, accessLevel, checkPassword);

      
        assertEquals("Phone number must be of specified format +355 6X XXX XXXX!", response.getErrorMessage());
    }


    @Test
    void testDeleteUserById_invalidId() {
        int userId = 999; 

        boolean result = librarianController.deleteUserById(userId);

        assertFalse(result, "User should not be deleted if not found");
    }

    

    @Test
    void testTotalNoBooksSoldByLibrarian_EmptyBills() {
        fileController.transactions.clear();

        int totalSold = librarianController.totalNoBooksSoldByLibrarian(librarian);

    
        assertEquals(0, totalSold, "Total number of books sold should be 0 when there are no transactions");
    }



    @Test
    void testEditUser_emptyFields() {
        String name = "";
        String surname = "";
        String username = "";
        String salary = "";
        String phoneNum = "+355 6X XXX XXXX";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        int accessLevel = 1;
        String password = "ValidP@ss1";

        StandardViewResponse<User> response = librarianController.editUser(name, surname, username, salary, phoneNum, 1, gender, accessLevel, birthDate, password);

        assertEquals("Fields are empty!", response.getErrorMessage(), "Librarian edit should fail due to empty fields.");
    }

    @Test
    void testEditUser_invalidPhoneNumber() {
        String name = "John";
        String surname = "Doe";
        String username = "john_doe";
        String salary = "3500";
        String phoneNum = "1234567890";  
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        int accessLevel = 1;
        String password = "ValidP@ss1";

        StandardViewResponse<User> response = librarianController.editUser(name, surname, username, salary, phoneNum, 1, gender, accessLevel, birthDate, password);

        assertEquals("Phone number must be of specified format +355 6X XXX XXXX!", response.getErrorMessage(), "Librarian edit should fail due to invalid phone number format.");
    }



    @Test
    void testIsUniqueUsername_uniqueUsername() {
        String uniqueUsername = "unique_user";
        boolean result = LibrarianController.isUniqueUsername(uniqueUsername);
        assertTrue(result, "Username should be unique and return true.");
    }


    @Test
    void testIsUniqueUsername_caseSensitive() {
        String caseSensitiveUsername = "John_Doe";
        boolean result = LibrarianController.isUniqueUsername(caseSensitiveUsername);
        assertTrue(result, "Username should be case-sensitive and considered unique.");
    }

    @Test
    void testIsUniqueUsername_emptyList() {
        FileController.users.clear();
        String username = "new_user";
        boolean result = LibrarianController.isUniqueUsername(username);
        assertTrue(result, "With no users in the list, any username should be considered unique.");
    }


    @Test
    void testAddLibrarian_nonLibrarianUser() {

        User nonLibrarian = new User("Alice", "Smith", "alice_smith", Roles.Manager, "password789", 4000, "555666777", LocalDate.now(), Gender.Female, 3);

        LibrarianController.addLibrarian(nonLibrarian);
        assertTrue(FileController.users.contains(nonLibrarian), "User should be added to the users list, even if not a librarian.");
    }

    @Test
    void testChangeAccessLevel() {
        int newAccessLevel = 5;
        LibrarianController librarianController = new LibrarianController();

        librarianController.changeAccessLevel(newAccessLevel);

        for (User user : FileController.users) {
            if (user instanceof Librarian) {
                assertEquals(newAccessLevel, user.getAccessLevel(), "Access level should be updated to the new level for Librarians");
            } else {
                assertNotEquals(newAccessLevel, user.getAccessLevel(), "Non-Librarian users' access level should remain unchanged");
            }
        }
    }


}
