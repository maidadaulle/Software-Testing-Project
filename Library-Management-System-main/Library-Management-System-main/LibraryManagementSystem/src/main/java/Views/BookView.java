package Views;

import Controllers.BookController;
import Models.Book;
import Models.InvalidIsbnFormatException;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookView {
    private User currentUser;

    public BookView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showView(Stage stage) {
        VBox pane = new VBox(10);
        GridPane pane1 = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setAlignment(Pos.CENTER);
        pane1.setAlignment(Pos.CENTER);
        pane1.setPadding(new Insets(10, 10, 10, 10));
        pane1.setHgap(10);
        pane1.setVgap(10);

        Label searchL = new Label("Search with ISBN: ");
        TextField searchF = new TextField();
        Label searchMsg = new Label();
        searchMsg.setStyle("-fx-text-fill: red;");

        Text text = new Text("Find Book Information");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));

        pane1.add(text, 1, 0);
        pane1.add(searchL, 0, 1);
        pane1.add(searchF, 1, 1);
        pane1.add(searchMsg, 1, 2);

        Button back = new Button("Homepage");
        pane1.add(back, 2, 1);
        back.setOnAction(e -> {
            EmployeeHomePage hp = new EmployeeHomePage(currentUser);
            stage.setScene(hp.showView(stage));
        });

        BookController bc = new BookController();
        BookCatalogPane bcp = new BookCatalogPane();
        BookDetailsPane bdp = new BookDetailsPane();
        pane.getChildren().addAll(bcp.showPane());

        searchF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                bc.verifyISBN(newValue);
                searchF.setStyle("-fx-text-fill: black;");
                searchMsg.setText(null);
            } catch (InvalidIsbnFormatException e) {
                searchF.setStyle("-fx-text-fill: red;");
                searchMsg.setText(e.getMessage());
            }
        });

        searchF.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (searchMsg.getText() == null) {
                    Book foundBook = bc.findBook(searchF.getText());
                    if (foundBook != null) {
                        pane.getChildren().clear();
                        pane.getChildren().addAll(bdp.showPane(foundBook));

                    } else {
                        Alert error = new Alert(AlertType.INFORMATION);
                        error.setHeaderText("This book doesn't exists in the database!");
                        error.showAndWait();
                    }
                }
            }
        });

        BorderPane bp = new BorderPane();
        bp.setTop(pane1);
        bp.setCenter(pane);
        ScrollPane sp = new ScrollPane(bp);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        Scene sc = new Scene(sp, 700, 500);
        sp.prefWidthProperty().bind(sc.widthProperty());
        sp.prefHeightProperty().bind(sc.heightProperty());
        return sc;
    }
}