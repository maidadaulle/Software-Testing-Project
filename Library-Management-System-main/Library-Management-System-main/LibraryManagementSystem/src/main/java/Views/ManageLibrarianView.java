package Views;

import Controllers.LibrarianController;
import Models.Librarian;
import Models.Roles;
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

public class ManageLibrarianView {

    private User currentUser;

    public ManageLibrarianView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showManageLibrarianView(Librarian librarian, Stage stage) {

        BorderPane border = new BorderPane();

        Text text = new Text("Librarian " + librarian.getName());
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));
        border.setTop(stack);

        Button editManagerButton = new Button("Edit Librarian");
        Button performanceButton = new Button("Performance");
        Button deleteManagerButton = new Button("Delete Librarian");
        Button backButton = new Button("Back");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        if(currentUser.getUserRole()==Roles.Admin){
            grid.add(editManagerButton, 0, 0);
            grid.add(deleteManagerButton, 2, 0);
            
        }
        grid.add(performanceButton, 1, 0);
        grid.add(backButton, 3, 0);
        border.setCenter(grid);
        editManagerButton.setOnAction(e -> {
            EditLibrarianView managerView = new EditLibrarianView(currentUser);
            stage.setScene(managerView.editLibrarian(stage, librarian));
        });

        deleteManagerButton.setOnAction(e -> {
            LibrarianController librarianController = new LibrarianController();
            var deleted = librarianController.deleteUserById(librarian.getId());
            if (deleted) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Librarian was Successfully Deleted!");
                error.showAndWait();
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.manageLibrariansView(stage));
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("An error happened while trying to Delete librarian!");
                error.showAndWait();
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.manageLibrariansView(stage));
            }

        });
        if(currentUser.getUserRole()==Roles.Admin){
            backButton.setOnAction(e -> {
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.manageLibrariansView(stage));
            });
        } else {
            backButton.setOnAction(e -> {
                EmployeeHomePage hp = new EmployeeHomePage(currentUser);
                stage.setScene(hp.showView(stage));
            });
        }
        
        performanceButton.setOnAction(e -> {
            LibrarianPerformanceView librarianPerformanceView = new LibrarianPerformanceView(currentUser);
            stage.setScene(librarianPerformanceView.showLibrarianPerformanceView(stage, librarian));
        });

        return new Scene(border, 700, 500);
    }
}
