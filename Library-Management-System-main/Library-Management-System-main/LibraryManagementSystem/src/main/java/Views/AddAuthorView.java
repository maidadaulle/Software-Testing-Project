package Views;

import Controllers.AuthorController;
import Models.Gender;
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

public class AddAuthorView {
    private User currentUser;

    public AddAuthorView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene addAuthor(Stage stage) {
        BorderPane bp = new BorderPane();
        Text title = new Text("Add Author");
        StackPane p1 = new StackPane();
        title.setFont(new Font(30));
        p1.getChildren().add(title);
        p1.setPadding(new Insets(20));
        bp.setTop(p1);
        // Title
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setAlignment(Pos.CENTER);
        Label name = new Label("Name");
        TextField nameT = new TextField();
        gp.add(name, 0, 0);
        gp.add(nameT, 1, 0);

        Label surname = new Label("Surname");
        TextField surnameT = new TextField();
        gp.add(surname, 0, 1);
        gp.add(surnameT, 1, 1);

        Label gl = new Label("Gender");
        ToggleGroup tg = new ToggleGroup();
        RadioButton M = new RadioButton("Male");
        RadioButton F = new RadioButton("Female");
        RadioButton O = new RadioButton("Other");
        M.setToggleGroup(tg);
        F.setToggleGroup(tg);
        O.setToggleGroup(tg);
        HBox b1 = new HBox(10);
        b1.getChildren().addAll(M, F, O);
        gp.add(gl, 0, 2);
        gp.add(b1, 1, 2);

        Label systemLabel = new Label("System");
        Label label1=new Label("");
        label1.setTextFill(Color.RED);
        gp.add(systemLabel, 0, 3);
        gp.add(label1, 1, 3);

        Button registerButton = new Button("Register Author");
        registerButton.setOnAction(e -> {
            AuthorController controller = new AuthorController();
            Gender gender;
            if (M.isSelected())
                gender = Gender.Male;
            else if (F.isSelected())
                gender = Gender.Female;
            else
                gender = Gender.Other;

            var added = controller.createAuthor(nameT.getText(), surnameT.getText(), gender);
            if (added.getUser() != null) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText("Author was successfully added!");
                success.showAndWait();
                EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
                stage.setScene(employeeHomePage.showView(stage));
            }else {
                label1.setText(added.getErrorMessage());
            }
        });
        Button back = new Button("Back");
        back.setOnAction(e -> {
            EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
            stage.setScene(employeeHomePage.showView(stage));
        });
        HBox b2 = new HBox();
        b2.setSpacing(10);
        b2.getChildren().addAll(registerButton, back);
        gp.add(b2, 1, 4);
        bp.setCenter(gp);
        return new Scene(bp, 700, 500);
    }
}
