package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;

class BillTest {
    private Bill bill;
    private ArrayList<Book> books;
    private ArrayList<Integer> quantities;
    private BillsType billType;

    @BeforeEach
    void setUp() {
        Bill.setTotalBills(0);
        books = new ArrayList<>();
        quantities = new ArrayList<>();

        books.add(new Book("1234", "Book 1", new Author("Author1", "Surname1", Gender.MALE),
                new ArrayList<>(), "Supplier1", 100, 150, 200, "address1", new Date()));
        books.add(new Book("5678", "Book 2", new Author("Author2", "Surname2", Gender.Female),
                new ArrayList<>(), "Supplier2", 150, 200, 250, "address2", new Date()));

        quantities.add(2);
        quantities.add(3);

        billType = BillsType.Sold;
        bill = new Bill(1, books, quantities, 950, billType);
    }

    @Test
    void testBillConstructor() {
        Bill.setTotalBills(0); 

        int id = 1;
        Book book = new Book("12345", "Test Book", null, null, "Supplier", 10, 15, 20, "cover.jpg");
        int quantity = 3;
        int totalPrice = 60;
        BillsType type = BillsType.Sold;

        Bill bill = new Bill(id, book, quantity, totalPrice, type);

        assertEquals(1, Bill.getTotalBills(), "Total bills should be 1 after creating the first bill");
        assertNotNull(bill.getCreatedDate(), "Created date should not be null");
        assertEquals(id, bill.getSoldBy(), "SoldBy should match the provided id");
        assertEquals(1, bill.getBooks().size(), "Books list should contain one book");
        assertEquals(book, bill.getBooks().get(0), "The first book should match the input book");
        assertEquals(totalPrice, bill.getTotalPrice(), "Total price should match the provided totalPrice");
        assertEquals(1, bill.getBillNumber(), "Bill number should be 1 since it's the first bill");
        assertEquals(type, bill.getType(), "The bill type should match the provided type");

    }


    @Test
    void testGetCreatedDate() {
        Date createdDate = bill.getCreatedDate();
        assertNotNull(createdDate, "Created Date should not be null");
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(950, bill.getTotalPrice(), "Total price should match the expected value");
    }

    @Test
    void testSetTotalPrice() {
        bill.setTotalPrice(1000);
        assertEquals(1000, bill.getTotalPrice(), "Total price should be updated");
    }

    @Test
    void testGetTotalBills() {
        int initialTotalBills = Bill.getTotalBills();
        Bill newBill = new Bill(2, books, quantities, 1000, BillsType.Sold);
        assertEquals(initialTotalBills + 1, Bill.getTotalBills(), "Total bills should increment after creating a new bill");
    }

    @Test
    void testGetBillNumber() {
        assertEquals(1, bill.getBillNumber(), "Bill number should be 1 for the first bill");
    }

    @Test
    void testToString() {
        String expectedString = "Bill no.1\nDate:" + bill.getCreatedDate() + "\nEmployee ID:1\n" +
                "\n1234 ..... Book 1 ..... 200 ALL x 2 copies" +
                "\n5678 ..... Book 2 ..... 250 ALL x 3 copies" +
                "\n\nTotal Price 950 ALL.";
        assertTrue(bill.toString().contains("Bill no.1"), "ToString method should include bill number");
        assertTrue(bill.toString().contains("Book 1"), "ToString method should include book title");
        assertTrue(bill.toString().contains("Total Price 950 ALL."), "ToString method should include total price");
    }

    @Test
    void testSetSoldBy() {
        bill.setSoldBy(2);
        assertEquals(2, bill.getSoldBy(), "SoldBy should be updated to 2");
    }

    @Test
    void testSetBooks() {
        ArrayList<Book> newBooks = new ArrayList<>();
        Author author = new Author("Author", "Author3", Gender.MALE);

        newBooks.add(new Book("9999",
                "Book 3",
                author,
                new ArrayList<>(),
                "Supplier3",
                300, 400, 500,
                "address3",
                new Date()));

        bill.setBooks(newBooks);
        assertEquals(newBooks, bill.getBooks(), "Books should be updated correctly");
    }

    @Test
    void testSetQuantity() {
        ArrayList<Integer> newQuantities = new ArrayList<>();
        newQuantities.add(1);
        bill.setQuantity(newQuantities);
        assertEquals(newQuantities, bill.getQuantity(), "Quantities should be updated correctly");
    }

    @Test
    void testSetTotalBills() {
        Bill.setTotalBills(10);
        assertEquals(10, Bill.getTotalBills(), "Total bills should be set correctly");
    }

    @Test
    void testSetBooksEmpty() {
        ArrayList<Book> emptyBooks = new ArrayList<>();
        bill.setBooks(emptyBooks);
        assertEquals(emptyBooks, bill.getBooks(), "Books should be updated correctly with an empty list");
    }

    @Test
    void testGetType() {
        Bill soldBill = new Bill(1, books, quantities, 950, BillsType.Sold);
        assertEquals(BillsType.Sold, soldBill.getType(), "The type should be Sold");

        Bill purchasedBill = new Bill(2, books, quantities, 1000, BillsType.Purchased);
        assertEquals(BillsType.Purchased, purchasedBill.getType(), "The type should be Purchased");
    }
}
