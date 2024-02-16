package servrvlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import security.AuthToken;
import security.ValidationResult;
import service.AuthService;
import java.io.IOException;

@WebServlet("/validate")
public class ValidateTokenServlet extends HttpServlet {
    private final AuthService authService = AuthService.getINSTANCE();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AuthToken authToken = mapper.readValue(req.getReader(), AuthToken.class);
            boolean isValidToken = authService.isValidateToken(authToken.getAuthToken());

            ValidationResult validationResult = new ValidationResult(isValidToken);
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(validationResult));
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid request format");
        }
    }
}
