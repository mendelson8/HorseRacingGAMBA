package horses.Controllers;

import horses.Dtos.RegisterDto;
import horses.Services.RegisterService;
import horses.configurations.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class RegisterController {
    private final RegisterService registerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    @GetMapping("/user")
    public String user() {
        return "Hello, User!";
    }

    @PostMapping("/register")
    public RegisterDto register(@RequestBody RegisterDto registerDto){
        registerService.create(registerDto);
        return registerDto;
    }


    @PostMapping("/perform_login")
    public ResponseEntity<Object> login(@RequestBody RegisterDto registerDto, HttpServletRequest request, HttpServletResponse response) {
    System.out.print("registerDto: " + registerDto);
    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDto.username(), registerDto.password())
        );

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = JwtUtil.generateToken(registerDto.username());

        // Add the token as a cookie in the response
        Cookie cookie = new Cookie("AUTH-TOKEN", token);
        cookie.setHttpOnly(false);
        cookie.setSecure(false); // Use true if using HTTPS
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        System.out.print("Login Successfully");
        return ResponseEntity.ok().body(new LoginResponse("Login Successfully"));
    } catch (AuthenticationException e) {
        System.out.print("Login Failed: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("Login Failed: " + e.getMessage()));
    }
}
}
