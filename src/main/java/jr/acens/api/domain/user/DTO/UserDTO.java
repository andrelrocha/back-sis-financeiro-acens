package jr.acens.api.domain.user.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jr.acens.api.domain.user.UserProfile;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record UserDTO(
        @NotNull
        String login,
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "A senha deve conter pelo menos uma letra maiúscula e um número")
        @NotNull
        String password,
        @NotNull String name,
        @NotNull Date birthday,
        @NotNull String cpf,
        @NotNull UserProfile profile,
        String phone,
        String professionalId
) {  }
