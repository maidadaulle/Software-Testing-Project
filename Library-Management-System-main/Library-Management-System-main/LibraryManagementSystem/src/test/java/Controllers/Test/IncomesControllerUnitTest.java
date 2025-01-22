package Controllers.Test;


import Controllers.IncomesController;
import Controllers.FileController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IncomesControllerUnitTest {

    private IncomesController incomesController;
    private List<Bill> mockTransactions;

    @BeforeEach
    public void setup() {

        incomesController = new IncomesController();

        mockTransactions = new ArrayList<>();

        // Mock data for Books and Bills
        Book mockBook1 = new Book("ISBN1", "Book 1", new Author("John", "Doe", Gender.Male), new ArrayList<>(), "Supplier 1", 10, 20, 30, "Address 1", Date.from(ZonedDateTime.now().toInstant()));
        Book mockBook2 = new Book("ISBN2", "Book 2", new Author("Jane", "Smith", Gender.Female), new ArrayList<>(), "Supplier 2", 15, 25, 35, "Address 2", Date.from(ZonedDateTime.now().minusMonths(1).toInstant()));
        Book mockBook3 = new Book("ISBN3", "Book 3", new Author("Alice", "Johnson", Gender.Female), new ArrayList<>(), "Supplier 3", 20, 30, 40, "Address 3", Date.from(ZonedDateTime.now().minusYears(1).toInstant()));

        ArrayList<Book> books = new ArrayList<>();
        books.add(mockBook1);
        books.add(mockBook2);
        books.add(mockBook3);

        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);
        quantities.add(2);
        quantities.add(3);

        Bill mockBill = new Bill(1, books, quantities, 150, BillsType.Sold);
        mockTransactions.add(mockBill);

        // Mock the static reference to transactions in FileController
        FileController.transactions = (ArrayList<Bill>) mockTransactions;
    }

    @Test
    public void testGetDailyIncome() {
        double dailyIncome = incomesController.getDailyIncome();
        assertEquals(150, dailyIncome);
    }

    @Test
    public void testGetMonthlyIncome() {
        double monthlyIncome = incomesController.getMonthlyIncome();
        assertEquals(150, monthlyIncome);
    }

    @Test
    public void testGetYearlyIncome() {
        double yearlyIncome = incomesController.getYearlyIncome();
        assertEquals(150, yearlyIncome);
    }

    @Test
    public void testNumberOfBooksSoldToday() {
        int booksSoldToday = incomesController.numberOfBooksSoldToday();
        assertEquals(1, booksSoldToday);
    }

    @Test
    public void testNumberOfBooksSoldThisMonth() {
        int booksSoldThisMonth = incomesController.numberOfBooksSoldThisMonth();
        assertEquals(1, booksSoldThisMonth);
    }

    @Test
    public void testNumberOfBooksSoldThisYear() {
        int booksSoldThisYear = incomesController.numberOfBooksSoldThisYear();
        assertEquals(3, booksSoldThisYear);
    }

    @Test
    public void testGetBillsOfSoldBooksThisYear() {
        ArrayList<Integer> billsThisYear = incomesController.getBillsOfSoldBooksThisYear();
        assertEquals(2, billsThisYear.size());
    }

    @Test
    public void testGetBillsOfSoldBooksThisMonth() {
        ArrayList<Integer> billsThisMonth = incomesController.getBillsOfSoldBooksThisMonth();
        assertEquals(1, billsThisMonth.size());
    }

    @Test
    public void testGetBillsOfSoldBooksToday() {
        ArrayList<Integer> billsToday = incomesController.getBillsOfSoldBooksToday();
        assertEquals(1, billsToday.size());
    }
}
