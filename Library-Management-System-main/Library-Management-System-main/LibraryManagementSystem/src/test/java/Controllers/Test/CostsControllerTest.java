package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controllers.CostsController;
import Controllers.FileController;
import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.time.ZonedDateTime;

public class CostsControllerTest {

    private CostsController costsController;
    private List<Bill> mockTransactions;

    @BeforeEach
    public void setup() {
        costsController = new CostsController();
        mockTransactions = new ArrayList<>();

        Book mockBook1 = new Book("ISBN1", "Book 1", new Author("Ismail", "Kadare", Gender.Male), new ArrayList<>(), "Supplier 1", 10, 20, 30, "Address 1", Date.from(ZonedDateTime.now().minusDays(1).toInstant()));
        Book mockBook2 = new Book("ISBN2", "Book 2", new Author("David", "Williams", Gender.Male), new ArrayList<>(), "Supplier 2", 15, 25, 35, "Address 2", Date.from(ZonedDateTime.now().minusMonths(1).toInstant()));
        ArrayList<Book> books = new ArrayList<>();
        books.add(mockBook1);
        books.add(mockBook2);

        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);
        quantities.add(2);

        Bill mockBill = new Bill(1, books, quantities, 100, BillsType.Bought);

        mockTransactions.add(mockBill);
        FileController.transactions = (ArrayList<Bill>) mockTransactions;
    }

    @Test
    public void testGetBooksBoughtToday() {
        List<Book> result = costsController.getBooksBoughtToday();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetBooksBoughtThisMonth() {
        List<Book> result = costsController.getBooksBoughtThisMonth();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBooksBoughtThisYear() {
        List<Book> result = costsController.getBooksBoughtThisYear();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBillsOfBoughtBooksThisYear() {
        List<Integer> result = costsController.getBillsOfBoughtBooksThisYear();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBillsOfBoughtBooksThisMonth() {
        List<Integer> result = costsController.getBillsOfBoughtBooksThisMonth();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetDailyCost() {
        double result = costsController.getDailyCost();
        assertEquals(100, result);
    }

    @Test
    public void testGetMonthlyCost() {
        double result = costsController.getMonthlyCost();
        assertEquals(100, result);
    }

    @Test
    public void testGetYearlyCost() {
        double result = costsController.getYearlyCost();
        assertEquals(100, result);
    }

    @Test
    public void testNumberOfBooksBoughtToday() {
        int result = costsController.numberOfBooksBoughtToday();
        assertEquals(0, result);
    }

    @Test
    public void testNumberOfBooksBoughtThisMonth() {
        int result = costsController.numberOfBooksBoughtThisMonth();
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfBooksBoughtThisYear() {
        int result = costsController.numberOfBooksBoughtThisYear();
        assertEquals(3, result);
    }

    @Test
    public void testGetBillsOfBoughtBooksToday_NoBooksBoughtToday() {
        mockTransactions.clear();
        Book mockBook1 = new Book("ISBN1", "Book 1", new Author("Ismail", "Kadare", Gender.Male), new ArrayList<>(), "Supplier 1", 10, 20, 30, "Address 1", Date.from(ZonedDateTime.now().minusDays(2).toInstant()));
        ArrayList<Book> books = new ArrayList<>();
        books.add(mockBook1);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);

        Bill mockBill = new Bill(1, books, quantities, 30, BillsType.Bought);
        mockTransactions.add(mockBill);

        FileController.transactions = new ArrayList<>(mockTransactions);

        List<Integer> result = costsController.getBillsOfBoughtBooksToday();
        assertTrue(result.isEmpty());
    }
}

