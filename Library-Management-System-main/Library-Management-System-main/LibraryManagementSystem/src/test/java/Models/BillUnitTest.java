package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
class BillUnitTest {
    private Bill bill;
    private ArrayList<Book> books;
    private ArrayList<Integer> quantities;
    private BillsType billType;

    @BeforeEach
    void setUp() {
        // Reset totalBills to 0 before each test to ensure bill number starts at 1
        Bill.setTotalBills(0);

        // Prepare some mock data for the test
        books = new ArrayList<>();
        quantities = new ArrayList<>();

        // Add books with proper Author and Category parameters
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
        // Create an Author instance with all required parameters: name, surname, gender
        Author author = new Author("Author", "Author3", Gender.MALE);  // Adjust Gender as needed

        // Create a new Book instance and pass the author along with other required parameters
        newBooks.add(new Book("9999",
                "Book 3",
                author,  // Pass the Author instance
                new ArrayList<Category>(),  // Empty list for categories
                "Supplier3",
                300, 400, 500,  // Prices: purchasedPrice, originalPrice, sellingPrice
                "address3",
                new Date()));  // Purchased date (current date)

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

    /*@Test
    void testToStringForPurchasedBill() {
        // Setup test data
        ArrayList<Book> booksForTest = new ArrayList<>();
        booksForTest.add(new Book("1234", "Book 1", new Author("Author1", "Surname1", Gender.MALE), new ArrayList<>(), "Supplier1", 100, 150, 200, "address1", new Date()));
        booksForTest.add(new Book("5678", "Book 2", new Author("Author2", "Surname2", Gender.Female), new ArrayList<>(), "Supplier2", 150, 200, 250, "address2", new Date()));

        ArrayList<Integer> quantitiesForTest = new ArrayList<>();
        quantitiesForTest.add(1);
        quantitiesForTest.add(2);

        // Create the Bill (Purchased type)
        Bill purchasedBill = new Bill(1, booksForTest, quantitiesForTest, 1000, BillsType.Purchased);

        // Get the actual output of toString()
        String actualString = purchasedBill.toString();

        // Expected string without the dynamic Date (replaced with 'xxx')
        String expectedString = "Bill no.1\nDate:xxx\nEmployee ID:1\n" +
                "\n1234 ..... Book 1 ..... 150 ALL x 1 copies" +
                "\n5678 ..... Book 2 ..... 200 ALL x 2 copies" +
                "\n\nTotal Price 1000 ALL.";

        // Remove the dynamic Date part from both actual and expected strings
        String actualStringWithoutDate = actualString.replaceAll("Date:.*\n", "Date:xxx\n");
        String expectedStringWithoutDate = expectedString.replaceAll("Date:.*\n", "Date:xxx\n");

        // Assert that the two strings are now equal
        assertEquals(expectedStringWithoutDate, actualStringWithoutDate, "toString() should match the expected format for a purchased bill");

        // Optionally, check that the total price is included correctly in the actual string
        assertTrue(actualString.contains("Total Price 1000 ALL."), "toString() should include the correct total price");
    }*/









    @Test
    void testSetBooksEmpty() {
        ArrayList<Book> emptyBooks = new ArrayList<>();
        bill.setBooks(emptyBooks);
        assertEquals(emptyBooks, bill.getBooks(), "Books should be updated correctly with an empty list");
    }


    @Test
    void testGetType() {
        // Create a bill of type Sold
        Bill soldBill = new Bill(1, books, quantities, 950, BillsType.Sold);
        assertEquals(BillsType.Sold, soldBill.getType(), "The type should be Sold");

        // Create a bill of type Purchased
        Bill purchasedBill = new Bill(2, books, quantities, 1000, BillsType.Purchased);
        assertEquals(BillsType.Purchased, purchasedBill.getType(), "The type should be Purchased");
    }



}
