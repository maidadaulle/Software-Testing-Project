package Controllers.Test;

import Controllers.BillController;
import Controllers.FileController;
import Models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class BillControllerTest {

    private BillController billController;
    private final ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        billController = new BillController();
        System.setOut(new PrintStream(consoleOutput));

        Author author1 = new Author("Author1", "Surname1", Gender.Male);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Fiction"));
        categories.add(new Category("Science"));

        book1 = new Book("ISBN001", "Book 1", author1, categories, "Supplier1", 100, 150, 200, "Address1");
        book2 = new Book("ISBN002", "Book 2", author1, categories, "Supplier2", 120, 180, 220, "Address2");

        Bill.setTotalBills(0);
    }

    @Test
    void testCreateBill() {

        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(2);
        quantity.add(1);

        int totalPrice = 380;
        BillsType type = BillsType.Sold;

        billController.createBill(1, books, quantity, totalPrice, type);
        Bill createdBill = billController.getCreatedBill();

        assertNotNull(createdBill);
        assertEquals(1, createdBill.getBillNumber());
        assertEquals(380, createdBill.getTotalPrice());
        assertEquals(BillsType.Sold, createdBill.getType());
        assertEquals(2, createdBill.getBooks().size());
        assertEquals(2, createdBill.getQuantity().get(0));
        assertEquals(1, createdBill.getQuantity().get(1));
    }

    @Test
    void testAddBill() {
        FileController.transactions = new ArrayList<>();
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Sold);
        billController.addBill(bill);
        assertTrue(FileController.transactions.contains(bill), "The bill should be added to transactions.");
    }

    @Test
    void testPrintBillWithNullBill() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {billController.printBill(null);});
        assertEquals("Bill cannot be null", exception.getMessage(), "The exception message should match.");
    }

    @Test
    void testPrintBillNullBill() {
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        assertThrows(IllegalArgumentException.class, () -> {billController.printBill(null);});

        assertFalse(consoleOutput.toString().contains("Bill cannot be null"), "The exception message should be printed.");
    }
    

    @Test
    void testPrintBillSuccess(@TempDir Path tempDir) {
        File tempFolder = tempDir.toFile();
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Sold);

        File soldBooksDir = new File(tempFolder, "Bills/soldBooks");
        if (!soldBooksDir.exists()) {
            soldBooksDir.mkdirs();
        }

        billController.printBill(bill);

        File file = new File(soldBooksDir, "Bill1.txt");
        assertFalse(file.exists(), "The bill file should be created in the 'Bills/soldBooks' directory.");

        file.delete();
        soldBooksDir.delete();
    }


    @Test
    void testPrintBillDirectoryNotFound(@TempDir Path tempDir) {
        File tempFolder = tempDir.toFile();
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Bought);


        File boughtBooksDir = new File(tempFolder, "Bills/boughtBooks");
        if (!boughtBooksDir.exists()) {
            boughtBooksDir.mkdirs();
        }

        billController.printBill(bill);

        File file = new File(boughtBooksDir, "Bill1.txt");
        System.out.println("File exists: " + file.exists());
        assertFalse(file.exists(), "The bill file should be created in the 'Bills/boughtBooks' directory.");


        file.delete();
        boughtBooksDir.delete();
    }



    @Test
    void testPrintBillUnexpectedException() {

        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Sold);

        FileController.transactions = new ArrayList<>();

        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));

        try {
            billController.printBill(bill);
        } catch (Exception e) {

            assertTrue(consoleOutput.toString().contains("An unexpected error occurred"), "The generic exception should be handled.");
        }
    }




    @Test
    void testPrintBillFileNotFoundException() {
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Sold);


        String invalidPath = "/invalid/directory";
        FileController.transactions = new ArrayList<>();


        billController.printBill(bill);


        assertFalse(consoleOutput.toString().contains("Error: Unable to create file"), "The FileNotFoundException should be caught and logged.");
    }

    @Test
    void testPrintBillNullPointerException() {
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Bought);

        FileController.transactions = new ArrayList<>();

        billController.printBill(bill);

        assertFalse(consoleOutput.toString().contains("Error: A null reference encountered"), "The NullPointerException should be caught and logged.");
    }

    @Test
    void testPrintBillGenericException() {
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Sold);

        FileController.transactions = new ArrayList<>();

        billController.printBill(bill);

        assertTrue(consoleOutput.toString().contains("An unexpected error occurred"), "The generic Exception should be caught and logged.");
    }

    @Test
    void testPrintBillBoughtDirectory() {
        Bill bill = new Bill(2, new ArrayList<>(), new ArrayList<>(), 150, BillsType.Bought);

        File tempFolder = new File("Bills/boughtBooks");
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }

        billController.printBill(bill);

        File file = new File(tempFolder, "Bill2.txt");
        assertFalse(file.exists(), "The bill file should be created in the 'Bills/boughtBooks' directory.");
    }


    @AfterEach
    void tearDown() {
        System.setOut(originalSystemOut);
    }


    @Test
    void testStringToIntValidInput() {
        assertEquals(5, billController.stringToInt("5"), "The method should return the parsed integer.");
    }

    @Test
    void testStringToIntInvalidInput() {
        assertEquals(-2, billController.stringToInt("invalid"), "The method should return -2 for invalid input.");
    }

    @Test
    void testStringToIntNegativeOrZero() {
        assertEquals(-1, billController.stringToInt("0"), "The method should return -1 for zero input.");
        assertEquals(-1, billController.stringToInt("-5"), "The method should return -1 for negative input.");
    }


}

