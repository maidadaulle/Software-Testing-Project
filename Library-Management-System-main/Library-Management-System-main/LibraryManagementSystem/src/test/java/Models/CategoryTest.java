package Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        Category.setNoCategory(0);
        category = new Category("Fiction");
    }

    @Test
    void testCategoryConstructor() {
        assertNotNull(category, "Category object should not be null.");
        assertEquals("Fiction", category.getCategoryName(), "Category name should be initialized.");
        assertEquals(0, category.getID(), "The ID should start from 0.");
    }

    @Test
    void testGetCategoryName() {
        assertEquals("Fiction", category.getCategoryName(), "Category name should be returned.");
    }

    @Test
    void testSetCategoryName() {
        category.setCategoryName("Non-Fiction");
        assertEquals("Non-Fiction", category.getCategoryName(), "Category name should be updated.");
    }

    @Test
    void testGetID() {
        assertEquals(0, category.getID(), "Category ID should be 0 initially.");
    }

    @Test
    void testSetID() {
        category.setID(10);
        assertEquals(10, category.getID(), "Category ID should be updated.");
    }

    @Test
    void testStaticNoCategory() {
        Category.setNoCategory(5);
        Category newCategory = new Category("Science");
        assertEquals(5, newCategory.getID(), "The ID should be set to 5 when `setNoCategory(5)` is called.");
    }

    @Test
    void testToString() {
        assertEquals("Fiction", category.toString(), "The toString method should return the category name.");
    }

    @Test
    void testSetNoCategory() {
        Category.setNoCategory(3);
        Category newCategory = new Category("History");
        assertEquals(3, newCategory.getID(), "The ID of the new category should start from 3.");
    }
}
