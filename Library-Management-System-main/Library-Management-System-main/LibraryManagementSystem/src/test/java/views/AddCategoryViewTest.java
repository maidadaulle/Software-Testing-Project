package views;

import Controllers.*;
import Models.*;
import Views.AddCategoryView;
import Views.EmployeeHomePage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import static org.mockito.Mockito.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class AddCategoryViewTest extends ApplicationTest {


    private Button addCategoryButton;
    private TextField categoryNameField;
    private Label errorMessageLabel;
    private Stage stage;
    private CategoryController mockCategoryController;
    private User mockUser;

    public void waitUntil(BooleanSupplier condition) {
        long timeout = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < timeout) {
            if (condition.getAsBoolean()) {
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new AssertionError("Condition not met within timeout");
    }

    @BeforeEach
    public void setUp() {
        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("rexhens");
        sleep(1000);
        when(mockUser.getPassword()).thenReturn("Rexhens1@");
        sleep(1000);
        when(mockUser.getUserRole()).thenReturn(Roles.Manager);

        mockCategoryController = mock(CategoryController.class);

        Platform.runLater(() -> {
            AddCategoryView addCategoryView = new AddCategoryView(mockUser);
            stage = new Stage();
            Scene scene = addCategoryView.addCategory(stage);
            stage.setScene(scene);
            stage.show();
        });

        waitUntil(() -> stage != null && stage.isShowing());
    }

    @Test
    public void testCategoryCreationSuccess() {
        StandardViewResponse<Category> successResponse = new StandardViewResponse<>(new Category("Valid Category"), null);
        when(mockCategoryController.createCategory("Valid Category")).thenReturn(successResponse);

        FileController.categories = new ArrayList<>();

        waitUntil(() -> stage != null && stage.isShowing());
        sleep(1000);

        categoryNameField = (TextField) stage.getScene().lookup("#category-name-field");
        categoryNameField.setText("Valid Category");

        Button registerButton = (Button) stage.getScene().lookup("#register-category-btn");
        clickOn(registerButton);
        sleep(1000);

        Platform.runLater(() -> {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Category was successfully added!");
            successAlert.showAndWait();

            Button okButton = (Button) successAlert.getDialogPane().lookupButton(ButtonType.OK);
            clickOn(okButton);
        });

        waitUntil(() -> {
            Label label = (Label) stage.getScene().lookup("#error-message-label");
            return label != null && label.getText().equals("Category was successfully added!");
        });

        Label label = (Label) stage.getScene().lookup("#error-message-label");
        assertNotNull(label, "Error message label should be present.");
        assertEquals("Category was successfully added!", label.getText(), "Success message text should be correct.");
    }

    @Test
    public void testBackButtonTransition() {
        Button backButton = lookup("#back-to-homepage-btn").query();
        clickOn(backButton);
        sleep(1000);

        Platform.runLater(() -> {
            assertTrue(stage.getScene().getRoot() instanceof BorderPane,
                    "The scene should have transitioned to EmployeeHomePage.");
        });

        sleep(1000);
    }

    @Test
    public void testAddCategoryInvalidInput() {
        categoryNameField = lookup("#category-name-field").query();
        sleep(1000);
        errorMessageLabel = lookup("#error-message-label").query();
        sleep(1000);

        String invalidCategoryName = "F@ction";
        sleep(1000);
        categoryNameField.setText(invalidCategoryName);
        sleep(1000);

        Button registerButton = lookup("#register-category-btn").query();
        sleep(1000);
        clickOn(registerButton);
        sleep(1000);

        assertNotEquals("", errorMessageLabel.getText());
        assertTrue(errorMessageLabel.getText().contains("Name can't contain special characters"));
    }

    @Test
    public void testCategoryAlreadyExists() {
        waitUntil(() -> stage != null && stage.isShowing());
        sleep(1000);
        TextField categoryNameField = lookup("#category-name-field").query();
        sleep(1000);
        assertNotNull(categoryNameField);
        sleep(1000);

        categoryNameField.setText("Existing Category");
        sleep(1000);
    }
}
