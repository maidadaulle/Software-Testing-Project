package Views;

import Controllers.ManagerController;
import Models.Gender;
import Models.Manager;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditManagerView {

    private User currentUser;

    public EditManagerView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showEditManagerView(Manager manager, Stage stage) {
        BorderPane borderPane = new BorderPane();

        StackPane stackText = new StackPane();
        Text text = new Text("Edit Manager");
        text.setFont(new Font(30));
        stackText.getChildren().add(text);
        stackText.setPadding(new Insets(20));
        borderPane.setTop(stackText);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField(manager.getName());
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);

        Label surnameLabel = new Label("Surname");
        TextField surnameTxtField = new TextField(manager.getSurname());
        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameTxtField, 1, 1);

        Label usernameLabel = new Label("Username");
        TextField usernameTxtField = new TextField(manager.getUsername());
        gridPane.add(usernameLabel, 0, 2);
        gridPane.add(usernameTxtField, 1, 2);

        Label passwordLabel = new Label("Password");
        TextField passwordField = new PasswordField();
        passwordField.setText(manager.getPassword());
        passwordField.setEditable(true);
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(passwordField, 1, 3);

        Label phoneNumLabel = new Label("Phone Number");
        TextField phoneNumField = new TextField(manager.getPhoneNum());
        gridPane.add(phoneNumLabel, 0, 4);
        gridPane.add(phoneNumField, 1, 4);

        Label BirthDateLabel = new Label("Birth Date");
        DatePicker dateP = new DatePicker(manager.getBirthDate());
        dateP.setEditable(false);
        gridPane.add(BirthDateLabel, 0, 5);
        gridPane.add(dateP, 1, 5);

        Label genderLabel = new Label("Gender");
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton Male = new RadioButton("Male");
        RadioButton Female = new RadioButton("Female");
        RadioButton Other = new RadioButton("Other");
        Male.setToggleGroup(toggleGroup);
        Female.setToggleGroup(toggleGroup);
        Other.setToggleGroup(toggleGroup);
        HBox b1 = new HBox(10);
        b1.getChildren().addAll(Male, Female, Other);
        gridPane.add(genderLabel, 0, 6);
        gridPane.add(b1, 1, 6);
        switch (manager.getGender()) {
            case Male:
                Male.setSelected(true);
                break;
            case Female:
                Female.setSelected(true);
                break;
            case Other:
                Other.setSelected(true);
                break;
        }

        ToggleGroup tgA = new ToggleGroup();
        Label accessL = new Label("Access Level");
        RadioButton level1 = new RadioButton("Sell books");
        RadioButton level2 = new RadioButton("Add Stock/Authors/Categories");
        RadioButton level3 = new RadioButton("Both");
        level1.setToggleGroup(tgA);
        level2.setToggleGroup(tgA);
        level3.setToggleGroup(tgA);

        gridPane.add(accessL, 0, 7);

        HBox b2 = new HBox(10);
        b2.getChildren().addAll(level1, level2, level3);
        gridPane.add(b2, 1, 7);

        switch (manager.getAccessLevel()) {
            case 1:
                level1.setSelected(true);
                break;
            case 2:
                level2.setSelected(true);
                break;
            default:
                level3.setSelected(true);
                break;
        }
        Label salaryLabel = new Label("Salary");
        TextField salaryField = new TextField(Double.toString(manager.getSalary()));
        gridPane.add(salaryLabel, 0, 8);
        gridPane.add(salaryField, 1, 8);

        Label systemLabel = new Label("System");
        TextArea systemArea = new TextArea();
        systemArea.setEditable(false);
        systemArea.setWrapText(true);
        gridPane.add(systemLabel, 0, 9);
        gridPane.add(systemArea, 1, 9);

        Button editButton = new Button("Edit");
        gridPane.add(editButton, 2, 10);
        editButton.setOnAction(e -> {
            ManagerController controller = new ManagerController();
            Gender gender;
            if (Male.isSelected())
                gender = Gender.Male;
            else if (Female.isSelected())
                gender = Gender.Female;
            else
                gender = Gender.Other;

            int accessLevel;
            if (level1.isSelected()) {
                accessLevel = 1;
            } else if (level2.isSelected()) {
                accessLevel = 2;
            } else {
                accessLevel = 3;
            }
            var edited = controller.editUser(nameField.getText(), surnameTxtField.getText(),
                    usernameTxtField.getText(), salaryField.getText(), phoneNumField.getText(),
                    manager.getId(), gender, accessLevel, dateP.getValue(), passwordField.getText());
            if (edited.getErrorMessage().isEmpty() || edited.getErrorMessage() == null) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("Manager was successfully edited!");
                error.showAndWait();
                AdminHomePage adminHomePage = new AdminHomePage(currentUser);
                stage.setScene(adminHomePage.manageManagersView(stage));
            } else {
                systemArea.setText(edited.getErrorMessage());
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            AdminHomePage adminHomePage = new AdminHomePage(currentUser);
            stage.setScene(adminHomePage.manageManagersView(stage));
        });
        gridPane.add(backBtn, 3, 10);

        borderPane.setCenter(gridPane);
        return new Scene(borderPane, 700, 500);
    }
}
