package Models;

import java.io.Serial;
import java.io.Serializable;

public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 8589318269664880015L;
    private static int noCategory;
    private String categoryName;
    private int ID;

    public Category(String categoryName) {
        this.ID = noCategory++;
        this.categoryName = categoryName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }

    public static void setNoCategory(int noCategory) {
        Category.noCategory = noCategory;
    }

}