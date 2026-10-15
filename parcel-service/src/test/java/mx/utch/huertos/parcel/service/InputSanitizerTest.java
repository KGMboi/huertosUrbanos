package mx.utch.huertos.parcel.service;

import mx.utch.huertos.parcel.error.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputSanitizerTest {
    @Test
    void trimsAndCollapsesWhitespace() {
        assertEquals("Huerto Norte", InputSanitizer.cleanText("  Huerto   Norte  ", "name"));
    }

    @Test
    void rejectsMarkup() {
        assertThrows(InvalidInputException.class,
                () -> InputSanitizer.cleanText("<script>alert(1)</script>", "name"));
    }
}
