package UnitTesting;

import Controllers.FileController;
import Controllers.ManagerController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class ManagerControllerTest {

    private ManagerController managerController;

    @BeforeEach
    void setUp() {
        managerController = new ManagerController();
        FileController.users = new ArrayList<>();
        FileController.transactions = new ArrayList<>();
    }

    @Test
    void testEditUser_Success() {
        Manager manager = new Manager("Rexhe", "Balla", "rexhe", Roles.Manager, "password", 5000, "+355 69 123 4567", Gender.Male, LocalDate.of(2004, 1, 1), 1);
        FileController.users.add(manager);
        System.out.println(FileController.users);
        StandardViewResponse<User> response = managerController.editUser(
                "Rexhens", "Balla", "rexhe", "6000", "+355 69 987 6543", 1, Gender.Female, 2, LocalDate.of(2004, 5, 15), "newPassword"
        );
        assertNotNull(response);
        assertEquals("", response.getErrorMessage());
        assertEquals("Rexhens", response.getUser().getName());
        assertEquals("Balla", response.getUser().getSurname());
        assertEquals("rexhe", response.getUser().getUsername());

    }

    @Test
    void testEditUser_InvalidName() {
        Manager manager = new Manager("Rexhens", "Balla", "rexhe", Roles.Manager, "password", 5000, "+355 69 123 4567", Gender.Male, LocalDate.of(1990, 1, 1), 1);
        FileController.users.add(manager);

        StandardViewResponse<User> response = managerController.editUser(
                "", "Smith", "janesmith", "6000", "+355 69 987 6543", 1, Gender.Female, 2, LocalDate.of(1995, 5, 15), "newPassword"
        );

        assertNotNull(response);
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testAddUser_Success() {
        StandardViewResponse<User> response = managerController.addUser(
                "Rexhens", "Balla", "rexhe", "pass12345%$F", "6000", "+355 69 987 6543",  LocalDate.of(2004, 5, 15),  Gender.Male,1,
                "pass12345%$F");

        assertNotNull(response);
        assertEquals("", response.getErrorMessage());
        assertNotNull(response.getUser());
        assertEquals("Rexhens", response.getUser().getName());
    }

    @Test
    void testAddUser_InvalidPhoneNumber() {
        StandardViewResponse<User> response = managerController.addUser(
                "Rexhens", "Balla", "rexhe", "pass12345%$F", "6000", "+355 69 987 65",  LocalDate.of(2004, 5, 15),  Gender.Male,1,
                "pass12345%$F");

        assertNotNull(response);
        assertEquals("Phone number must be of specified format +355 6X XXX XXXX!", response.getErrorMessage());
    }

    @Test
    void testFindUserById() {
        Manager manager =new Manager("Rexhe", "Balla", "rexhe", Roles.Manager, "password231223J%$", 5000, "+355 69 123 4567", Gender.Male, LocalDate.of(2004, 1, 1), 1);
        FileController.users.add(manager);
        System.out.println(FileController.users);
        Manager result = managerController.findUserById(1);

        assertNotNull(result);
        assertEquals("Rexhe", result.getName());
    }

    @Test
    void testDeleteUserById_Success() {
        Manager manager = new Manager("Rexhe", "Balla", "rexhe", Roles.Manager, "password", 5000, "+355 69 123 4567", Gender.Male, LocalDate.of(2004, 1, 1), 1);
        FileController.users.add(manager);

        boolean result = managerController.deleteUserById(1);

        assertTrue(result);
        assertTrue(FileController.users.isEmpty());
    }

    @Test
    void testCalculateMoneySpendThisMonth() {
        Manager manager = new Manager("Rexhe", "Balla", "rexhe", Roles.Manager, "password", 5000, "+355 69 123 4567", Gender.Male, LocalDate.of(2004, 1, 1), 1);

        Bill transaction = new Bill(manager.getId(),new Book("ISBN", "bookTitle","authorName",20, 30), 1,  100,BillsType.Bought);
        FileController.transactions.add(transaction);

        double result = managerController.calculateMoneySpendThisMonth(manager);

        assertEquals(100.0, result);
    }

    @Test
    void testChangeAccessLevel() {
        Manager manager = new Manager("John", "Doe", "johndoe", Roles.Manager, "password", 5000, "+355 69 123 4567", Gender.Male, LocalDate.of(1990, 1, 1), 1);
        FileController.users.add(manager);

        managerController.changeAccessLevel(5);

        assertEquals(5, manager.getAccessLevel());
    }
}