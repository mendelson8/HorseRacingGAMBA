package horses.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggedUserLog implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoggedUserLog.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        logger.info("User {} has logged in successfully", authentication.getName());

        // Set the response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write JSON response
        response.getWriter().write("{\"status\": \"success\", \"message\": \"Login successful\"}");
        response.getWriter().flush();
    }
}