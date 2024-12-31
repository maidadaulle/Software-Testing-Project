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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AuthorControllerTestCoverage {

    @BeforeEach
    void setUp() {
        FileController.authors = new ArrayList<>();
    }

    private void addExistingAuthor(String name, String surname) {
        Author.setNoAuthor(1);
        Author existingAuthor = new Author(name, surname, Gender.MALE);
        FileController.authors.add(existingAuthor);
    }

    @Test
    void testCreateAuthor_ValidInput() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Kadare";
        Gender gender = Gender.MALE;

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
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_EmptySurname() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "";
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameTooShort() {
        AuthorController authorController = new AuthorController();
        String name = "Is";
        String surname = "Kadare";
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameTooLong() {
        AuthorController authorController = new AuthorController();
        String name = "Ismaillllllllllllllllllllll";
        String surname = "Kadare";
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameContainsNumbers() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail123";
        String surname = "Kadare";
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't contain numbers!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_NameContainsSpecialCharacters() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail@Kadare";
        String surname = "Ismail";
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Name can't contain special characters!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_SurnameTooShort() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Ka";
        Gender gender = Gender.MALE;

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
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Surname cannot have this length!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_SurnameContainsNumbers() {
        AuthorController authorController = new AuthorController();
        String name = "Ismail";
        String surname = "Kadare123";
        Gender gender = Gender.MALE;

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
        Gender gender = Gender.MALE;

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());
        assertEquals("Surname can't contain special characters!", response.getErrorMessage());
    }

    @Test
    void testCreateAuthor_AuthorAlreadyExists() {
        AuthorController authorController = new AuthorController();
        String name = "George";
        String surname = "Martin";
        Gender gender = Gender.MALE;

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
        Gender gender = Gender.MALE;

        doThrow(new RuntimeException("Unexpected error"))
                .when(authorController).addAuthor(any(Author.class));

        StandardViewResponse<Author> response = authorController.createAuthor(name, surname, gender);

        assertNull(response.getUser());  
        assertEquals("Unexpected error", response.getErrorMessage());
    }



}
