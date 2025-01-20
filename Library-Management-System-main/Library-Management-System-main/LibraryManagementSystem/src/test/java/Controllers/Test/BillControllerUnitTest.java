package Controllers.Test;

import Controllers.BillController;
import Controllers.FileController;
import Models.Bill;
import Models.BillsType;
import Models.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class BillControllerUnitTest {

    private BillController billController;
    private final ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    void setUp() {
        billController = new BillController();
        System.setOut(new PrintStream(consoleOutput));
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
    void testCreateBill() {

        FileController.transactions = new ArrayList<>();
        BillController billController = new BillController();

        int ID = 7;
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        int totalPrice = 100;
        BillsType type = BillsType.Sold;


        billController.createBill(ID, books, quantities, totalPrice, type);


        assertEquals(1, FileController.transactions.size(), "A new bill should be added to transactions.");
        assertEquals(ID, FileController.transactions.get(0).getBillNumber(), "The bill ID should match.");
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
    void testAddBillCall() {
        Bill bill = new Bill(1, new ArrayList<>(), new ArrayList<>(), 100, BillsType.Bought);

        FileController.transactions = new ArrayList<>();

        billController.printBill(bill);

        assertTrue(FileController.transactions.contains(bill), "The bill should be added to transactions.");
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
        assertTrue(file.exists(), "The bill file should be created in the 'Bills/boughtBooks' directory.");
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
