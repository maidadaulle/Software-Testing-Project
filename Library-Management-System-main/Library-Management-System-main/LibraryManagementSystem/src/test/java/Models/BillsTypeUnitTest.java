package Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BillsTypeUnitTest {

    @Test
    void testEnumValues() {
        BillsType[] values = BillsType.values();
        assertEquals(3, values.length, "BillsType should contain exactly 3 values");

        assertEquals(BillsType.Sold, values[0], "First enum value should be 'Sold'");
        assertEquals(BillsType.Purchased, values[1], "Second enum value should be 'Purchased'");
        assertEquals(BillsType.Bought, values[2], "Third enum value should be 'Bought'");
    }

    @Test
    void testEnumValueOf() {
        assertEquals(BillsType.Sold, BillsType.valueOf("Sold"), "Enum value 'Sold' should match");
        assertEquals(BillsType.Purchased, BillsType.valueOf("Purchased"), "Enum value 'Purchased' should match");
        assertEquals(BillsType.Bought, BillsType.valueOf("Bought"), "Enum value 'Bought' should match");
    }

    @Test
    void testEnumSize() {
        assertEquals(3, BillsType.values().length, "BillsType should have exactly 3 constants");
    }

}
