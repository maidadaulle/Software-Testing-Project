package Controllers;


import Models.Category;
import Models.StandardViewResponse;

public class CategoryController {
    public void addCategory(Category c) {
        FileController.categories.add(c);
    }

    public StandardViewResponse<Category> createCategory(String categoryname) {
        Category category=null;
        try {

            if (categoryname.isEmpty()) {
                return new StandardViewResponse<>(category, "Field is empty!");
            }

            if (categoryname.length() < 3 || categoryname.length() > 20) {
                return new StandardViewResponse<>(category, "Name can't have this length!");
            } else if (categoryname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(category, "Name can't contain numbers!");
            }
            for (char c : categoryname.toCharArray()) {
                if (isSpecialChar(c)) {
                    return new StandardViewResponse<>(category, "Name can't contain special characters!");
                }
            }
            if(!isUniqueCategory(categoryname)) {
                return new StandardViewResponse<>(category, "There already exists a category with this name");
            }
            category = new Category(categoryname);
            addCategory(category);
            System.out.println("Category was successfully added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(category, e.getMessage());
        }
        return new StandardViewResponse<>(category, "");
    }

    public Category findCategory(int id) {
        for (Category c : FileController.categories) {
            if (c.getID() == id)
                return c;
        }
        return null;
    }
    static boolean isSpecialChar(char c) {
        String specialChars = "!@#$%^&*()._";
        return specialChars.contains(String.valueOf(c));
    }
    static boolean isUniqueCategory(String categoryname) {
        for (Category category : FileController.categories) {
            if (category.getCategoryName().equals(categoryname)) {
                return false;
            }
        }
        return true;
    }

    // public boolean deleteCategory(int id) {
    //     var exists = findCategory(id);
    //     if (exists == null) {
    //         return false;
    //     }
    //     for (Category category : FileController.categories) {
    //         if (category.getID() == id) {
    //             FileController.categories.remove(category);
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public StandardViewResponse<Category> editCategory( int ID, String categoryname){
        Category category = findCategory(ID);
        try {

            if (categoryname.isEmpty()) {
                return new StandardViewResponse<>(category, "Fields are empty!");
            }

            if (categoryname.length() < 3 || categoryname.length() > 20) {
                return new StandardViewResponse<>(category, "Name can't have this length!");
            } else if (categoryname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(category, "Name can't contain numbers!");
            }
            for (char c : categoryname.toCharArray()) {
                if (isSpecialChar(c)) {
                    return new StandardViewResponse<>(category, "Name can't contain special characters!");
                }
            }
            if(!isUniqueCategory(categoryname)) {
                return new StandardViewResponse<>(category, "There already exists a category with this name");
            }
            category.setCategoryName(categoryname);
            System.out.println("Category was successfully edited");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(category, e.getMessage());
        }
        return new StandardViewResponse<>(category, "");
    }
}
