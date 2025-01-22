package views;

import Controllers.*;
import Models.Category;
import Views.EmployeeHomePage;
import Views.*;
import Models.StandardViewResponse;
import Models.User;
import Views.LogInView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

class AddCategoryViewTest extends ApplicationTest {

    private TextField categoryNameField;
    private Button registerButton;
    private CategoryController categoryControllerMock;

    @Override
    public void start(Stage stage) {
        AddCategoryView addCategoryView = new AddCategoryView(new User());
        Scene scene = addCategoryView.addCategory(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testCategoryCreationWithValidData() {
        categoryControllerMock = Mockito.mock(CategoryController.class);
        StandardViewResponse<Category> response = new StandardViewResponse<>(new Category("New Category"), "");
        when(categoryControllerMock.createCategory("New Category")).thenReturn(response);

        categoryNameField = lookup("#category-name-field").query();
        registerButton = lookup("#register-category-btn").query();

        // Input valid data
        categoryNameField.setText("New Category");

        clickOn(registerButton);

        assertTrue(response.getErrorMessage().isEmpty());
    }

    @Test
    public void testCategoryCreationWithInvalidData() {
        categoryControllerMock = Mockito.mock(CategoryController.class);
        StandardViewResponse<Category> response = new StandardViewResponse<>(null, "Category name can't contain numbers!");
        when(categoryControllerMock.createCategory("Invalid123")).thenReturn(response);

        categoryNameField = lookup("#category-name-field").query();
        registerButton = lookup("#register-category-btn").query();

        categoryNameField.setText("Invalid123");

        clickOn(registerButton);

        assertEquals("Category name can't contain numbers!", response.getErrorMessage());
    }
}