package filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/products/*")
public class AuthorizationFilter implements Filter {
    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = new AuthService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String token = httpRequest.getHeader("Authorization");

        if (token != null && authService.isTokenValid(token)) {

            if (isAdminUser(token) || !httpRequest.getMethod().equals("POST")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("Permission denied for POST requests");
            }
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized access");
        }
    }

    private boolean isAdminUser(String token) {
        return token.contains("admin");
    }
}
