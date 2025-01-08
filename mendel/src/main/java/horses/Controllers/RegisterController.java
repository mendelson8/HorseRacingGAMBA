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
}