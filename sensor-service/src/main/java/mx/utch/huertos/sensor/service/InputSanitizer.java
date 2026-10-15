package mx.utch.huertos.sensor.service;

import mx.utch.huertos.sensor.error.InvalidInputException;

public final class InputSanitizer {
    private InputSanitizer() {
    }

    public static String cleanText(String value, String fieldName) {
        if (value == null) {
            throw new InvalidInputException(fieldName + " is required.");
        }
        String normalized = value.strip().replaceAll("\\s+", " ");
        if (normalized.isEmpty() || normalized.chars().anyMatch(Character::isISOControl)
                || normalized.indexOf('<') >= 0 || normalized.indexOf('>') >= 0) {
            throw new InvalidInputException(fieldName + " contains unsupported characters.");
        }
        return normalized;
    }
}
