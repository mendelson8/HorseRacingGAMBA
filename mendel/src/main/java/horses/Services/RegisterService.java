package horses.Services;

import horses.Dtos.RegisterDto;
import horses.Repositories.RegisterInterface;
import horses.databases.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RegisterService implements UserDetailsService {

    @Autowired
    private RegisterInterface registerInterface;

    public String create(RegisterDto dto) {
        User info = User.builder()
                .username(dto.username())
                .password(new BCryptPasswordEncoder().encode(dto.password()))
                .authorities("USER")
                .points(0)
                .build();
        registerInterface.save(info);
        return "Create Successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieves user details by username from the database
        return registerInterface.findByUsername(username);
    }
}
