package mx.utch.huertos.sensor.service;

import mx.utch.huertos.sensor.error.DependencyUnavailableException;
import mx.utch.huertos.sensor.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
public class ParcelClient {
    private final RestClient restClient;

    public ParcelClient(RestClient.Builder builder, @Value("${parcel.service.url}") String parcelServiceUrl) {
        this.restClient = builder.baseUrl(parcelServiceUrl).build();
    }

    public void requireExists(UUID parcelId) {
        try {
            restClient.get()
                    .uri("/api/v1/parcels/{id}", parcelId)
                    .exchange((request, response) -> {
                        HttpStatusCode status = response.getStatusCode();
                        if (status.value() == 404) {
                            throw new ResourceNotFoundException("Parcel " + parcelId + " was not found.");
                        }
                        if (!status.is2xxSuccessful()) {
                            throw new DependencyUnavailableException(
                                    "Parcel service could not verify parcel " + parcelId + ".", null);
                        }
                        return null;
                    });
        } catch (ResourceNotFoundException | DependencyUnavailableException exception) {
            throw exception;
        } catch (RestClientException exception) {
            throw new DependencyUnavailableException("Parcel service is unavailable; try again later.", exception);
        }
    }
}
