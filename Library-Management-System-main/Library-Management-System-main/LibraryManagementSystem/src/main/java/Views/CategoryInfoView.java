package Views;

import Controllers.CategoryController;
import Controllers.FileController;
import Models.Category;
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

public class CategoryInfoView {
    private User currentUser;

    public CategoryInfoView(User currentUser) {
        this.currentUser = currentUser;
    }

    public Scene showcategory(Stage stage) {
        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Button> categoryNameBt = new ArrayList<>();
        for (Category category : FileController.categories) {
            categories.add(category);
            categoryNameBt.add(new Button(category.getCategoryName()));
        }
        Button back = new Button("Back");
        categoryNameBt.add(back);

        BorderPane border = new BorderPane();
        Text text = new Text(" ");
        if (currentUser.getAccessLevel() == 2 || currentUser.getAccessLevel() == 3
                || currentUser.getUserRole() == Roles.Admin) {
            text.setText("Category Info/Edit");
        } else {
            text.setText("Category Info");
        }
        StackPane p = new StackPane();
        text.setFont(new Font(30));
        p.getChildren().add(text);
        p.setPadding(new Insets(20));
        border.setTop(p);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5);
        gp.setVgap(5);

        int row = 0;
        HBox currentHBox = new HBox(10);

        for (int i = 0; i < categoryNameBt.size(); i++) {
            int finalI = i;
            currentHBox.getChildren().add(categoryNameBt.get(i));

            if ((i + 1) % 5 == 0 || i == categoryNameBt.size() - 1) {
                gp.add(currentHBox, 0, row++);
                currentHBox = new HBox(10);
            }
            categoryNameBt.get(i).setOnAction(e -> {
                if (finalI < categories.size()) {
                    CategoryController categoryController = new CategoryController();
                    Category category = categoryController.findCategory(finalI);
                    EditCategoryView editCategoryView= new EditCategoryView(currentUser);
                    stage.setScene(editCategoryView.editCategory(stage, category));
                } else if (finalI == categoryNameBt.size() - 1) {
                    EmployeeHomePage employeeHomePage = new EmployeeHomePage(currentUser);
                    stage.setScene(employeeHomePage.showView(stage));
                }
            });
        }

        border.setCenter(gp);
        return new Scene(border, 700, 500);
    }
}
