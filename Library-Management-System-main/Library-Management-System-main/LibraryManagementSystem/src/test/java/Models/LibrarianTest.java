package Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.time.*;
import org.junit.jupiter.api.BeforeEach;

class LibrarianTest {

    private Librarian librarian;

    @BeforeEach
    public void setup() {
        librarian = new Librarian();
    }

    @Test
    void testLibrarianConstructor() {
        String username = "librarianUser";
        String password = "securePassword";

        Librarian librarian = new Librarian(username, password);

        assertNotNull(librarian, "Librarian object should not be null");
        assertEquals(username, librarian.getUsername(), "Username should be set correctly");
        assertEquals(password, librarian.getPassword(), "Password should be set correctly");
        assertEquals(Roles.Librarian, librarian.getUserRole(), "User role should be Librarian");
        assertTrue(librarian.getId() > 0, "ID should be greater than 0 (should be incremented from the superclass)");
    }

    @Test
    void testLibrarianConstructorWithDetails() {
        String name = "John";
        String surname = "Doe";
        String username = "johndoe";
        String password = "securePassword";
        double salary = 50000.0;
        String phoneNum = "123-456-7890";
        Gender gender = Gender.MALE;
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        int accessLevel = 5;

        Librarian librarian = new Librarian(name, surname, username, password, salary, phoneNum, gender, birthDate, accessLevel);

        assertNotNull(librarian, "Librarian object should not be null");
        assertEquals(name, librarian.getName(), "Name should be set correctly");
        assertEquals(surname, librarian.getSurname(), "Surname should be set correctly");
        assertEquals(username, librarian.getUsername(), "Username should be set correctly");
        assertEquals(password, librarian.getPassword(), "Password should be set correctly");
        assertEquals(salary, librarian.getSalary(), "Salary should be set correctly");
        assertEquals(phoneNum, librarian.getPhoneNum(), "Phone number should be set correctly");
        assertEquals(gender, librarian.getGender(), "Gender should be set correctly");
        assertEquals(birthDate, librarian.getBirthDate(), "Birthdate should be set correctly");
        assertEquals(accessLevel, librarian.getAccessLevel(), "Access level should be set correctly");
        assertEquals(Roles.Librarian, librarian.getUserRole(), "User role should be Librarian");
    }


    @Test
    public void testMoneyMadeInDay_WithNullDatesSold() {
        librarian.setDatesSold(null);
        librarian.setMoneyMadeDates(null);
        double result = librarian.moneyMadeInDay();
        assertEquals(0, result, "Money made in day should be 0 when datesSold and moneyMadeDates are null");
    }

    @Test
    public void testMoneyMadeInDay_WithNoSalesToday() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        datesSold.add(new Date(124, 0, 20));
        moneyMadeDates.add(100.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInDay();
        assertEquals(0, result, "Money made in day should be 0 when there are no sales today");
    }

    @Test
    public void testMoneyMadeInDay_WithSalesToday() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        datesSold.add(today);
        moneyMadeDates.add(100.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInDay();
        assertEquals(100.0, result, "Money made in day should be the amount of sales on today");
    }

    @Test
    public void testMoneyMadeInDay_WithMultipleSalesToday() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        datesSold.add(today);
        moneyMadeDates.add(100.0);
        datesSold.add(today);
        moneyMadeDates.add(50.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInDay();
        assertEquals(150.0, result, "Money made in day should be the sum of today's sales");
    }

    @Test
    public void testMoneyMadeInDay_WithSalesOnDifferentDays() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 1000 * 60 * 60 * 24);

        datesSold.add(today);
        moneyMadeDates.add(100.0);
        datesSold.add(yesterday);
        moneyMadeDates.add(50.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInDay();
        assertEquals(100.0, result, "Money made in day should only return sales for today");
    }

    @Test
    public void testMoneyMadeInMonth_WithNullDatesSold() {
        librarian.setDatesSold(null);
        librarian.setMoneyMadeDates(null);
        double result = librarian.moneyMadeInMonth();
        assertEquals(0, result, "Money made in day should be 0 when datesSold and moneyMadeDates are null");
    }

    @Test
    public void testMoneyMadeInMonth_WithNoSalesThisMonth() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        datesSold.add(new Date(124, 0, 20));
        moneyMadeDates.add(100.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInMonth();
        assertEquals(0, result, "Money made in month should be 0 when there are no sales in the current month");
    }

    @Test
    public void testMoneyMadeInMonth_WithSalesThisMonth() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        datesSold.add(today);
        moneyMadeDates.add(200.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInMonth();
        assertEquals(200.0, result, "Money made in month should match the sales amount for the current month");
    }

    @Test
    public void testMoneyMadeInMonth_WithMultipleSalesThisMonth() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        datesSold.add(today);
        moneyMadeDates.add(200.0);
        datesSold.add(today);
        moneyMadeDates.add(50.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInMonth();
        assertEquals(250.0, result, "Money made in month should be the sum of sales in the current month");
    }

    @Test
    public void testMoneyMadeInMonth_WithSalesFromDifferentMonths() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        Date lastMonth = new Date(today.getTime() - 1000 * 60 * 60 * 24 * 30);

        datesSold.add(today);
        moneyMadeDates.add(200.0);
        datesSold.add(lastMonth);
        moneyMadeDates.add(150.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInMonth();
        assertEquals(200.0, result, "Money made in month should only return sales for the current month");
    }

    @Test
    public void testMoneyMadeInYear_WithNullDatesSold() {
        librarian.setDatesSold(null);
        librarian.setMoneyMadeDates(null);
        double result = librarian.moneyMadeInYear();
        assertEquals(0, result, "Money made in day should be 0 when datesSold and moneyMadeDates are null");
    }

    @Test
    public void testMoneyMadeInYear_WithNoSalesThisYear() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();


        datesSold.add(new Date(123, 0, 20));
        moneyMadeDates.add(100.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInYear();
        assertEquals(0, result, "Money made in year should be 0 when there are no sales in the current year");
    }

    @Test
    public void testMoneyMadeInYear_WithSalesThisYear() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        datesSold.add(today);
        moneyMadeDates.add(200.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInYear();
        assertEquals(200.0, result, "Money made in year should match the sales amount for the current year");
    }

    @Test
    public void testMoneyMadeInYear_WithMultipleSalesThisYear() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        datesSold.add(today);
        moneyMadeDates.add(200.0);
        datesSold.add(today);
        moneyMadeDates.add(50.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInYear();
        assertEquals(250.0, result, "Money made in year should be the sum of sales in the current year");
    }

    @Test
    public void testMoneyMadeInYear_WithSalesFromDifferentYears() {
        List<Date> datesSold = new ArrayList<>();
        List<Double> moneyMadeDates = new ArrayList<>();

        Date today = new Date();
        Date lastYear = new Date(today.getTime() - 1000L * 60 * 60 * 24 * 365);

        datesSold.add(today);
        moneyMadeDates.add(200.0);
        datesSold.add(lastYear);
        moneyMadeDates.add(150.0);

        librarian.setDatesSold((ArrayList<Date>) datesSold);
        librarian.setMoneyMadeDates((ArrayList<Double>) moneyMadeDates);

        double result = librarian.moneyMadeInYear();
        assertEquals(200.0, result, "Money made in year should only return sales for the current year");
    }
}
