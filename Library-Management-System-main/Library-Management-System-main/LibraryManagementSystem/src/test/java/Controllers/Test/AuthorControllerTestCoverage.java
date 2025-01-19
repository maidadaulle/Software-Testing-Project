package Controllers.Test;

import Controllers.AuthorController;
import Controllers.FileController;
import Models.Author;
import Models.Gender;
import Models.StandardViewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static Models.Gender.MALE;
import static Models.Gender.Male;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuthorControllerTestCoverage {

    @BeforeEach
    void setUp() {
        FileController.authors = new ArrayList<>();
    }

    private void addExistingAuthor(String name, String surname) {
        Author.setNoAuthor(1); // Ensure ID is properly set
        Author existingAuthor = new Author(name, surname, MALE);
        FileController.authors.add(existingAuthor); // Ensure author is added
    }

    @Test
    void testCreateAuthor_ValidInput() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Kadare";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals(gender, response.getUser().getGender());
        assertEquals("", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_EmptyName() {
        AuthorController authorController = new AuthorController();
        String name = "";
        String surname = "Kadare";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_EmptySurname() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameTooShort() {
        AuthorController authorController = new AuthorController();
        String name = "Is";
        String surname = "Kadare";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameTooLong() {
        AuthorController authorController = new AuthorController();
        String name = "Ismaillllllllllllllllllllll";
        String surname = "Kadare";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameContainsNumbers() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail123";
        String surname = "Kadare";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't contain numbers!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameContainsSpecialCharacters() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail@Kadare";
        String surname = "Ismail";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't contain special characters!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_SurnameTooShort() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Ka";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Surname cannot have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_SurnameTooLong() {
        FileController.authors = new ArrayList<>();
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Kadarererererererererreerre";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Surname cannot have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_SurnameContainsNumbers() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Kadare123";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Surname can't contain numbers!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_SurnameContainsSpecialCharacters() {
        FileController.authors = new ArrayList<>();
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Kadare@";
        Gender gender = MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Surname can't contain special characters!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_AuthorAlreadyExists() {
        AuthorController authorController = new AuthorController();
        String name = "George";
        String surname = "Martin";
        Gender gender = MALE;

        addExistingAuthor(name, surname);

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("There already exists an Author with this credentials", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_ExceptionHandling() {

        AuthorController authorController = Mockito.spy(new AuthorController());
        String name = "Ismail";
        String surname = "Kadare";
        Gender gender = MALE;

        doThrow(new RuntimeException("Unexpected error"))
                .when(authorController).addAuthor(any(Author.class));

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());  
        assertEquals("Unexpected error", response.getErrorMessage());
    }


    @Test
    void testFindAuthor_Existing() {
        AuthorController authorController = new AuthorController();
        Author author = new Author("Ismail", "Kadare", MALE);
        FileController.authors.add(author);

        Author foundAuthor = authorController.findAuthor(author.getID());
        assertNotNull(foundAuthor, "Failed to find existing author.");
        assertEquals(author, foundAuthor, "Returned author does not match.");
    }

    @Test
    void testFindAuthor_NonExistent() {
        AuthorController authorController = new AuthorController();
        Author foundAuthor = authorController.findAuthor(999);
        assertNull(foundAuthor, "Expected no author to be found for a non-existent ID.");
    }
    @Test
    void testEditAuthor_ValidInput() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "Rita", "Petro", Gender.Female);

        assertNotNull(response.getUser());
        assertEquals("Rita", response.getUser().getName());
        assertEquals("Petro", response.getUser().getSurname());
        assertEquals(Gender.Female, response.getUser().getGender());
        assertEquals("", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_EmptyName() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "", "Kadare", Gender.Male);

        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_EmptySurname() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "Ismail", "", Gender.Male);

        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_NameLengthInvalid() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");  // Set up the initial author with the name "Ismail" and surname "Kadare"

        StandardViewResponse<Author> response = authorController.editAuthor(1, "I", "Kadare", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Name can't have this length!", response.getErrorMessage());

        response = authorController.editAuthor(1, "Ismailllllllllllllllllllllllllllllll", "Kadare", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }



    @Test
    void testEditAuthor_NameWithNumbers() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "Ismail123", "Kadare", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Name can't contain numbers!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_NameWithSpecialCharacters() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "Ismail@", "Kadare", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Name can't contain special characters!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_SurnameLengthInvalid() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "Ismail", "K", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Surname cannot have this length!", response.getErrorMessage());

        response = authorController.editAuthor(1, "Ismail", "ThisSurnameIsWayTooLong", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Surname cannot have this length!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_SurnameWithNumbers() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");

        StandardViewResponse<Author> response = authorController.editAuthor(1, "Ismail", "Kadare123", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Surname can't contain numbers!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_SurnameWithSpecialCharacters() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");


        StandardViewResponse<Author> response = authorController.editAuthor(1, "Ismail", "Kadare@", Gender.Male);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("Surname can't contain special characters!", response.getErrorMessage());
    }

    @Test
    void testEditAuthor_AuthorAlreadyExists() {
        AuthorController authorController = new AuthorController();
        addExistingAuthor("Ismail", "Kadare");
        addExistingAuthor("Rita", "Petro");


        StandardViewResponse<Author> response = authorController.editAuthor(1, "Rita", "Petro", Gender.Female);
        assertNotNull(response.getUser());
        assertEquals("Ismail", response.getUser().getName());
        assertEquals("Kadare", response.getUser().getSurname());
        assertEquals("There already exists an Author with this credentials", response.getErrorMessage());
    }






}
