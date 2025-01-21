package Controllers.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controllers.CategoryController;
import Controllers.FileController;
import Models.Category;
import Models.StandardViewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    static class MockFileController {
        static ArrayList<Category> categories = new ArrayList<>();
    }

    private CategoryController controller;

    @BeforeEach
    void setUp() {
        // Set up the mock environment
        File dir = new File("binaryFiles");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File("binaryFiles/users.dat");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller = new CategoryController();
        MockFileController.categories.clear();
    }

    // Unit test
    @Test
    void testCreateCategoryValid() {
        StandardViewResponse<Category> response = controller.createCategory("Technology");
        assertNotNull(response.getUser());
        assertTrue(response.getErrorMessage().isEmpty());
        assertEquals("Technology", response.getUser().getCategoryName());
    }

    // Unit test
    @Test
    void testCreateCategoryEmptyInput() {
        StandardViewResponse<Category> response = controller.createCategory("");
        assertNull(response.getUser());
        assertEquals("Field is empty!", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testCreateCategoryShortName() {
        StandardViewResponse<Category> response = controller.createCategory("IT");
        assertNull(response.getUser());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testCreateCategorySpecialCharacters() {
        StandardViewResponse<Category> response = controller.createCategory("Tech@");
        assertNull(response.getUser());
        assertEquals("Name can't contain special characters!", response.getErrorMessage());
    }

    //Unit Testing
    @Test
    void testCreateCategoryNumbers() {
        StandardViewResponse<Category> response = controller.createCategory("Tech1234");
        assertNull(response.getUser());
        assertEquals("Name can't contain numbers!", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testCreateCategoryDuplicateName() {
        MockFileController.categories.add(new Category("Technology"));
        StandardViewResponse<Category> response = controller.createCategory("Technology");
        assertNull(response.getUser());
        assertEquals("There already exists a category with this name", response.getErrorMessage());
    }

    // Integration test
    @Test
    void testIntegrationAddCategory() {
        FileController.categories.clear();
        Category newCategory = new Category("Science");
        controller.addCategory(newCategory);

        assertEquals(1, FileController.categories.size(), "The category size should be 1 after addition.");
        assertEquals("Science", FileController.categories.get(0).getCategoryName(), "The category name should be 'Science'.");
    }

    // Integration test
    @Test
    void testEditCategoryValid() {
        Category category = new Category("Education");
        FileController.categories.add(category);
        assertNotNull(FileController.categories.get(0), "The category should not be null before editing");

        StandardViewResponse<Category> response = controller.editCategory(category.getID(), "Learning");

        assertNotNull(response, "The response should not be null");
        assertNotNull(response.getUser(), "The response user should not be null");
        assertEquals("Learning", response.getUser().getCategoryName(), "The category name should be updated to 'Learning'");
        assertTrue(response.getErrorMessage().isEmpty(), "The error message should be empty");
    }

    // Integration test
    @Test
    void testEditCategoryEmptyName() {
        FileController.categories = new ArrayList<>();
        Category category = new Category("Old Category");
        FileController.categories.add(category);

        StandardViewResponse<Category> response = controller.editCategory(category.getID(), "");

        assertNotNull(response.getUser(), "The response category should not be null");
        assertEquals("Fields are empty!", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testEditCategoryNonUniqueName() {
        Category category1 = new Category("Education");
        FileController.categories.add(category1);

        Category category2 = new Category("Science");
        FileController.categories.add(category2);

        StandardViewResponse<Category> response = controller.editCategory(category2.getID(), "Education");

         assertNotNull(response.getUser(), "The response category should not be null");
        assertEquals("There already exists a category with this name", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testEditCategorySpecialCharsInName() {
        Category category = new Category("Education");
        FileController.categories.add(category);
        StandardViewResponse<Category> response = controller.editCategory(category.getID(), "Edu@cation");
        assertNotNull(response.getUser());
        assertEquals("Name can't contain special characters!", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testEditCategoryNumbersInName() {
        Category category = new Category("Education");
        FileController.categories.add(category);
        StandardViewResponse<Category> response = controller.editCategory(category.getID(), "Education124");
        assertNotNull(response.getUser());
        assertEquals("Name can't contain numbers!", response.getErrorMessage());
    }

    // Unit test
    @Test
    void testEditCategoryInvalidLength() {
        Category category = new Category("Education");
        FileController.categories.add(category);
        StandardViewResponse<Category> response = controller.editCategory(category.getID(), "Eduuuuuuuuuuuuuuuuuuuuuuuuuuuucation");
        assertNotNull(response.getUser());
        assertEquals("Name can't have this length!", response.getErrorMessage());
    }

    // Integration test
    @Test
    void testIntegrationFindCategory() {
        FileController.categories = new ArrayList<>();
        Category fictionCategory = new Category("Fiction");
        FileController.categories.add(fictionCategory);
        FileController.categories.add(new Category("Non-Fiction"));
        CategoryController controller = new CategoryController();
        Category result = controller.findCategory(fictionCategory.getID());

        assertNotNull(result, "The category should not be null");
        assertEquals("Fiction", result.getCategoryName(), "The category name should match");
    }

    //Integration Test
    @Test
    void testFindCategoryReturnsNullWhenNotFound() {
        if (FileController.categories == null) {
            FileController.categories = new ArrayList<>();
        }
        int nonExistentId = 999;
        Category result = controller.findCategory(nonExistentId);
        assertNull(result, "The category should be null since the ID does not exist in the list");
    }


}
