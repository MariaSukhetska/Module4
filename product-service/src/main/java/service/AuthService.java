package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import util.ExternalService;
import util.ValidationResult;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    @SneakyThrows
    public boolean isTokenValid(String token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ExternalService.AUTH.getUrl() + "/validate"))
                .header("Authorization", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        ValidationResult result = mapper.readValue(jsonString, ValidationResult.class);
        return result.isValid();
    }
}
