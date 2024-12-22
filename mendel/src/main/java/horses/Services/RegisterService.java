package horses.Services;

import horses.Dtos.RegisterDto;
import horses.Repositories.RegisterInterface;
import horses.databases.RegisterInfo;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final RegisterInterface registerInterface;

    public RegisterService(RegisterInterface registerInterface) {
        this.registerInterface = registerInterface;
    }
    public RegisterDto createAccount(RegisterDto dto) {
        RegisterInfo info = RegisterInfo.builder()
                .login(dto.username())
                .password(dto.password())
                .build();
        registerInterface.save(info);
        return dto;
    }
}
