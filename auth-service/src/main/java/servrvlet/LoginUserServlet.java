package servrvlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import security.AuthToken;
import service.AuthService;
import java.io.IOException;

@WebServlet("/login")
public class LoginUserServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = AuthService.getINSTANCE();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        boolean authenticateUser = authService.isAuthenticateUser(email, password);

        if (authenticateUser) {
            String authToken = authService.getTokenFromEmail(email);
            AuthToken token = new AuthToken(authToken);

            String json = mapper.writeValueAsString(token);

            resp.setContentType("application/json");
            resp.getWriter().write(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Invalid credentials");
        }
    }
}
