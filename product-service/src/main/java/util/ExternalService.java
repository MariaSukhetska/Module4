package util;

import lombok.Getter;

@Getter
public enum ExternalService {
    AUTH("http://localhost:8088/validate"),
    DISCOUNT("http://localhost:8087/discount?productName=");

    private final String url;
    ExternalService(String url) {
        this.url = url;
    }
}
