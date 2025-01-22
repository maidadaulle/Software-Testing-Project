package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controllers.FileController;
import Controllers.LibrarianController;
import Models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

class LibrarianControllerTest {
    private LibrarianController librarianController;
    private FileController fileController;
    private Librarian librarian;

    @BeforeEach
    void setUp() {
        librarianController = new LibrarianController();
        FileController.transactions = new ArrayList<>();
        fileController = new FileController();
        librarian = new Librarian();
        librarian.setUsername("librarian");
        librarian.setPassword("Librarian@123");
        librarian.setName("John");
        librarian.setSurname("Doe");
        librarian.setAccessLevel(1);
        fileController.addUser(librarian);

        Librarian librarian1 = new Librarian();
        librarian1.setUsername("librarian1");
        FileController.users.add(librarian1);

        Librarian librarian2 = new Librarian();
        librarian2.setUsername("librarian2");
        FileController.users.add(librarian2);
    }

    @AfterEach
    public void tearDown() {
        FileController.transactions.clear();
    }

    @Test
    void testAddUser_invalidPhoneNumber() {
        String phoneNum = "1234567890";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        String checkPassword = "ValidP@ss1";

        StandardViewResponse<User> response = librarianController.addUser("John", "Doe", "johndoe", "ValidP@ss1", "3000", phoneNum, birthDate, gender, 1, checkPassword);
        assertEquals("Phone number must be of specified format +355 6X XXX XXXX!", response.getErrorMessage());
    }

    @Test
    void testDeleteUserById_invalidId() {
        int userId = 999;
        boolean result = librarianController.deleteUserById(userId);
        assertFalse(result);
    }

    @Test
    void testTotalNoBooksSoldByLibrarian_EmptyBills() {
        fileController.transactions.clear();
        int totalSold = librarianController.totalNoBooksSoldByLibrarian(librarian);
        assertEquals(0, totalSold);
    }

    @Test
    void testIsUniqueUsername_uniqueUsername() {
        String uniqueUsername = "unique_user";
        boolean result = LibrarianController.isUniqueUsername(uniqueUsername);
        assertTrue(result);
    }

    @Test
    void testIsUniqueUsername_caseSensitive() {
        String caseSensitiveUsername = "John_Doe";
        boolean result = LibrarianController.isUniqueUsername(caseSensitiveUsername);
        assertTrue(result);
    }

    @Test
    void testIsUniqueUsername_emptyList() {
        FileController.users.clear();
        String username = "new_user";
        boolean result = LibrarianController.isUniqueUsername(username);
        assertTrue(result);
    }

    @Test
    void testAddLibrarian_nonLibrarianUser() {
        User nonLibrarian = new User("Alice", "Smith", "alice_smith", Roles.Manager, "password789", 4000, "555666777", LocalDate.now(), Gender.Female, 3);
        LibrarianController.addLibrarian(nonLibrarian);
        assertTrue(FileController.users.contains(nonLibrarian));
    }

    @Test
    void testChangeAccessLevel() {
        int newAccessLevel = 5;
        librarianController.changeAccessLevel(newAccessLevel);
        for (User user : FileController.users) {
            if (user instanceof Librarian) {
                assertEquals(newAccessLevel, user.getAccessLevel());
            } else {
                assertNotEquals(newAccessLevel, user.getAccessLevel());
            }
        }
    }

    @Test
    public void testTotalNoBillsByLibrarian_NoMatchingBills() {
        Librarian librarian = new Librarian(null, "John Doe");
        FileController.transactions.add(new Bill(2, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Purchased));
        FileController.transactions.add(new Bill(1, new ArrayList<>(), new ArrayList<>(), 200, BillsType.Bought));
        int result = librarianController.totalNoBillsByLibrarian(librarian);
        assertEquals(0, result);
    }

