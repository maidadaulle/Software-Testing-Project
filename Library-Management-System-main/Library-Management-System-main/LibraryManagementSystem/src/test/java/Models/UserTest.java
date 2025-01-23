package Models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @BeforeEach
    void setUp() {
        User.setNoUsers(0); 
    }

    @Test
    void testDefaultConstructor() {
        User user = new User();


        assertEquals(0, user.getId(), "Default ID should be 0");
        assertNull(user.getName(), "Default name should be null");
        assertNull(user.getSurname(), "Default surname should be null");
        assertNull(user.getUsername(), "Default username should be null");
        assertNull(user.getUserRole(), "Default role should be null");
        assertNull(user.getPassword(), "Default password should be null");
        assertEquals(0.0, user.getSalary(), "Default salary should be 0.0");
        assertNull(user.getPhoneNum(), "Default phone number should be null");
        assertNull(user.getBirthDate(), "Default birth date should be null");
        assertEquals(0, user.getAccessLevel(), "Default access level should be 0");
        assertNull(user.getGender(), "Default gender should be null");
    }


    @Test
    void testGetBirthDate() {
        LocalDate expectedBirthDate = LocalDate.of(1990, 1, 1);

        User user = new User();
        user.setBirthDate(expectedBirthDate);

        assertEquals(expectedBirthDate, user.getBirthDate(), "getBirthDate() should return the correct birth date");
    }


    @Test
    void testSetBirthDate() {
        User user = new User();

        LocalDate expectedBirthDate = LocalDate.of(1985, 7, 20);
        user.setBirthDate(expectedBirthDate);

        assertEquals(expectedBirthDate, user.getBirthDate(), "setBirthDate() should correctly set the birth date");
    }

    @Test
    void testGetGender() {
        User user = new User();

        Gender expectedGender = Gender.MALE;
        user.setGender(expectedGender);

        assertEquals(expectedGender, user.getGender(), "getGender() should return the correct gender value");
    }


    @Test
    void testSetGender() {
        User user = new User();

        Gender expectedGender = Gender.Female;
        user.setGender(expectedGender);

        assertEquals(expectedGender, user.getGender(), "setGender() should update the gender field correctly");
    }

    @Test
    void testUserConstructorWithBasicDetails() {
        String username = "testUser";
        String password = "testPass";
        Roles role = Roles.Librarian;

        User user = new User(username, password, role);

        assertEquals(username, user.getUsername(), "Username should match the provided value");
        assertEquals(password, user.getPassword(), "Password should match the provided value");
        assertEquals(role, user.getUserRole(), "Role should match the provided value");
        assertEquals(1, user.getId(), "ID should start from 1 for the first user");
    }

    @Test
    void testUserConstructorIDIncrement() {
        User.setNoUsers(0);

        User user1 = new User("user1", "pass1", Roles.Manager);
        User user2 = new User("user2", "pass2", Roles.ADMIN);

        assertEquals(1, user1.getId(), "First user's ID should be 1");
        assertEquals(2, user2.getId(), "Second user's ID should be 2");
    }

    @Test
    void testUserConstructorWithFullDetails() {
        String name = "John";
        String surname = "Doe";
        String username = "johndoe";
        Roles userRole = Roles.ADMIN;
        String password = "adminPass";
        double salary = 50000;
        String phoneNum = "123-456-7890";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        Gender gender = Gender.MALE;
        int accessLevel = 5;

        User user = new User(name, surname, username, userRole, password, salary, phoneNum, birthDate, gender, accessLevel);

        assertEquals(name, user.getName(), "Name should match the constructor value");
        assertEquals(surname, user.getSurname(), "Surname should match the constructor value");
        assertEquals(username, user.getUsername(), "Username should match the constructor value");
        assertEquals(userRole, user.getUserRole(), "User role should match the constructor value");
        assertEquals(password, user.getPassword(), "Password should match the constructor value");
        assertEquals(salary, user.getSalary(), "Salary should match the constructor value");
        assertEquals(phoneNum, user.getPhoneNum(), "Phone number should match the constructor value");
        assertEquals(birthDate, user.getBirthDate(), "Birthdate should match the constructor value");
        assertEquals(gender, user.getGender(), "Gender should match the constructor value");
        assertEquals(accessLevel, user.getAccessLevel(), "Access level should match the constructor value");
        assertEquals(1, user.getId(), "ID should increment correctly");
    }

    @Test
    void testGetName() {
        String expectedName = "John";
        User user = new User("John", "Doe", "johndoe", Roles.Manager, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);

        String actualName = user.getName();

        assertEquals(expectedName, actualName, "The getName() method should return the name of the user.");
    }

    @Test
    void testSetName() {

        String newName = "Jane";
        User user = new User("John", "Doe", "johndoe", Roles.Admin, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);

        user.setName(newName);
        String actualName = user.getName();

        assertEquals(newName, actualName, "The setName() method should set the name of the user.");
    }

    @Test
    void testGetSurname() {
        String expectedSurname = "Doe";
        User user = new User("John", expectedSurname, "johndoe", Roles.Librarian, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);

        String actualSurname = user.getSurname();

        assertEquals(expectedSurname, actualSurname, "The getSurname() method should return the correct surname.");
    }


    @Test
    void testSetSurname() {
        User user = new User("John", "Doe", "johndoe", Roles.Admin, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        String newSurname = "Smith";
        user.setSurname(newSurname);

        assertEquals(newSurname, user.getSurname(), "The setSurname() method should update the surname.");
    }

    @Test
    void testGetUsername() {
        User user = new User("John", "Doe", "johndoe", Roles.Librarian, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);

        String username = user.getUsername();

        assertEquals("johndoe", username, "The getUsername() method should return the correct username.");
    }

    @Test
    void testSetUsername() {
        User user = new User("John", "Doe", "johndoe", Roles.Manager, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername(), "The setUsername() method should update the username.");
    }

    @Test
    void testGetUserRole() {
        User user = new User("John", "Doe", "johndoe", Roles.ADMIN, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);

        Roles userRole = user.getUserRole();

        assertEquals(Roles.ADMIN, userRole, "The getUserRole() method should return the correct user role.");
    }

    @Test
    void testSetUserRole() {
        User user = new User("John", "Doe", "johndoe", Roles.ADMIN, "password", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        user.setUserRole(Roles.ADMIN);

        assertEquals(Roles.ADMIN, user.getUserRole(), "The setUserRole() method should correctly update the user's role.");
    }

    @Test
    void testGetPassword() {
        User user = new User("John", "Doe", "johndoe", Roles.Manager, "securePassword123", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        String password = user.getPassword();
        assertEquals("securePassword123", password, "The getPassword() method should return the correct password.");
    }

    @Test
    void testSetPassword() {
        User user = new User("John", "Doe", "johndoe", Roles.Librarian, "initialPassword123", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        user.setPassword("newPassword456");
        assertEquals("newPassword456", user.getPassword(), "The setPassword() method should correctly update the password.");
    }

    @Test
    void testGetSalary() {
        User user = new User("John", "Doe", "johndoe", Roles.ADMIN, "password123", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        double salary = user.getSalary();
        assertEquals(50000, salary, "The getSalary() method should return the correct salary value.");
    }

    @Test
    void testSetSalary() {
        User user = new User("John", "Doe", "johndoe", Roles.Librarian, "password123", 50000, "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 1);
        user.setSalary(60000);
        assertEquals(60000, user.getSalary(), "The setSalary() method should update the salary correctly.");
    }

    @Test
    void testGetAccessLevel() {
        User user = new User("Jane", "Doe", "janedoe", Roles.Manager, "password123", 75000, "111-222-3333", LocalDate.of(1990, 8, 25), Gender.Female, 3);
        int accessLevel = user.getAccessLevel();
        assertEquals(3, accessLevel, "The getAccessLevel() method should return the correct access level.");
    }

    @Test
    void testSetAccessLevel() {
        User user = new User("Jane", "Doe", "janedoe", Roles.Manager, "password123", 75000, "111-222-3333", LocalDate.of(1990, 8, 25), Gender.Female, 3);
        user.setAccessLevel(5);
        assertEquals(5, user.getAccessLevel(), "The access level should be correctly updated using setAccessLevel().");
    }

    @Test
    void testGetPhoneNum() {
        String expectedPhoneNum = "123-456-7890";
        User user = new User("Jane", "Doe", "janedoe", Roles.Manager, "password123", 75000, expectedPhoneNum, LocalDate.of(1990, 8, 25), Gender.Female, 3);
        String actualPhoneNum = user.getPhoneNum();

        assertEquals(expectedPhoneNum, actualPhoneNum, "The phone number should match the expected value.");
    }

    @Test
    void testSetPhoneNum() {
        User user = new User("Jane", "Doe", "janedoe", Roles.Manager, "password123", 75000, "", LocalDate.of(1990, 8, 25), Gender.Female, 3);

        String newPhoneNum = "987-654-3210";
        user.setPhoneNum(newPhoneNum);

        assertEquals(newPhoneNum, user.getPhoneNum(), "The phone number should be set correctly.");
    }

    @Test
    void testGetId() {
        User user = new User("JohnDoe", "password123", Roles.ADMIN);
        int id = user.getId();
        assertEquals(1, id, "The ID should be 1 for the first user.");
    }

    @Test
    void testMultipleUsersId() {

        User user1 = new User("JohnDoe", "password123", Roles.Manager);
        User user2 = new User("JaneDoe", "password456", Roles.Librarian);

        int id1 = user1.getId();
        int id2 = user2.getId();

        assertEquals(1, id1, "The ID of the first user should be 1.");
        assertEquals(2, id2, "The ID of the second user should be 2.");
    }

    @Test
    void testGetNoUsers() {
        User user1 = new User("JohnDoe", "password123", Roles.Manager);
        User user2 = new User("JaneDoe", "password456", Roles.Manager);

        int totalUsers = User.getNoUsers();

        assertEquals(2, totalUsers, "The number of users should be 2 after creating two users.");
    }

    @Test
    void testNoUsersWhenNoUsersCreated() {
        int totalUsers = User.getNoUsers();
        assertEquals(0, totalUsers, "The number of users should be 0 when no users have been created.");
    }

    @Test
    void testSetNoUsers() {
        User.setNoUsers(5);
        int totalUsers = User.getNoUsers();
        assertEquals(5, totalUsers, "The number of users should be set to 5.");
    }

    @Test
    void testSetNoUsersToZero() {
        User.setNoUsers(0);
        int totalUsers = User.getNoUsers();
        assertEquals(0, totalUsers, "The number of users should be set to 0.");
    }

    @Test
    void testToString() {
        User user = new User(
                "John", "Doe", "johndoe", Roles.ADMIN, "adminPass", 50000,
                "123-456-7890", LocalDate.of(1995, 5, 15), Gender.MALE, 3
        );

        String result = user.toString();

        String expected = "Id:" + user.getId() +
                "Name: John\n" +
                "Username: johndoe\n" +
                "Password: adminPass\n" +
                "Role: ADMIN\n" +
                "Salary: 50000.0\n" +
                "Phone Number: 123-456-7890";

        assertEquals(expected, result, "The toString() method did not return the expected string representation.");
    }


}
