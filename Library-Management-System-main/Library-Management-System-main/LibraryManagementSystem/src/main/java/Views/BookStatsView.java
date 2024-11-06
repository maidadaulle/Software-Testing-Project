package Views;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;

import Controllers.BookController;
import Controllers.FileController;
import Models.Bill;
import Models.BillsType;
import Models.Book;
import Models.InvalidIsbnFormatException;
import Models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookStatsView {
    private static User currentUser;
    private static Stage stage;

    public BookStatsView(User u, Stage s) {
        currentUser = u;
        stage = s;
    }

    public Scene showView() {
        BorderPane pane = new BorderPane();
        GridPane gPane = new GridPane();
        VBox vb = new VBox(10);
        vb.setAlignment(Pos.CENTER_LEFT);
        HBox hb = new HBox(20);
        hb.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setHgap(10);
        gPane.setVgap(10);

        pane.setTop(gPane);
        pane.setCenter(hb);

        Label searchL = new Label("Search with ISBN: ");
        TextField searchF = new TextField();
        Label searchMsg = new Label();
        searchMsg.setStyle("-fx-text-fill: red;");

        Text text = new Text("Find Number Of Book Copies Sold");
        StackPane stack = new StackPane();
        text.setFont(new Font(30));
        stack.getChildren().add(text);
        stack.setPadding(new Insets(20));

        gPane.add(text, 1, 0);
        gPane.add(searchL, 0, 1);
        gPane.add(searchF, 1, 1);
        gPane.add(searchMsg, 1, 2);

        class MyButtonClickHandler implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent event) {
                EmployeeHomePage hp = new EmployeeHomePage(currentUser);
                stage.setScene(hp.showView(stage));
            }
        }
        MyButtonClickHandler myBtn = new MyButtonClickHandler();

        Button back = new Button("Homepage");
        gPane.add(back, 2, 1);
        back.setOnAction(myBtn::handle);

        BookController bc = new BookController();

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
                    hb.getChildren().clear();
                    vb.getChildren().clear();
                    Book foundBook = bc.findBook(searchF.getText());
                    if (foundBook != null) {

                        Path folderPath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers",
                                "images");
                        Path imagePath = folderPath.resolve(foundBook.getCover());
                        ImageView cover = new ImageView(new Image(imagePath.toUri().toString()));
                        cover.setFitWidth(300);
                        cover.setFitHeight(290);
                        cover.setPreserveRatio(true);
                        Label book = new Label("Book: " + foundBook.getBookTitle());
                        Label author = new Label("Author: " + foundBook.getAuthor().getName() + " "
                                + foundBook.getAuthor().getSurname());

                        DatePicker datePicker1 = new DatePicker();
                        DatePicker datePicker2 = new DatePicker();
                        datePicker1.setEditable(false);
                        datePicker2.setEditable(false);

                        int q = 0;
                        Label sold = new Label();

                            for (Bill b : FileController.transactions) {
                                if (b.getType() == BillsType.Sold) {
                                    for (int i = 0; i < b.getBooks().size(); i++) {
                                        if (b.getBooks().get(i).getISBN().equals(foundBook.getISBN()))
                                            q += b.getQuantity().get(i);
                                    }
                                }
                            }
                            sold.setText("Total Sold Number: " + q);
                        
                        int qFinal=q;
                        Button check = new Button("Check");
                        check.setOnAction(e1->{
                            if(datePicker1.getValue()==null&&datePicker2.getValue()==null){
                                sold.setText("Total Sold Number: " + qFinal);
                            } else if(datePicker1.getValue()==null||datePicker2.getValue()==null){
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("You need to select both dates!");
                                error.showAndWait();
                            } else if(datePicker1.getValue().isAfter(LocalDate.now())||datePicker2.getValue().isAfter(LocalDate.now())){
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("Dates picked can't be after today's date!");
                                error.showAndWait();
                            } else if(datePicker1.getValue().isAfter(datePicker2.getValue())){
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("Beginning date can't be after end date!");
                                error.showAndWait();
                            } else {
                                int qF=0;
                                for (Bill b : FileController.transactions) {
                                    if (b.getType() == BillsType.Sold&&isDateInRange(b.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                                        datePicker1.getValue(),datePicker2.getValue())) {
                                        for (int i = 0; i < b.getBooks().size(); i++) {
                                            if (b.getBooks().get(i).getISBN().equals(foundBook.getISBN()))
                                                qF += b.getQuantity().get(i);
                                        }
                                    }
                                }
                                sold.setText("Sold Number: " + qF);
                            }
                        });

                        GridPane gp1 = new GridPane();
                        gp1.setAlignment(Pos.CENTER);
                        gp1.setVgap(10);
                        gp1.setHgap(10);
                        gp1.add(datePicker1, 0, 0);
                        gp1.add(datePicker2, 1, 0);
                        gp1.add(check, 2, 0);

                        vb.getChildren().addAll(book, author, sold);
                        hb.getChildren().addAll(cover, vb, gp1);
                    } else {
                        Alert error = new Alert(AlertType.INFORMATION);
                        error.setHeaderText("This book doesn't exists in the database!");
                        error.showAndWait();
                    }
                }
            }
        });

        ScrollPane sp = new ScrollPane(pane);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        Scene sc = new Scene(sp, 700, 500);
        sp.prefWidthProperty().bind(sc.widthProperty());
        sp.prefHeightProperty().bind(sc.heightProperty());
        return sc;
    }

    private boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
               (date.isEqual(endDate) || date.isBefore(endDate));
    }
}
