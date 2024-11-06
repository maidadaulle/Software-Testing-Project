package Views;

import Controllers.AuthorController;
import Models.Author;
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

public class EditAuthorView {
    private User currentUser;

    public EditAuthorView(User currentUser) {
        this.currentUser = currentUser;
    }
    public Scene editAuthor(Stage stage, Author author) {
        BorderPane bp = new BorderPane();

        StackPane st = new StackPane();
        Text text = new Text("Edit Author");
        text.setFont(new Font(30));
        st.getChildren().add(text);
        st.setPadding(new Insets(20));
        bp.setTop(st);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField(author.getName());
        gp.add(nameLabel, 0, 0);
        gp.add(nameField, 1, 0);

        Label surnameLabel = new Label("Surname");
        TextField surnameField = new TextField(author.getSurname());
        gp.add(surnameLabel, 0, 1);
        gp.add(surnameField, 1, 1);

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
        gp.add(genderLabel, 0, 2);
        gp.add(b1, 1, 2);
        switch (author.getGender()) {
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
        Label systemLabel = new Label("System");
        Label label1=new Label("");
        label1.setTextFill(Color.RED);
        gp.add(systemLabel, 0, 3);
        gp.add(label1, 1, 3);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            AuthorController controller = new AuthorController();
            Gender gender;
            if (Male.isSelected())
                gender = Gender.Male;
            else if (Female.isSelected())
                gender = Gender.Female;
            else
                gender = Gender.Other;

            var edited = controller.editAuthor(author.getID(),nameField.getText(), surnameField.getText(), gender);
            if (edited.getErrorMessage() == null) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText("Author was successfully edited!");
                success.showAndWait();
                Views.AuthorInfoView authorInfoView = new Views.AuthorInfoView(currentUser);
                stage.setScene(authorInfoView.showView(stage));
            } else {
                label1.setText(edited.getErrorMessage());
            }
        });
        // Button deleteAuthorButton = new Button("Delete Author");
        // deleteAuthorButton.setOnAction(e->{
        //     AuthorController authorController = new AuthorController();
        //     var deleted = authorController.deleteAuthor(author.getID());
        //     if(deleted){
        //         Alert error = new Alert(Alert.AlertType.INFORMATION);
        //         error.setHeaderText("Author was successfully deleted");
        //         error.showAndWait();
        //         Views.AuthorInfoView authorInfoView = new Views.AuthorInfoView(currentUser);
        //         stage.setScene(authorInfoView.showView(stage));
        //     }
        //     else {
        //         Alert fail = new Alert(Alert.AlertType.ERROR);
        //         fail.setHeaderText("Author wasn't deleted");
        //         fail.showAndWait();
        //         Views.AuthorInfoView authorInfoView = new Views.AuthorInfoView(currentUser);
        //         stage.setScene(authorInfoView.showView(stage));
        //     }
        // });
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            Views.AuthorInfoView authorInfoView = new Views.AuthorInfoView(currentUser);
            stage.setScene(authorInfoView.showView(stage));
        });
        HBox b2 = new HBox();
        b2.setSpacing(10);
        b2.getChildren().addAll(editButton, backBtn);
        gp.add(b2, 1, 4);
        bp.setCenter(gp);
        return new Scene(bp, 700, 500);
    }
}
