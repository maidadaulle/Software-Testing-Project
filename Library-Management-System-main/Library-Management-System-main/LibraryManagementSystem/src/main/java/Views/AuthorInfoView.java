package Views;

import Controllers.AuthorController;
import Controllers.FileController;
import Models.Author;
import Models.Roles;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AuthorInfoView {
    private User currentUser;

    public AuthorInfoView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showView(Stage stage) {
        ArrayList<Author> authors = new ArrayList<>();
        ArrayList<Button> authorNameBt = new ArrayList<>();

        for (Author author : FileController.authors) {
            authors.add(author);
            authorNameBt.add(new Button(author.getName() + " " + author.getSurname()));
        }

        Button backBt = new Button("Back");
        authorNameBt.add(backBt);

        BorderPane border = new BorderPane();
        Text text = new Text(" ");
        if (currentUser.getAccessLevel() == 2 || currentUser.getAccessLevel() == 3
                || currentUser.getUserRole() == Roles.Admin) {
            text.setText("Author Info/Edit");
        } else {
            text.setText("Author Info");
        }

        StackPane pane1 = new StackPane();
        text.setFont(new Font(30));
        pane1.getChildren().add(text);
        pane1.setPadding(new Insets(20));
        border.setTop(pane1);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5);
        gp.setVgap(5);

        int row = 0;
        HBox currentHBox = new HBox(10);

        for (int i = 0; i < authorNameBt.size(); i++) {
            int finalI = i;
            currentHBox.getChildren().add(authorNameBt.get(i));

            if ((i + 1) % 5 == 0 || i == authorNameBt.size() - 1) {
                gp.add(currentHBox, 0, row++);
                currentHBox = new HBox(10);
            }
            authorNameBt.get(i).setOnAction(e -> {
                if(currentUser.getAccessLevel() == 2 || currentUser.getAccessLevel() == 3
                        || currentUser.getUserRole() == Roles.Admin) {
                    if (finalI < authors.size()) {
                        AuthorController authorController = new AuthorController();
                        Author author = authorController.findAuthor(finalI);
                        EditAuthorView editAuthorView = new EditAuthorView(currentUser);
                        stage.setScene(editAuthorView.editAuthor(stage, author));
                    } else if (finalI == authorNameBt.size() - 1) { // Back button
                        EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
                        stage.setScene(employeeHomePage.showView(stage));
                    }
                }
                else{
                    if (finalI < authors.size()) {
                        AuthorController authorController = new AuthorController();
                        Author author = authorController.findAuthor(finalI);
                        ShowAuthorinfo showAuthorinfo = new ShowAuthorinfo(currentUser);
                        stage.setScene(showAuthorinfo.showAuthor(stage, author));
                    } else if (finalI == authorNameBt.size() - 1) { // Back button
                        EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
                        stage.setScene(employeeHomePage.showView(stage));
                    }
                }

            });
        }

        border.setCenter(gp);
        return new Scene(border, 700, 500);
    }
}
