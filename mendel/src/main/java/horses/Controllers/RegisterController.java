package horses.Controllers;

import horses.Dtos.RegisterDto;
import horses.Services.RegisterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    @PostMapping("/register")
    public RegisterDto register(@RequestBody RegisterDto registerDto){
        registerService.createAccount(registerDto);
        return registerDto;
    }
}