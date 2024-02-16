package security;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.UserRole;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private int userId;
    private String email;
    private UserRole userRole;
}
