package mx.utch.huertos.sensor.api;

import mx.utch.huertos.sensor.error.ApiExceptionHandler;
import mx.utch.huertos.sensor.service.SensorReadingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorReadingController.class)
@Import(ApiExceptionHandler.class)
class SensorReadingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorReadingService service;

    @Test
    void missingReadingFieldsReturnStructuredValidationErrors() throws Exception {
        mockMvc.perform(post("/api/v1/readings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "parcelId": "00000000-0000-0000-0000-000000000001",
                                  "type": "SOIL_MOISTURE",
                                  "value": 42.5,
                                  "unit": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed."))
                .andExpect(jsonPath("$.fieldErrors.unit").exists());
        verifyNoInteractions(service);
    }

    @Test
    void unknownMeasurementTypeReturnsCustomClientError() throws Exception {
        mockMvc.perform(post("/api/v1/readings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "parcelId": "00000000-0000-0000-0000-000000000001",
                                  "type": "UNKNOWN",
                                  "value": 42.5,
                                  "unit": "%"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("The request body is malformed or contains an unsupported value."));
        verifyNoInteractions(service);
    }
}
