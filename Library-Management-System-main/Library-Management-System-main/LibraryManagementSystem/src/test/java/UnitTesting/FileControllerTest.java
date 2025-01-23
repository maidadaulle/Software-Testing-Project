package UnitTesting;


import Controllers.FileController;
import Models.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileControllerTest {

    FileController fileController;
    private final String testUsersFilePath = "test_users.dat";
    private final String testCategoriesFilePath = "test_categories.dat";
    private final String testBooksFilePath = "test_books.dat";
    private final String testTransactionsFilePath = "test_transactions.dat";
    private final String testAuthorsFilePath = "test_authors.dat";

    @BeforeEach
    void setup() throws Exception {
        fileController = new FileController();
        // Set up test file paths
        FileController.usersFile = new File(testUsersFilePath);
        FileController.categoriesFile = new File(testCategoriesFilePath);
        FileController.booksFile = new File(testBooksFilePath);
        FileController.transactionsFile = new File(testTransactionsFilePath);
        FileController.authorsFile = new File(testAuthorsFilePath);

        FileController.users = new ArrayList<>();
        FileController.categories = new ArrayList<>();
        FileController.books = new ArrayList<>();
        FileController.transactions = new ArrayList<>();
        FileController.authors = new ArrayList<>();
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up test files
        Files.deleteIfExists(Path.of(testUsersFilePath));
        Files.deleteIfExists(Path.of(testCategoriesFilePath));
        Files.deleteIfExists(Path.of(testBooksFilePath));
        Files.deleteIfExists(Path.of(testTransactionsFilePath));
        Files.deleteIfExists(Path.of(testAuthorsFilePath));
    }

    @Test
    void testReadUsers_readsSuccessfully() throws Exception {
        FileController.users.add(new Admin("admin", "admin"));
        FileController.writeUsers();

        FileController.users.clear();
        fileController.readUsers();

        assertEquals(1, FileController.users.size());
        assertEquals("admin", FileController.users.get(0).getUsername());
    }

    @Test
    void testWriteUsers_writesSuccessfully() throws Exception {
        FileController.users.add(new Admin("admin", "admin"));
        FileController.writeUsers();

        assertTrue(Files.exists(Path.of(testUsersFilePath)));
    }

    @Test
    void testReadCategories_readsSuccessfully() throws Exception {
        FileController.categories.add(new Category("Fiction"));
        FileController.writeCategories();

        FileController.categories.clear();
        fileController.readCategories();

        assertEquals(1, FileController.categories.size());
        assertEquals("Fiction", FileController.categories.get(0).getCategoryName());
    }

    @Test
    void testWriteCategories_writesSuccessfully() throws Exception {
        FileController.categories.add(new Category("Fiction"));
        FileController.writeCategories();

        assertTrue(Files.exists(Path.of(testCategoriesFilePath)));
    }

    @Test
    void testReadBooks_readsSuccessfully() throws Exception {
        FileController.books.add(new Book("12345", "Test Book", "Test Author", 1, 10));
        FileController.writeBooks();

        FileController.books.clear();
        fileController.readBooks();

        assertEquals(1, FileController.books.size());
        assertEquals("Test Book", FileController.books.get(0).getBookTitle());
    }

    @Test
    void testWriteBooks_writesSuccessfully() throws Exception {
        FileController.books.add(new Book("Test Book", "12345", "Test Author", 9, 10));
        FileController.writeBooks();

        assertTrue(Files.exists(Path.of(testBooksFilePath)));
    }

    @Test
    void testReadTransactions_readsSuccessfully() throws Exception {
        FileController.transactions.add(new Bill(1,new Book("ISBN", "bookTitle","authorName",20, 30), 1,  100,BillsType.Bought)
   );
        FileController.writeTransactions();

        FileController.transactions.clear();
        FileController.readTransactions();

        assertEquals(1, FileController.transactions.size());
        assertEquals(BillsType.Bought, FileController.transactions.get(0).getType());
    }

    @Test
    void testWriteTransactions_writesSuccessfully() throws Exception {
        FileController.transactions.add(new Bill(1,new Book("ISBN", "bookTitle","authorName",20, 30), 1,  100,BillsType.Bought)
   );
        FileController.writeTransactions();

        assertTrue(Files.exists(Path.of(testTransactionsFilePath)));
    }

    @Test
    void testReadAuthors_readsSuccessfully() throws Exception {
        FileController.authors.add(new Author("Test Author", "Biography",Gender.Male));
        FileController.writeAuthors();

        FileController.authors.clear();
        fileController.readAuthors();

        assertEquals(1, FileController.authors.size());
        assertEquals("Test Author", FileController.authors.get(0).getName());
    }

    @Test
    void testWriteAuthors_writesSuccessfully() throws Exception {
        FileController.authors.add(new Author("Test Author", "Biography", Gender.Male));
        FileController.writeAuthors();

        assertTrue(Files.exists(Path.of(testAuthorsFilePath)));
    }

    @Test
    void testAddUser_addsUserSuccessfully() {
        User user = new Admin("testUser", "testPass");
        FileController.users.add(user);

        assertEquals(1, FileController.users.size());
        assertEquals("testUser", FileController.users.get(0).getUsername());
    }


}