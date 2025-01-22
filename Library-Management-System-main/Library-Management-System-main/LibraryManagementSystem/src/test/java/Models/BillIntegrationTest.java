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

    private BillsType billType;

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

        Bill.setTotalBills(0);

        books = new ArrayList<>();
        quantities = new ArrayList<>();

        books.add(new Book("1234",
                "Book 1",
                new Author("Author1", "Surname1", Gender.MALE),
                new ArrayList<>(),
                "Supplier1",
                100, 150, 200,
                "address1",
                new Date()));

        books.add(new Book("5678",
                "Book 2",
                new Author("Author2", "Surname2", Gender.Female),
                new ArrayList<>(),
                "Supplier2",
                150, 200, 250,
                "address2",
                new Date()));

        quantities.add(2);
        quantities.add(3);

        billType = BillsType.Sold;
        bill = new Bill(1, books, quantities, 950, billType);
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
