package Models;


import Models.Author;
import Models.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {


        private Author author;

        @BeforeEach
        void setUp() {
            author = new Author("Ismail", "Kadare", Gender.Male);
        }

        @Test
        void testAuthorConstructor() {
            assertNotNull(author);
            assertEquals("Ismail", author.getName());
            assertEquals("Kadare", author.getSurname());
            assertEquals(Gender.Male, author.getGender());
        }

        @Test
        void testGetName() {
            assertEquals("Ismail", author.getName());
        }

        @Test
        void testSetName() {
            author.setName("Ahmed");
            assertEquals("Ahmed", author.getName());
        }

        @Test
        void testGetSurname() {
            assertEquals("Kadare", author.getSurname());
        }

        @Test
        void testSetSurname() {
            author.setSurname("Ali");
            assertEquals("Ali", author.getSurname());
        }

        @Test
        void testGetGender() {
            // Verify the getGender method
            assertEquals(Gender.Male, author.getGender());
        }

        @Test
        void testSetGender() {
            author.setGender(Gender.Female);
            assertEquals(Gender.Female, author.getGender());
        }

        @Test
        void testToString() {
            String expected = "Ismail Kadare";
            assertEquals(expected, author.toString());
        }

        @Test
        void testGetID() {
            int id = author.getID();
            assertTrue(id >= 0, "Author ID should be non-negative.");
        }

        @Test
        void testSetID() {
            author.setID(10);
            assertEquals(10, author.getID());
        }

        @Test
        void testStaticNoAuthor() {
            Author author2 = new Author("George", "Orwell", Gender.Male);
            assertTrue(author2.getID() > author.getID(), "The ID for the second author should be greater.");
        }

    @Test
    void testSetNoAuthor() {
        Author.setNoAuthor(5);
        Author author2 = new Author("George", "Orwell", Gender.Male);
        assertEquals(5, author2.getID(), "The ID of the second author should start from 5.");
    }
    }