    @Test
    public void testTotalNoBillsByLibrarian_SomeMatchingBills() {
        Librarian librarian = new Librarian(null, "John Doe");
        FileController.transactions.add(new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Sold));
        FileController.transactions.add(new Bill(1, new ArrayList<>(), new ArrayList<>(), 200, BillsType.Purchased));
        FileController.transactions.add(new Bill(2, new ArrayList<>(), new ArrayList<>(), 150, BillsType.Sold));
        int result = librarianController.totalNoBillsByLibrarian(librarian);
        assertEquals(1, result);
    }

    @Test
    public void testTotalNoBooksSoldByLibrarian_NoMatchingBills() {
        Librarian librarian = new Librarian("John", "Doe", "jdoe", "password123", 50000, "1234567890", Gender.MALE, LocalDate.of(2020, 1, 1), 1);
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);

        FileController.transactions.add(new Bill(2, books, quantities, 100, BillsType.Purchased));
        FileController.transactions.add(new Bill(1, books, quantities, 200, BillsType.Bought));
        int result = librarianController.totalNoBooksSoldByLibrarian(librarian);
        assertEquals(0, result);
    }

    @Test
    public void testTotalNoBooksSoldByLibrarian_SomeMatchingBills() {
        Librarian librarian = new Librarian("John", "Doe", "jdoe", "password123", 50000, "1234567890", Gender.MALE, LocalDate.of(2020, 1, 1), 1);
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);

        FileController.transactions.add(new Bill(1, books, quantities, 100, BillsType.Sold));
        FileController.transactions.add(new Bill(1, books, quantities, 200, BillsType.Purchased));
        FileController.transactions.add(new Bill(2, books, quantities, 150, BillsType.Sold));
        int result = librarianController.totalNoBooksSoldByLibrarian(librarian);
        assertEquals(5, result);
    }

    @Test
    public void testTotalNoBooksSoldByLibrarian_EmptyTransactions() {
        Librarian librarian = new Librarian("John", "Doe", "jdoe", "password123", 50000, "1234567890", Gender.MALE, LocalDate.of(2020, 1, 1), 1);
        int result = librarianController.totalNoBooksSoldByLibrarian(librarian);
        assertEquals(0, result);
    }

    @Test
    public void testCalculateMoneyMadeThisYear_NoMatchingBills() {
        Librarian librarian = new Librarian("John", "Doe", "jdoe", "password123", 50000, "1234567890", Gender.MALE, LocalDate.of(2020, 1, 1), 1);
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);

        FileController.transactions.add(new Bill(2, books, quantities, 100, BillsType.Purchased));
        FileController.transactions.add(new Bill(1, books, quantities, 200, BillsType.Bought));
        double result = librarianController.calculateMoneyMadeThisYear(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeThisYear_EmptyTransactions() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        double result = librarianController.calculateMoneyMadeThisYear(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeThisYear_TransactionsBefore12Months() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);
        FileController.transactions.add(new Bill(1, books, quantities, 100, BillsType.Sold));
        double result = librarianController.calculateMoneyMadeThisYear(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeThisMonth_NoMatchingBills() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);
        FileController.transactions.add(new Bill(2, books, quantities, 100, BillsType.Purchased));
        FileController.transactions.add(new Bill(1, books, quantities, 200, BillsType.Bought));
        double result = librarianController.calculateMoneyMadeThisMonth(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeThisMonth_EmptyTransactions() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        double result = librarianController.calculateMoneyMadeThisMonth(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeThisMonth_TransactionsBefore1Month() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);
        FileController.transactions.add(new Bill(1, books, quantities, 100, BillsType.Sold));
        double result = librarianController.calculateMoneyMadeThisMonth(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeToday_NoMatchingBills() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);
        FileController.transactions.add(new Bill(2, books, quantities, 100, BillsType.Purchased));
        FileController.transactions.add(new Bill(1, books, quantities, 200, BillsType.Sold));
        double result = librarianController.calculateMoneyMadeToday(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeToday_EmptyTransactions() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        double result = librarianController.calculateMoneyMadeToday(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateMoneyMadeToday_BillsFromPreviousDays() {
        Librarian librarian = new Librarian(
                "John", "Doe", "jdoe", "password123", 50000, "1234567890",
                Gender.MALE, LocalDate.of(2020, 1, 1), 1
        );
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);
        FileController.transactions.add(new Bill(1, books, quantities, 100, BillsType.Sold));
        double result = librarianController.calculateMoneyMadeToday(librarian);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testDeleteUserById_UserDoesNotExist() {
        FileController.users.add(new User("jdoe", "password", Roles.Librarian));
        FileController.users.add(new User("jsmith", "password", Roles.Librarian));
        boolean result = librarianController.deleteUserById(999);
        assertFalse(result);
    }

    @Test
    public void testEditUser_EmptyFields() {
        String newName = "";
        String newSurname = "";
        String newUsername = "";
        String newSalary = "";
        String newPhoneNum = "+355 678 123 4567";
        Gender newGender = Gender.MALE;
        LocalDate newBirthDate = LocalDate.of(1990, 5, 12);
        String newPassword = "newpassword";
        int newAccessLevel = 2;
        StandardViewResponse<User> response = librarianController.editUser(newName, newSurname, newUsername, newSalary, newPhoneNum, librarian.getId(), newGender, newAccessLevel, newBirthDate, newPassword);
        assertEquals("Fields are empty!", response.getErrorMessage());
        assertNotNull(response.getErrorMessage());
    }

    @Test
    public void testEditUser_NameTooShort() {
        String newName = "Jo";
        String newSurname = "Doe";
        String newUsername = "johndoe";
        String newSalary = "50000";
        String newPhoneNum = "+355 678 123 4567";
        Gender newGender = Gender.MALE;
        LocalDate newBirthDate = LocalDate.of(1990, 5, 12);
        String newPassword = "password";
        int newAccessLevel = 2;
        StandardViewResponse<User> response = librarianController.editUser(newName, newSurname, newUsername, newSalary, newPhoneNum, librarian.getId(), newGender, newAccessLevel, newBirthDate, newPassword);
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    @Test
    public void testEditUser_NameTooLong() {
        String newName = "AveryLongNameThatExceedsTwentyChars";
        String newSurname = "Doe";
        String newUsername = "johndoe";
        String newSalary = "50000";
        String newPhoneNum = "+355 678 123 4567";
        Gender newGender = Gender.MALE;
        LocalDate newBirthDate = LocalDate.of(1990, 5, 12);
        String newPassword = "password";
        int newAccessLevel = 2;
        StandardViewResponse<User> response = librarianController.editUser(newName, newSurname, newUsername, newSalary, newPhoneNum, librarian.getId(), newGender, newAccessLevel, newBirthDate, newPassword);
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    @Test
    public void testEditUser_NameWithNumbers() {
        String newName = "John123";
        String newSurname = "Doe";
        String newUsername = "johndoe";
        String newSalary = "50000";
        String newPhoneNum = "+355 678 123 4567";
        Gender newGender = Gender.MALE;
        LocalDate newBirthDate = LocalDate.of(1990, 5, 12);
        String newPassword = "password";
        int newAccessLevel = 2;
        StandardViewResponse<User> response = librarianController.editUser(newName, newSurname, newUsername, newSalary, newPhoneNum, librarian.getId(), newGender, newAccessLevel, newBirthDate, newPassword);
        assertEquals("Name can't contain numbers!", response.getErrorMessage());
    }

    @Test
    public void testEditUser_NameWithSpecialCharacters() {
        String newName = "John#Doe";
        String newSurname = "Doe";
        String newUsername = "johndoe";
        String newSalary = "50000";
        String newPhoneNum = "+355 678 123 4567";
        Gender newGender = Gender.MALE;
        LocalDate newBirthDate = LocalDate.of(1990, 5, 12);
        String newPassword = "password";
        int newAccessLevel = 2;
        StandardViewResponse<User> response = librarianController.editUser(newName, newSurname, newUsername, newSalary, newPhoneNum, librarian.getId(), newGender, newAccessLevel, newBirthDate, newPassword);
        assertEquals("Name can't contain special characters!", response.getErrorMessage());
    }

    @Test
    public void testEditUser_InvalidPhoneNumberFormat() {
        String newName = "John";
        String newSurname = "Doe";
        String newUsername = "johndoe";
        String newSalary = "50000";
        String invalidPhoneNum = "123456";
        Gender newGender = Gender.MALE;
        LocalDate newBirthDate = LocalDate.of(1990, 5, 12);
        String newPassword = "password";
        int newAccessLevel = 2;

        StandardViewResponse<User> response = librarianController.editUser(newName, newSurname, newUsername, newSalary, invalidPhoneNum, librarian.getId(), newGender, newAccessLevel, newBirthDate, newPassword);

        assertEquals("Phone number must be of specified format +355 6X XXX XXXX!", response.getErrorMessage());
    }

    @Test
    public void testEditUser_PhoneNumberValidation() {
        String invalidPhoneNum = "123456789";
        LocalDate validDate = LocalDate.now().minusYears(30);

        StandardViewResponse<User> response = librarianController.editUser(
                "John", "Doe", "johndoe", "50000", invalidPhoneNum, librarian.getId(),
                Gender.MALE, 1, validDate, "password");

        assertEquals("Phone number must be of specified format +355 6X XXX XXXX!", response.getErrorMessage());
    }

    @Test
    void testFindLibrarian_ValidIndex() {
        Librarian result = librarianController.findLibrarian(1);
        assertNotNull(result);
        assertEquals("librarian1", result.getUsername());
    }

    @Test
    void testFindLibrarian_EmptyList() {
        FileController.users.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            librarianController.findLibrarian(0);
        });
    }

    @Test
    void testDeleteLibrarianByUsername_InvalidUsername() {
        librarianController.deleteLibrarianByUsername("nonexistent");
        assertEquals(4, FileController.users.size());
    }

    @Test
    void testDeleteLibrarianByUsername_EmptyList() {
        FileController.users.clear();
        librarianController.deleteLibrarianByUsername("librarian1");
        assertEquals(0, FileController.users.size());
    }

    @Test
    void testDeleteLibrarianById_EmptyList() {
        FileController.users.clear();
        boolean result = librarianController.deleteUserById(1);
        assertFalse(result);
    }


}


