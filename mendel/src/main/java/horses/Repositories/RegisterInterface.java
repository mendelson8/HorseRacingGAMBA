package horses.Repositories;

import horses.Controllers.RegisterController;
import horses.databases.User;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface RegisterInterface extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
