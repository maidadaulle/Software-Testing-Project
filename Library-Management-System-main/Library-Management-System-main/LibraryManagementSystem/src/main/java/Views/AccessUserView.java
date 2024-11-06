package Views;

import Controllers.LibrarianController;
import Controllers.ManagerController;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccessUserView {
    public Scene showAccessUserView(User user, Stage stage,int userLevel)
    {
        BorderPane borderPane = new BorderPane();

        StackPane stackText = new StackPane();
        Text text = new Text();
        if(userLevel == 1) {
             text.setText("Librarian Access");
        }else{
            text.setText("Manager Access");
        }

        text.setFont(new Font(30));
        stackText.getChildren().add(text);
        stackText.setPadding(new Insets(20));
        borderPane.setTop(stackText);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);

        ToggleGroup tgA = new ToggleGroup();
        Label accessL = new Label("Access Level");
        accessL.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;");

        RadioButton level1 = new RadioButton("Sell books");
        RadioButton level2 = new RadioButton("Add Stock/Authors/Categories");
        RadioButton level3 = new RadioButton("Both");
        level1.setToggleGroup(tgA);
        level2.setToggleGroup(tgA);
        level3.setToggleGroup(tgA);

        gridPane.add(accessL, 0, 0);
        HBox b2 = new HBox(10);
        b2.getChildren().addAll(level1, level2, level3);
        gridPane.add(b2, 4, 0);

        Button saveChangesButton = new Button("Save Changes");
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            AdminHomePage statisticMainView = new AdminHomePage(user);
            stage.setScene(statisticMainView.showAdminHomePage(stage));
        });
        HBox vBox = new HBox();
        vBox.setSpacing(15);
        vBox.getChildren().addAll(saveChangesButton,backButton);

        Label errorLabel = new Label("Please choose one option!");
        errorLabel.setVisible(false);
        errorLabel.setStyle("-fx-text-fill: red");
        gridPane.add(errorLabel,4,8);

        saveChangesButton.setOnAction(e->{
            int accessLevel = 0;
            if (level1.isSelected()) {
                accessLevel = 1;
            } else if (level2.isSelected()) {
                accessLevel = 2;
            } else if(level3.isSelected()){
                accessLevel = 3;
            }
            if(accessLevel == 0){
                errorLabel.setVisible(true);
            }else {
                if (userLevel == 2) {
                    ManagerController managerController = new ManagerController();
                    managerController.changeAccessLevel(accessLevel);
                } else if (userLevel == 1) {
                    LibrarianController librarianController = new LibrarianController();
                    librarianController.changeAccessLevel(accessLevel);
                }
                AdminHomePage adminHomePage = new AdminHomePage(user);
                stage.setScene(adminHomePage.showAdminHomePage(stage));
            }
        });


        vBox.setPadding(new Insets(60, 60, 60, 250));
        borderPane.setBottom(vBox);


        borderPane.setCenter(gridPane);
        return new Scene(borderPane,700,500);
    }
}
