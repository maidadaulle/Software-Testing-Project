package Views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import Controllers.BookController;
import Controllers.CategoryController;
import Controllers.FileController;
import Models.Author;
import Models.Book;
import Models.Category;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModifyBookView {
    private static User currentUser;
    private static Stage stage;
    private Path targetPath;
    private File selectedFile;
    private String relativeImagePath;
    private ImageView cover;

    public ModifyBookView(User u, Stage s) {
        currentUser = u;
        stage = s;
    }

    public void setRelativeImagePath(String relativeImagePath) {
        this.relativeImagePath = relativeImagePath;
    }

    public void saveToFolder(Book foundBook) {
        try {
            Path folderPath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers",
                    "images");
            Path oldImagePath = folderPath.resolve(foundBook.getCover());
            Files.delete(oldImagePath);
            Files.copy(this.selectedFile.toPath(), this.targetPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setTargetPath(Path targetPath) {
        this.targetPath = targetPath;
    }

    public Scene showView() {
        BorderPane pane = new BorderPane();
        GridPane gPane = new GridPane();
        GridPane gp1 = new GridPane();
        gp1.setAlignment(Pos.CENTER);
        gp1.setVgap(10);
        gp1.setHgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setHgap(10);
        gPane.setVgap(10);

        pane.setTop(gPane);
        pane.setCenter(gp1);

        Label searchL = new Label("Search with ISBN: ");
        TextField searchF = new TextField();
        Label searchMsg = new Label();
        searchMsg.setStyle("-fx-text-fill: red;");

        Text text = new Text("Find Book To Modify");
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
                    gp1.getChildren().clear();
                    Book foundBook = bc.findBook(searchF.getText());
                    if (foundBook != null) {

                        Label isbn = new Label("ISBN");
                        TextField isbn1 = new TextField(foundBook.getISBN());
                        isbn1.setEditable(false);
                        gp1.add(isbn, 0, 0);
                        gp1.add(isbn1, 1, 0);

                        Label book = new Label("Book Title");
                        TextField booktitle = new TextField(foundBook.getBookTitle());
                        gp1.add(book, 0, 1);
                        gp1.add(booktitle, 1, 1);

                        Label authorL = new Label("Select an Author");
                        ArrayList<Author> authors = FileController.authors;
                        ComboBox<Author> authorComboBox = new ComboBox<>();
                        authorComboBox.getItems().addAll(authors);

                        Author foundAuthor = foundBook.getAuthor();
                        if (foundAuthor != null) {
                            authorComboBox.getSelectionModel().select(foundAuthor);
                        }

                        gp1.add(authorL, 0, 2);
                        gp1.add(authorComboBox, 1, 2);

                        Label categoryL = new Label("Select the category");
                        gp1.add(categoryL, 0, 3);
                        ArrayList<CheckBox> categoryCheckboxes = new ArrayList<>();
                        VBox vb = new VBox(10);
                        vb.setPadding(new Insets(4));
                        gp1.add(vb, 1, 3);

                        for (Category category : FileController.categories) {
                            CheckBox checkBox = new CheckBox(category.toString());
                            categoryCheckboxes.add(checkBox);
                            vb.getChildren().add(checkBox);

                            for (Category c : foundBook.getBookCategories()) {
                                if (c.getCategoryName().equals(category.getCategoryName()))
                                    checkBox.setSelected(true);
                            }
                        }

                        Label su = new Label("Suplier");
                        TextField sut = new TextField(foundBook.getSupplier());
                        gp1.add(su, 0, 4);
                        gp1.add(sut, 1, 4);

                        Label p = new Label("Purchased Price");
                        TextField pt = new TextField(Integer.toString(foundBook.getPurchasedPrice()));
                        gp1.add(p, 0, 5);
                        gp1.add(pt, 1, 5);

                        Label o = new Label("Original Price");
                        TextField ot = new TextField(Integer.toString(foundBook.getOriginalPrice()));
                        gp1.add(o, 0, 6);
                        gp1.add(ot, 1, 6);

                        Label s = new Label("Selling Price");
                        TextField st = new TextField(Integer.toString(foundBook.getSellingPrice()));
                        gp1.add(s, 0, 7);
                        gp1.add(st, 1, 7);

                        Label coverL = new Label("Cover");
                        gp1.add(coverL, 0, 8);

                        Button openButton = new Button("Select Cover");
                        gp1.add(openButton, 1, 8);

                        Path folderPath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers",
                                "images");
                        Path imagePath = folderPath.resolve(foundBook.getCover());
                        cover = new ImageView(new Image(imagePath.toUri().toString()));
                        cover.setFitWidth(300);
                        cover.setFitHeight(290);
                        cover.setPreserveRatio(true);
                        gp1.add(cover, 2, 8);

                        openButton.setOnAction(e2 -> {
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setInitialDirectory(
                                    new File(System.getProperty("user.home") + File.separator + "Desktop"));
                            fileChooser.getExtensionFilters()
                                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
                            setSelectedFile(fileChooser.showOpenDialog(stage));

                            if (selectedFile != null) {
                                Path imageFolder = Paths.get("LibraryManagementSystem", "src", "main", "java",
                                        "Controllers", "images");
                                setRelativeImagePath(System.currentTimeMillis() + "_" + selectedFile.getName());
                                setTargetPath(imageFolder.resolve(relativeImagePath));
                                String imageUrl = selectedFile.toURI().toString();
                                Image image = new Image(imageUrl);
                                gp1.getChildren().remove(cover);
                                cover = new ImageView(image);
                                cover.setFitWidth(300);
                                cover.setFitHeight(290);
                                cover.setPreserveRatio(true);
                                gp1.add(cover, 2, 8);
                            }
                        });

                        Button save = new Button("Save Edits");
                        Button delete = new Button("Delete Book");
                        gp1.add(save, 3, 9);
                        gp1.add(delete, 4, 9);

                        delete.setOnAction(d -> {
                            try {
                                Path oldImagePath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers",
                                "images").resolve(foundBook.getCover());
                                Files.delete(oldImagePath);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            FileController.books.remove(foundBook);
                            Alert info = new Alert(AlertType.INFORMATION);
                            info.setHeaderText("Book removed successfully!");
                            info.showAndWait();

                            EmployeeHomePage hp = new EmployeeHomePage(currentUser);
                            stage.setScene(hp.showView(stage));
                        });

                        save.setOnAction(e1 -> {
                            BookController controller = new BookController();
                            CategoryController cc = new CategoryController();
                            ArrayList<Category> selected = new ArrayList<>();

                            if (booktitle.getText().isEmpty()) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("You need to inseret a title!");
                                error.showAndWait();
                            } else if (authorComboBox.getSelectionModel().getSelectedItem() == null) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("Select at least one author!");
                                error.showAndWait();
                            } else if (!controller.selectedCategory(categoryCheckboxes)) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("Select at least one category!");
                                error.showAndWait();
                            } else if (sut.getText().isEmpty()) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("You need to insert a supplier!");
                                error.showAndWait();
                            } else if (pt.getText().isEmpty() || ot.getText().isEmpty() || st.getText().isEmpty()) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("You need to insert price!");
                                error.showAndWait();
                            } else if (!controller.priceValidation(pt.getText())
                                    || !controller.priceValidation(ot.getText())
                                    || !controller.priceValidation(st.getText())) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("Price can't have letters!");
                                error.showAndWait();
                            } else if (Integer.parseInt(pt.getText()) <= 0 || Integer.parseInt(ot.getText()) <= 0
                                    || Integer.parseInt(st.getText()) <= 0) {
                                Alert error = new Alert(AlertType.ERROR);
                                error.setHeaderText("Price can't be negative or 0!");
                                error.showAndWait();
                            } else {
                                for (int i = 0; i < categoryCheckboxes.size(); i++) {
                                    if (categoryCheckboxes.get(i).isSelected()) {
                                        selected.add(cc.findCategory(i));
                                    }
                                }
                                if (selectedFile != null) {
                                    saveToFolder(foundBook);
                                    foundBook.setCover(relativeImagePath);
                                }
                                foundBook.setBookTitle(booktitle.getText());
                                foundBook.setAuthor(authorComboBox.getValue());
                                foundBook.setBookCategories(selected);
                                foundBook.setOriginalPrice(Integer.parseInt(ot.getText()));
                                foundBook.setPurchasedPrice(Integer.parseInt(pt.getText()));
                                foundBook.setSellingPrice(Integer.parseInt(st.getText()));
                                foundBook.setSupplier(sut.getText());

                                Alert info = new Alert(AlertType.INFORMATION);
                                info.setHeaderText("Book modified successfully!");
                                info.showAndWait();

                                EmployeeHomePage hp = new EmployeeHomePage(currentUser);
                                stage.setScene(hp.showView(stage));

                                System.out.println(foundBook);
                            }
                        });

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
}
