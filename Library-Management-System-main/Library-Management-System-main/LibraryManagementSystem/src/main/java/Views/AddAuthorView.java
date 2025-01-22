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

        // Name field
        Label name = new Label("Name");
        TextField nameT = new TextField();
        nameT.setId("nameT");  // Set ID for the name field
        gp.add(name, 0, 0);
        gp.add(nameT, 1, 0);

        // Surname field
        Label surname = new Label("Surname");
        TextField surnameT = new TextField();
        surnameT.setId("surnameT");  // Set ID for the surname field
        gp.add(surname, 0, 1);
        gp.add(surnameT, 1, 1);

        // Gender selection
        Label gl = new Label("Gender");
        ToggleGroup tg = new ToggleGroup();
        RadioButton M = new RadioButton("Male");
        M.setId("maleRadio");  // Set ID for Male radio button
        RadioButton F = new RadioButton("Female");
        F.setId("femaleRadio");  // Set ID for Female radio button
        RadioButton O = new RadioButton("Other");
        O.setId("otherRadio");  // Set ID for Other radio button
        M.setToggleGroup(tg);
        F.setToggleGroup(tg);
        O.setToggleGroup(tg);
        HBox b1 = new HBox(10);
        b1.getChildren().addAll(M, F, O);
        gp.add(gl, 0, 2);
        gp.add(b1, 1, 2);

        // System label and error message
        Label systemLabel = new Label("System");
        Label label1 = new Label("");
        label1.setId("systemField");  // Set ID for error message label
        label1.setTextFill(Color.RED);
        gp.add(systemLabel, 0, 3);
        gp.add(label1, 1, 3);

        // Register button
        Button registerButton = new Button("Register Author");
        registerButton.setId("registerButton");  // Set ID for Register button
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
            } else {
                label1.setText(added.getErrorMessage());
            }
        });

        // Back button
        Button back = new Button("Back");
        back.setId("backButton");  // Set ID for Back button
        back.setOnAction(e -> {
            EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
            stage.setScene(employeeHomePage.showView(stage));
        });

        // Buttons container
        HBox b2 = new HBox();
        b2.setSpacing(10);
        b2.getChildren().addAll(registerButton, back);
        gp.add(b2, 1, 4);

        bp.setCenter(gp);
        return new Scene(bp, 700, 500);
    }
}
