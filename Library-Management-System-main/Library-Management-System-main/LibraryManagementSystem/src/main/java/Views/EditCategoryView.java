package Views;



import Controllers.CategoryController;
import Models.Category;
import Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditCategoryView {
    private User currentUser;

    public EditCategoryView(User currentUser) {
        this.currentUser = currentUser;
    }
    public Scene editCategory(Stage stage, Category category) {
        BorderPane bp = new BorderPane();

        StackPane st = new StackPane();
        Text text = new Text("Edit Category");
        text.setFont(new Font(30));
        st.getChildren().add(text);
        st.setPadding(new Insets(20));
        bp.setTop(st);

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Category name");
        TextField nameField = new TextField(category.getCategoryName());
        gp.add(nameLabel, 0, 0);
        gp.add(nameField, 1, 0);

        Label systemLabel = new Label("System");
        Label label1=new Label("");
        label1.setTextFill(Color.RED);
        gp.add(systemLabel, 0, 1);
        gp.add(label1, 1, 1);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            CategoryController controller = new CategoryController();
            var edited = controller.editCategory(category.getID(),nameField.getText());
            if (edited.getErrorMessage() == null) {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setHeaderText("Category was successfully edited!");
                error.showAndWait();
                Views.CategoryInfoView categoryInfoView = new Views.CategoryInfoView(currentUser);
                stage.setScene(categoryInfoView.showcategory(stage));
            } else {
                label1.setText(edited.getErrorMessage());
            }
        });

        // Button deleteCategoryButton = new Button("Delete Category");
        // deleteCategoryButton.setOnAction(e->{
        //     CategoryController categoryController = new CategoryController();
        //     var deleted = categoryController.deleteCategory(category.getID());
        //     if(deleted){
        //         Alert error = new Alert(Alert.AlertType.INFORMATION);
        //         error.setHeaderText("Category was successfully deleted");
        //         error.showAndWait();
        //         Views.CategoryInfoView categoryInfoView = new Views.CategoryInfoView(currentUser);
        //         stage.setScene(categoryInfoView.showcategory(stage));
        //     }
        //     else {
        //         Alert fail = new Alert(Alert.AlertType.ERROR);
        //         fail.setHeaderText("Category wasn't deleted");
        //         fail.showAndWait();
        //         Views.CategoryInfoView categoryInfoView = new Views.CategoryInfoView(currentUser);
        //         stage.setScene(categoryInfoView.showcategory(stage));
        //     }
        // });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(i -> {
            Views.CategoryInfoView categoryInfoView = new Views.CategoryInfoView(currentUser);
            stage.setScene(categoryInfoView.showcategory(stage));
        });
        HBox b2 = new HBox();
        b2.setSpacing(10);
        b2.getChildren().addAll(editButton, backBtn);
        gp.add(b2, 1, 2);
        bp.setCenter(gp);
        return new Scene(bp, 700, 500);
    }
}

