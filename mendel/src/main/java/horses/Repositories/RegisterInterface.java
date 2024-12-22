package horses.Repositories;

import horses.Controllers.RegisterController;
import horses.databases.RegisterInfo;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegisterInterface extends JpaRepository<RegisterInfo, UUID> {
}
