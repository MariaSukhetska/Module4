package service;

import lombok.*;
import model.User;
import model.UserRole;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthService {
    @Getter
    private static final AuthService INSTANCE = new AuthService();
    private static final Integer COUNTER = 0;
    private static final int VALID_TOKEN_LENGTH = 64;

    private final Map<Integer, User> usersDatabase = new HashMap<>();
    private final Map<String, String> tokenDatabase = new HashMap<>();




    public int registerUser(String email, String password,
                            String name, String lastName,
                            String shippingAddress, int age, UserRole userRole) {
        int id = COUNTER + 1;
        if (email.toLowerCase().contains("admin")) {
            userRole = UserRole.ROLE_ADMIN;
        } else if (email.toLowerCase().contains("manager")) {
            userRole = UserRole.ROLE_MANAGER;
        } else {
            userRole = UserRole.ROLE_USER;
        }
        User user = new User(name, lastName, age, shippingAddress, email, password, userRole);
        usersDatabase.put(id, user);
        tokenDatabase.put(user.getEmail(), generateToken(id));
        return id;
    }

    public boolean isAuthenticateUser(String email, String password) {
        for (Map.Entry<Integer, User> entry : usersDatabase.entrySet()) {
            User user = entry.getValue();
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                tokenDatabase.put(user.getEmail(), generateToken(entry.getKey()));
                return true;
            }
        }
        return false;
    }

    public String generateToken(Integer id) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[VALID_TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public boolean isValidateToken(String token) {
        return tokenDatabase.containsValue(token);
    }

    public String getTokenFromEmail(String email) {
        return tokenDatabase.get(email);
    }
}
