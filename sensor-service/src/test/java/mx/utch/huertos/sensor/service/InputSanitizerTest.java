package mx.utch.huertos.sensor.service;

import mx.utch.huertos.sensor.error.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputSanitizerTest {
    @Test
    void trimsAndCollapsesWhitespace() {
        assertEquals("%", InputSanitizer.cleanText("  %  ", "unit"));
    }

    @Test
    void rejectsMarkup() {
        assertThrows(InvalidInputException.class, () -> InputSanitizer.cleanText("<b>%</b>", "unit"));
    }
}
