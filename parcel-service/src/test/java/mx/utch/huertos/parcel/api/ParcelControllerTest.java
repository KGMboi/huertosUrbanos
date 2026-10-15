package mx.utch.huertos.parcel.api;

import mx.utch.huertos.parcel.error.ApiExceptionHandler;
import mx.utch.huertos.parcel.service.ParcelService;
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

@WebMvcTest(ParcelController.class)
@Import(ApiExceptionHandler.class)
class ParcelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelService service;

    @Test
    void invalidParcelReturnsStructuredValidationErrors() throws Exception {
        mockMvc.perform(post("/api/v1/parcels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "location": { "latitude": 100, "longitude": -106 },
                                  "soilType": "LOAMY",
                                  "ownerOrLead": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed."))
                .andExpect(jsonPath("$.fieldErrors.name").exists())
                .andExpect(jsonPath("$.fieldErrors.ownerOrLead").exists())
                .andExpect(jsonPath("$.fieldErrors['location.latitude']").exists());
        verifyNoInteractions(service);
    }

    @Test
    void invalidPathUuidReturnsCustomClientError() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(
                        "/api/v1/parcels/not-a-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("A path or query parameter has an invalid value."));
        verifyNoInteractions(service);
    }
}
