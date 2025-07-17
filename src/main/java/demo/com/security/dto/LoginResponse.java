package demo.com.security.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
