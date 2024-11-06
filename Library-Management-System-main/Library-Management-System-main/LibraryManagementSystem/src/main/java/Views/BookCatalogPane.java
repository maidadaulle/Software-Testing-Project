package Views;

import java.util.ArrayList;

import Controllers.FileController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BookCatalogPane {
    public VBox showPane() {
        VBox pane = new VBox(10);
        pane.setAlignment(Pos.CENTER);

        ArrayList<ImageView> bookCatalog = new ArrayList<>();
        for (int i = 0; i < FileController.books.size(); i++) {
            Path folderPath = Paths.get("LibraryManagementSystem", "src", "main", "java", "Controllers", "images");
            Path imagePath = folderPath.resolve(FileController.books.get(i).getCover());
            ImageView display = new ImageView(new Image(imagePath.toUri().toString()));
            display.setFitWidth(100);
            display.setFitHeight(120);
            bookCatalog.add(display);
        }

        HBox currentHBox = new HBox(15);
        currentHBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < bookCatalog.size(); i++) {
            currentHBox.getChildren().add(bookCatalog.get(i));
            if ((i + 1) % 6 == 0 || i == bookCatalog.size() - 1) {
                pane.getChildren().add(currentHBox);
                currentHBox = new HBox(15);
                currentHBox.setAlignment(Pos.CENTER);
            }
        }

        for (int i = 0; i < bookCatalog.size(); i++) {
            ImageView imageView = bookCatalog.get(i);
            int bookIndex = i;

            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    pane.getChildren().clear();
                    BookDetailsPane bookDetails = new BookDetailsPane();
                    pane.getChildren().add(bookDetails.showPane(FileController.books.get(bookIndex)));
                }
            });
        }

        return pane;
    }
}
