package Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    @Test
    void testManagerConstructor1() {
        String username = "manager123";
        String password = "securePass";
        Manager manager = new Manager(username, password);

        assertNotNull(manager, "Manager object should not be null");
        assertEquals(username, manager.getUsername(), "Manager username should be set correctly");
        assertEquals(password, manager.getPassword(), "Manager password should be set correctly");
        assertEquals(Roles.Manager, manager.getUserRole(), "Manager role should be set to 'Manager'");
    }

    @Test
    void testManagerConstructor2() {
        String name = "John";
        String surname = "Doe";
        String username = "manager123";
        String password = "securePass";
        double salary = 5000.00;
        String phoneNum = "123-456-7890";
        Gender gender = Gender.MALE;
        LocalDate birthDate = LocalDate.of(1980, 5, 15);
        int accessLevel = 5;

        Manager manager = new Manager(name, surname, username, Roles.Manager, password, salary, phoneNum, gender, birthDate, accessLevel);

        assertNotNull(manager, "Manager object should not be null");
        assertEquals(name, manager.getName(), "Manager name should be set correctly");
        assertEquals(surname, manager.getSurname(), "Manager surname should be set correctly");
        assertEquals(username, manager.getUsername(), "Manager username should be set correctly");
        assertEquals(password, manager.getPassword(), "Manager password should be set correctly");
        assertEquals(salary, manager.getSalary(), "Manager salary should be set correctly");
        assertEquals(phoneNum, manager.getPhoneNum(), "Manager phone number should be set correctly");
        assertEquals(gender, manager.getGender(), "Manager gender should be set correctly");
        assertEquals(birthDate, manager.getBirthDate(), "Manager birth date should be set correctly");
        assertEquals(accessLevel, manager.getAccessLevel(), "Manager access level should be set correctly");
        assertEquals(Roles.Manager, manager.getUserRole(), "Manager role should be set to 'Manager'");
    }

    @Test
    void testManagerDefaultConstructor() {
        Manager manager = new Manager();

        assertNotNull(manager, "Manager object should not be null");
        assertNull(manager.getName(), "Manager name should be null by default");
        assertNull(manager.getSurname(), "Manager surname should be null by default");
        assertNull(manager.getUsername(), "Manager username should be null by default");
        assertNull(manager.getPassword(), "Manager password should be null by default");
        assertEquals(0.0, manager.getSalary(), "Manager salary should be 0.0 by default");
        assertNull(manager.getPhoneNum(), "Manager phone number should be null by default");
        assertNull(manager.getGender(), "Manager gender should be null by default");
        assertNull(manager.getBirthDate(), "Manager birth date should be null by default");
        assertEquals(0, manager.getAccessLevel(), "Manager access level should be 0 by default");
        assertNull(manager.getUserRole(), "Manager role should be null by default");
    }
}
