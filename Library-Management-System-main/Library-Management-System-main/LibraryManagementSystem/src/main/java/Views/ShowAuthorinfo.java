package Views;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowAuthorinfo {
    private User currentUser;

    public ShowAuthorinfo(User currentUser) {
        this.currentUser = currentUser;
    }
    public Scene showAuthor(Stage stage, Author author) {
        BorderPane bp = new BorderPane();

        StackPane st = new StackPane();
        Text text = new Text("Author Info");
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
        nameField.setEditable(false);
        gp.add(nameLabel, 0, 0);
        gp.add(nameField, 1, 0);

        Label surnameLabel = new Label("Surname");
        TextField surnameField = new TextField(author.getSurname());
        surnameField.setEditable(false);
        gp.add(surnameLabel, 0, 1);
        gp.add(surnameField, 1, 1);

        Label genderLabel = new Label("Gender");
        TextField genderField = new TextField();
        genderField.setEditable(false);
        gp.add(genderLabel, 0, 2);
        gp.add(genderField, 1, 2);
        if(author.getGender()==Gender.Male){
            genderField.setText("Male");
        }
        else if(author.getGender()==Gender.Female) {
            genderField.setText("Female");
        }
        else {
            genderField.setText("Other");
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            Views.AuthorInfoView authorInfoView = new Views.AuthorInfoView(currentUser);
            stage.setScene(authorInfoView.showView(stage));
        });
        HBox b2 = new HBox();
        b2.setSpacing(10);
        b2.getChildren().add(backBtn);
        gp.add(b2, 1, 3);
        bp.setCenter(gp);
        return new Scene(bp, 700, 500);
    }
}