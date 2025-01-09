package horses.Controllers;

import horses.Dtos.RegisterDto;
import horses.Services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {
    private final RegisterService registerService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegisterController(RegisterService registerService, AuthenticationManager authenticationManager) {
        this.registerService = registerService;
        this.authenticationManager = authenticationManager;
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
        System.out.print("registerDto: " + registerDto);
        registerService.create(registerDto);
        System.out.print("Create Successfully");
        return registerDto;
    }

    @PostMapping("/perform_login")
    public String login(@RequestBody RegisterDto registerDto){
        System.out.print("registerDto: " + registerDto);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDto.username(), registerDto.password())
            );
            System.out.print("Login Successfully");
            return "Login Successfully";
        } catch (AuthenticationException e) {
            System.out.print("Login Failed: " + e.getMessage());
            return "Login Failed: " + e.getMessage();
        }
    }
}