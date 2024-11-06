package Views;

import Controllers.Changepasscontroller;
import Models.Roles;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Changepassview {
    private User currentUser;

    public Changepassview(User currentUser) {
        this.currentUser = currentUser;
    }
    public Scene showview(Stage stage){
        BorderPane bp = new BorderPane();
        Text text = new Text("Change Password");
        StackPane pane1 = new StackPane();
        text.setFont(new Font(30));
        pane1.getChildren().add(text);
        pane1.setPadding(new Insets(20));
        bp.setTop(pane1);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setAlignment(Pos.CENTER);

        Label pass=new Label("Enter new password");
        PasswordField pass1= new PasswordField();
        gp.add(pass, 0, 1);
        gp.add(pass1, 1, 1);

        Label systemLabel = new Label("System");
        Label label1=new Label("");
        label1.setTextFill(Color.RED);
        gp.add(systemLabel, 0, 2);
        gp.add(label1, 1, 2);

        HBox h1=new HBox(10);
        Button confirmbtn=new Button("Confirm");
        h1.getChildren().add(confirmbtn);
        Button backbtn=new Button("Back");
        h1.getChildren().add(backbtn);
        gp.add(h1,1,3);
        confirmbtn.setOnAction(e->{
            Changepasscontroller controller = new Changepasscontroller();
            var edited = controller.changepass(pass1.getText(), currentUser.getId());
            if (edited.getErrorMessage().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("Password was successfully changed!");
                error.showAndWait();
                if(currentUser.getUserRole()==Roles.Admin){
                    AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                    stage.setScene(adminHomePage.showAdminHomePage(stage));
                }
                else{
                    EmployeeHomePage employeeHomePage=new EmployeeHomePage(currentUser);
                    stage.setScene(employeeHomePage.showView(stage));
                }
            }
            else {
                label1.setText(edited.getErrorMessage());
            }
        });
        backbtn.setOnAction(e->{
            if(currentUser.getUserRole()==Roles.Admin){
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.showAdminHomePage(stage));
            }
            else {
                EmployeeHomePage employeeHomePage=new EmployeeHomePage(currentUser);
                stage.setScene(employeeHomePage.showView(stage));
            }
        });
        bp.setCenter(gp);
        return new Scene(bp, 700, 500);
    }
}
