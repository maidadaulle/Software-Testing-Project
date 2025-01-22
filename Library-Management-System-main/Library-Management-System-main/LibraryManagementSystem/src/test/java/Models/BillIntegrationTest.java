package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BillIntegrationTest {
    private ArrayList<Book> books;
    private ArrayList<Integer> quantities;
    private Bill bill;

    private Bill billSold, billPurchased, billBought;

    @BeforeEach
    void setUp() {
        books = new ArrayList<>();
        quantities = new ArrayList<>();

        Book mockBook1 = new Book("ISBN1", "Book 1", new Author("John", "Doe", Gender.Male), new ArrayList<>(), "Supplier 1", 10, 20, 30, "Address 1", new Date());
        Book mockBook2 = new Book("ISBN2", "Book 2", new Author("Jane", "Smith", Gender.Female), new ArrayList<>(), "Supplier 2", 15, 25, 35, "Address 2", new Date());

        books.add(mockBook1);
        books.add(mockBook2);

        quantities.add(2);
        quantities.add(3);

        Bill.setTotalBills(0);
    }

    @Test
    void testBillCreationWithBooksAndQuantitiesIntegration() {
        int totalPrice = 1500 * 2 + 1800 * 3;
        bill = new Bill(101, books, quantities, totalPrice, BillsType.Sold);

        assertEquals(2, bill.getBooks().size());
        assertEquals("Book 1", bill.getBooks().get(0).getBookTitle());
        assertEquals("Book 2", bill.getBooks().get(1).getBookTitle());
        assertEquals(2, bill.getQuantity().get(0));
        assertEquals(3, bill.getQuantity().get(1));

        assertEquals(totalPrice, bill.getTotalPrice());
    }

    @Test
    void testBillCreationWithNoBooksIntegration() {
        ArrayList<Book> emptyBooks = new ArrayList<>();
        ArrayList<Integer> emptyQuantities = new ArrayList<>();

        Bill emptyBill = new Bill(102, emptyBooks, emptyQuantities, 0, BillsType.Purchased);

        assertEquals(0, emptyBill.getBooks().size());
        assertEquals(0, emptyBill.getQuantity().size());
        assertEquals(0, emptyBill.getTotalPrice());
    }

    @Test
    void testBookAndAuthorIntegration() {
        Book mockBook1 = new Book("ISBN1", "Book 1", new Author("John", "Doe", Gender.Male), new ArrayList<>(), "Supplier 1", 10, 20, 30, "Address 1", new Date());
        Book mockBook2 = new Book("ISBN2", "Book 2", new Author("Jane", "Smith", Gender.Female), new ArrayList<>(), "Supplier 2", 15, 25, 35, "Address 2", new Date());

        books.clear();
        books.add(mockBook1);
        books.add(mockBook2);

        quantities.clear();
        quantities.add(2);
        quantities.add(3);

        int totalPrice = 1500 * 2 + 1800 * 3;
        Bill billWithAuthors = new Bill(103, books, quantities, totalPrice, BillsType.Sold);

        assertEquals("John", billWithAuthors.getBooks().get(0).getAuthor().getName());
        assertEquals("Jane", billWithAuthors.getBooks().get(1).getAuthor().getName());
        assertEquals("Doe", billWithAuthors.getBooks().get(0).getAuthor().getSurname());
        assertEquals("Smith", billWithAuthors.getBooks().get(1).getAuthor().getSurname());
    }

    @Test
    void testBillNumberIncrementWithMultipleBills() {
        Bill firstBill = new Bill(104, books, quantities, 1500 * 2 + 1800 * 3, BillsType.Sold);
        Bill secondBill = new Bill(105, books, quantities, 2000 * 2 + 2500 * 3, BillsType.Purchased);

        assertEquals(1, firstBill.getBillNumber());
        assertEquals(2, secondBill.getBillNumber());
    }

    @Test
    void testBillTypeIntegration() {
        int totalPriceSold = (100 * 2) + (200 * 3);
        billSold = new Bill(101, books, quantities, totalPriceSold, BillsType.Sold);

        int totalPricePurchased = (50 * 2) + (100 * 3);
        billPurchased = new Bill(102, books, quantities, totalPricePurchased, BillsType.Purchased);

        int totalPriceBought = (50 * 2) + (100 * 3);
        billBought = new Bill(103, books, quantities, totalPriceBought, BillsType.Bought);

        assertEquals(totalPriceSold, billSold.getTotalPrice());
        assertEquals(totalPricePurchased, billPurchased.getTotalPrice());
        assertEquals(totalPriceBought, billBought.getTotalPrice());
    }
}
