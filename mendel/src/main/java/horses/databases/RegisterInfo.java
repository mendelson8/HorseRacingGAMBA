package horses.databases;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;


import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "horses", schema = "public")
public class RegisterInfo {
    @Id
    @Builder.Default
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id = UUID.randomUUID();

    @Size(max = 100)
    @NotNull
    @Column(name = "login", nullable = false, length = 100)
    private String login;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;
}
