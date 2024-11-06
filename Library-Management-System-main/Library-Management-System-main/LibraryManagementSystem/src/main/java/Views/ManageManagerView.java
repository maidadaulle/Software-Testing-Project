package Views;

import Controllers.ManagerController;
import Models.Manager;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ManageManagerView {

    private User currentUser;

    public ManageManagerView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showManageManagerView(Manager manager, Stage stage) {

        BorderPane border = new BorderPane();

        Text text = new Text("Manager " + manager.getName());
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Button editManagerButton = new Button("Edit Manager");
        Button performanceButton = new Button("Performance");
        Button deleteManagerButton = new Button("Delete Manager");
        Button backButton = new Button("Back");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(editManagerButton, 0, 0);
        grid.add(performanceButton, 1, 0);
        grid.add(deleteManagerButton, 2, 0);
        grid.add(backButton, 3, 0);
        border.setCenter(grid);
        editManagerButton.setOnAction(e -> {
            EditManagerView managerView = new EditManagerView(currentUser);
            stage.setScene(managerView.showEditManagerView(manager, stage));
        });

        deleteManagerButton.setOnAction(e -> {
            ManagerController managerController = new ManagerController();
            var deleted = managerController.deleteUserById(manager.getId());
            if (deleted) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Manager was Successfully Deleted!");
                error.showAndWait();
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.manageManagersView(stage));
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("An error happened while trying to Delete Manager!");
                error.showAndWait();
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.manageManagersView(stage));
            }

        });
        backButton.setOnAction(e -> {
            AdminHomePage adminHomePage = new AdminHomePage(currentUser);
            stage.setScene(adminHomePage.manageManagersView(stage));
        });
        performanceButton.setOnAction(e -> {
            ManagerPerformanceView managerPerformanceView = new ManagerPerformanceView(currentUser);
            stage.setScene(managerPerformanceView.showManagerPerformanceView(stage, manager));
        });

        return new Scene(border, 700, 500);
    }

}
