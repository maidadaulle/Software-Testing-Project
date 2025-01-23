package Views;

import Controllers.BookController;
import Controllers.CategoryController;
import Controllers.FileController;
import Models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AddBookView {
    private User currentUser;
    private Path targetPath;
    private File selectedFile;
    private String relativeImagePath;

    public void setRelativeImagePath(String relativeImagePath) {
        this.relativeImagePath = relativeImagePath;
    }

    public void saveToFolder() {
        if (selectedFile != null) {
            try {
                Files.copy(this.selectedFile.toPath(), this.targetPath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public void setTargetPath(Path targetPath) {
        this.targetPath = targetPath;
    }

    public AddBookView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene addBook(Stage stage) {
        BorderPane bp = new BorderPane();
        Text title = new Text("Add Book");
        StackPane p1 = new StackPane();
        title.setFont(new Font(30));
        p1.getChildren().add(title);
        p1.setPadding(new Insets(20));
        bp.setTop(p1);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setAlignment(Pos.CENTER);

        Label isbn = new Label("ISBN");
        TextField isbn1 = new TextField();
        isbn1.setId("isbn1");
        gp.add(isbn, 0, 0);
        gp.add(isbn1, 1, 0);

        Label book = new Label("Book Title");
        TextField booktitle = new TextField();
        booktitle.setId("booktitle");
        gp.add(book, 0, 1);
        gp.add(booktitle, 1, 1);

        Label authorL = new Label("Select an Author");
        ArrayList<Author> authors = FileController.authors;
        ComboBox<Author> authorComboBox = new ComboBox<>();
        authorComboBox.setId("authorComboBox");
        authorComboBox.getItems().addAll(authors);
        gp.add(authorL, 0, 2);
        gp.add(authorComboBox, 1, 2);

        Label categoryL = new Label("Select the category");
        gp.add(categoryL, 0, 3);
        ArrayList<Category> categories = FileController.categories;
        ArrayList<CheckBox> categoryCheckboxes = new ArrayList<>();
        for (Category g : categories) {
            CheckBox checkbox = new CheckBox(g.toString());
            checkbox.setId("categoryCheckBox_" + g.toString());
            categoryCheckboxes.add(checkbox);
        }
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(4));
        pane.getChildren().addAll(categoryCheckboxes);
        gp.add(pane, 1, 3);

        Label su = new Label("Supplier");
        TextField sut = new TextField();
        sut.setId("supplier");
        gp.add(su, 0, 4);
        gp.add(sut, 1, 4);

        Label p = new Label("Purchased Price");
        TextField pt = new TextField();
        pt.setId("purchasePrice");
        gp.add(p, 0, 5);
        gp.add(pt, 1, 5);

        Label o = new Label("Original Price");
        TextField ot = new TextField();
        ot.setId("originalPrice");
        gp.add(o, 0, 6);
        gp.add(ot, 1, 6);

        Label s = new Label("Selling Price");
        TextField st = new TextField();
        st.setId("sellingPrice");
        gp.add(s, 0, 7);
        gp.add(st, 1, 7);

        Label coverL = new Label("Cover");
        gp.add(coverL, 0, 8);
        Button openButton = new Button("Select Cover");
        openButton.setId("selectCoverButton");
        gp.add(openButton, 1, 8);

        Label errorLabel = new Label("");
        errorLabel.setId("errorLabel");
        errorLabel.setTextFill(Color.RED);
        gp.add(errorLabel, 1, 9);  // Add errorLabel to display error messages

        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            setSelectedFile(fileChooser.showOpenDialog(stage));

            if (selectedFile != null) {
                Path imageFolder = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers", "images");
                setRelativeImagePath(System.currentTimeMillis() + "_" + selectedFile.getName());
                setTargetPath(imageFolder.resolve(relativeImagePath));

                String imageUrl = selectedFile.toURI().toString();
                Image image = new Image(imageUrl);

                ImageView cover = new ImageView(image);
                cover.setId("bookCoverImage");
                cover.setFitHeight(120);
                cover.setFitWidth(100);
                gp.add(cover, 2, 8);
            } else {
                // If no image is selected, it's not an error.
                // errorLabel.setText("You need to select a picture!");
            }
        });

        Button registerButton = new Button("Register Book");
        registerButton.setId("registerBookButton");
        registerButton.setOnAction(e -> {
            BookController controller = new BookController();
            CategoryController cc = new CategoryController();
            ArrayList<Category> selected = new ArrayList<>();
            errorLabel.setText(""); // Clear previous errors

            if (isbn1.getText().isEmpty()) {
                errorLabel.setText("ISBN can't be empty!");
            } else {
                try {
                    controller.verifyISBN(isbn1.getText());
                    if (booktitle.getText().isEmpty()) {
                        errorLabel.setText("You need to insert a title!");
                    } else if (authorComboBox.getSelectionModel().getSelectedItem() == null) {
                        errorLabel.setText("Select at least one author!");
                    } else if (!controller.selectedCategory(categoryCheckboxes)) {
                        errorLabel.setText("Select at least one category!");
                    } else if (sut.getText().isEmpty()) {
                        errorLabel.setText("You need to insert a supplier!");
                    } else if (pt.getText().isEmpty() || ot.getText().isEmpty() || st.getText().isEmpty()) {
                        errorLabel.setText("You need to insert price!");
                    } else if (!controller.priceValidation(pt.getText()) || !controller.priceValidation(ot.getText())
                            || !controller.priceValidation(st.getText())) {
                        errorLabel.setText("Price can't have letters!");
                    } else if (controller.findBook(isbn1.getText()) != null) {
                        errorLabel.setText("Book with this ISBN already exists!");
                    } else if (Integer.parseInt(pt.getText()) <= 0 || Integer.parseInt(ot.getText()) <= 0
                            || Integer.parseInt(st.getText()) <= 0) {
                        errorLabel.setText("Price can't be negative or 0!");
                    } else {
                        for (int i = 0; i < categoryCheckboxes.size(); i++) {
                            if (categoryCheckboxes.get(i).isSelected()) {
                                selected.add(cc.findCategory(i));
                            }
                        }
                        if (selectedFile != null) {
                            saveToFolder();  // Only save the image if a file was selected
                        }

                        controller.createBook(isbn1.getText(), booktitle.getText(), authorComboBox.getValue(), selected,
                                sut.getText(), Integer.parseInt(pt.getText()), Integer.parseInt(ot.getText()),
                                Integer.parseInt(st.getText()), relativeImagePath);

                        // Success message
                        errorLabel.setTextFill(Color.GREEN);
                        errorLabel.setText("Book was successfully added!");
                    }
                } catch (InvalidIsbnFormatException e2) {
                    errorLabel.setText("ISBN format is incorrect!");
                }
            }
        });

        Button back = new Button("Back");
        back.setId("backButton");
        back.setOnAction(e -> {
            EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
            stage.setScene(employeeHomePage.showView(stage));
        });

        HBox b2 = new HBox();
        b2.setSpacing(10);
        b2.getChildren().addAll(registerButton, back);
        gp.add(b2, 1, 10);
        bp.setCenter(gp);

        return new Scene(bp, 800, 600);
    }
}
