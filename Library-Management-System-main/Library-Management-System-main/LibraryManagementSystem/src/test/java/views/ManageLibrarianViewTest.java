package views;
import Controllers.LibrarianController;
import Models.Librarian;
import Models.Roles;
import Models.User;
import Views.AdminHomePage;
import Views.EmployeeHomePage;
import Views.ManageLibrarianView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

public class ManageLibrarianViewTest extends ApplicationTest {
    private ManageLibrarianView manageLibrarianView;
    private User adminUser;
    private User employeeUser;
    private Librarian librarian;
    private Stage stage;
    @BeforeEach
    public void setUp() {
        adminUser = mock(User.class);
        employeeUser = mock(User.class);
        librarian = mock(Librarian.class);
        stage = mock(Stage.class);
        when(adminUser.getUserRole()).thenReturn(Roles.Admin);
        when(librarian.getName()).thenReturn("John");
        when(employeeUser.getUserRole()).thenReturn(Roles.Librarian);
        manageLibrarianView = new ManageLibrarianView(adminUser);
    }
    @Test
    public void testSceneLoadsForAdmin() {
        Scene scene = manageLibrarianView.showManageLibrarianView(librarian, stage);
        BorderPane borderPane = (BorderPane) scene.getRoot();
        StackPane topStackPane = (StackPane) borderPane.getTop();
        Text text = (Text) topStackPane.getChildren().get(0);
        assertNotNull(text);
        assertEquals("Librarian John", text.getText());
        GridPane gridPane = (GridPane) borderPane.getCenter();
        assertEquals(4, gridPane.getChildren().size());
        Button editButton = (Button) gridPane.getChildren().get(0);
        Button deleteButton = (Button) gridPane.getChildren().get(2);
        Button performanceButton = (Button) gridPane.getChildren().get(1);
        Button backButton = (Button) gridPane.getChildren().get(3);
        assertNotNull(editButton);
        assertNotNull(deleteButton);
        assertNotNull(performanceButton);
        assertNotNull(backButton);
    }
    @Test
    public void testSceneLoadsForEmployee() {
        manageLibrarianView = new ManageLibrarianView(employeeUser);
        Scene scene = manageLibrarianView.showManageLibrarianView(librarian, stage);
        BorderPane borderPane = (BorderPane) scene.getRoot();
        StackPane topStackPane = (StackPane) borderPane.getTop();
        Text text = (Text) topStackPane.getChildren().get(0);
        assertNotNull(text);
        assertEquals("Librarian John", text.getText());
        GridPane gridPane = (GridPane) borderPane.getCenter();
        assertEquals(2, gridPane.getChildren().size());
        Button performanceButton = (Button) gridPane.getChildren().get(0);
        Button backButton = (Button) gridPane.getChildren().get(1);
        assertNotNull(performanceButton);
        assertNotNull(backButton);
        boolean hasDeleteButton = gridPane.getChildren().stream()
                .anyMatch(node -> node instanceof Button && ((Button) node).getText().equals("Delete Librarian"));
        assertFalse(hasDeleteButton, "Employee should not have the 'Delete Librarian' button.");
    }
    @Test
    public void testDeleteButtonAction() {
        LibrarianController librarianController = mock(LibrarianController.class);
        when(librarianController.deleteUserById(anyInt())).thenReturn(true);
        ManageLibrarianView manageLibrarianView = new ManageLibrarianView(adminUser);
        Scene scene = manageLibrarianView.showManageLibrarianView(librarian, stage);
        Button deleteButton = (Button) ((GridPane) scene.lookup(".grid-pane")).getChildren().get(2);
        deleteButton.fire();
        verify(librarianController, times(1)).deleteUserById(librarian.getId());
        AdminHomePage mockAdminHomePage = mock(AdminHomePage.class);
        when(mockAdminHomePage.manageLibrariansView(stage)).thenReturn(mock(Scene.class));
        verify(mockAdminHomePage, times(1)).manageLibrariansView(stage);
    }
    @Test
    public void testBackButtonActionForAdmin() {
        Scene scene = manageLibrarianView.showManageLibrarianView(librarian, stage);
        Button backButton = (Button) ((GridPane) scene.lookup(".grid-pane")).getChildren().get(3);
        backButton.fire();
        AdminHomePage adminHomePage = mock(AdminHomePage.class);
        when(adminHomePage.manageLibrariansView(stage)).thenReturn(new Scene(new BorderPane()));
        assertNotNull(adminHomePage.manageLibrariansView(stage));
    }
    @Test
    public void testBackButtonActionForEmployee() {
        manageLibrarianView = new ManageLibrarianView(employeeUser);
        Scene scene = manageLibrarianView.showManageLibrarianView(librarian, stage);
        Button backButton = (Button) ((GridPane) scene.lookup(".grid-pane")).getChildren().get(1);
        backButton.fire();
        EmployeeHomePage employeeHomePage = mock(EmployeeHomePage.class);
        when(employeeHomePage.showView(stage)).thenReturn(new Scene(new BorderPane()));
        assertNotNull(employeeHomePage.showView(stage));
    }
}
