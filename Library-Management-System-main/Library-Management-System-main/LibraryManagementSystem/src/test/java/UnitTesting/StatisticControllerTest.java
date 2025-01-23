package UnitTesting;

import Controllers.FileController;
import Controllers.StatisticsController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticControllerTest {
    private StatisticsController controller;
    private FileController mockFileController;

    @BeforeEach
    void setUp() {
        mockFileController = new FileController();
        controller = new StatisticsController(mockFileController);

        // Set up categories
        var categories = new ArrayList<Category>();
        categories.add(new Category("Category 1"));

        // Set up quantities
        var quantities = new ArrayList<Integer>();
        quantities.add(1);

        // Set up books
        var books = new ArrayList<Book>();
        books.add(new Book(
                "ISBN",
                "Book",
                new Author("name", "surname", Gender.Male),
                categories,
                "",
                9,
                8,
                10,
                "",
                java.sql.Date.valueOf(LocalDate.now())
        ));

        // Set up mock transactions
        mockFileController.transactions = new ArrayList<Bill>(Arrays.asList(
                new Bill(1, books, quantities, 30, BillsType.Sold),
                new Bill(2, books, quantities, 20, BillsType.Sold),
                new Bill(3, books, quantities, 20, BillsType.Bought)


        ));

        // Set up mock users
        mockFileController.users = new ArrayList<>(Arrays.asList(
                new Admin("admin", "admin",1000),
                new Manager()
        ));
        System.out.println("Mocked users " + mockFileController.users);
    }

    @Test
    void testNumberOfBooksSoldDuringPeriod_ValidDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 23);

        StandardViewResponse<Integer> response = controller.numberOfBooksSoldDuringPeriod(startDate, endDate);
        System.out.println(response);
        assertNotNull(response);
        assertNull(response.getErrorMessage());
        assertEquals(2, response.getUser()); // Total quantity is 1+1 = 2
    }

    @Test
    void testNumberOfBooksSoldDuringPeriod_NullDates() {
        StandardViewResponse<Integer> response = controller.numberOfBooksSoldDuringPeriod(null, null);

        assertNotNull(response);
        assertEquals("Dates cannot be null", response.getErrorMessage());
    }

    @Test
    void testNumberOfBooksSoldDuringPeriod_EndDateBeforeStartDate() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        StandardViewResponse<Integer> response = controller.numberOfBooksSoldDuringPeriod(startDate, endDate);

        assertNotNull(response);
        assertEquals("End date cannot be before starting date", response.getErrorMessage());
    }

    @Test
    void testGetProfitThroughPeriod_ValidDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 23);

        Double profit = controller.getProfitThroughPeriod(startDate, endDate);

        assertNotNull(profit);
        assertEquals(50.0, profit); // Profit calculated based on mock data
    }

    @Test
    void testGetBookCostsThroughPeriod_ValidDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 23);

        Double cost = controller.getBookCostsThroughPeriod(startDate, endDate);

        assertNotNull(cost);
        assertEquals(20.0, cost);
    }

    @Test
    void testGetTotalSalary() {
        Double totalSalary = controller.getTotalSalary();

        assertNotNull(totalSalary);
        assertEquals(1000.0, totalSalary);
    }

    @Test
    void testNumberOfBooksBoughtDuringPeriod_ValidDates() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        StandardViewResponse<Integer> response = controller.numberOfBooksBoughtDuringPeriod(startDate, endDate);

        assertNotNull(response);
        assertNull(response.getErrorMessage());
        assertEquals(0, response.getUser()); // No "Bought" transactions in mock data
    }

    @Test
        void testNumberOfBooksBoughtDuringPeriod_InvalidDateRange() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 23);

        mockFileController.transactions = new ArrayList<>(Arrays.asList(
                new Bill(111, new ArrayList<>(Arrays.asList(new Book(
                        "123", "Tittle", new Author("Author", "Surname", Gender.Female),
                        new ArrayList<>(), "", 10, 20, 30, "", Date.valueOf(LocalDate.now())))),
                        new ArrayList<>(Arrays.asList(1)), 20, BillsType.Bought)
        ));

        StandardViewResponse<Integer> response = controller.numberOfBooksBoughtDuringPeriod(startDate, endDate);

        System.out.println("Filtered transactions: " + mockFileController.transactions); // Debugging line
        System.out.println("Response user value: " + response.getUser());                // Debugging line

        assertNotNull(response);
        assertNull(response.getErrorMessage());
        assertEquals(1, response.getUser()); // Only 1 "Bought" transaction
    }

}
