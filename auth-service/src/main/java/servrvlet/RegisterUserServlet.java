package servrvlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.RegistrationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import security.RegistrationResponse;
import service.AuthService;
import java.io.IOException;

@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet {
    private final AuthService authService = AuthService.getINSTANCE();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = mapper.readValue(req.getReader(), User.class);

            int userId = authService.registerUser(
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getLastName(),
                    user.getShippingAddress(),
                    user.getAge(),
                    user.getUserRole()
            );

            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(new RegistrationResponse(userId, user.getEmail(), user.getUserRole())));
        } catch (RegistrationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Registration failed: " + e.getMessage());
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid request format");
        }
    }
}
