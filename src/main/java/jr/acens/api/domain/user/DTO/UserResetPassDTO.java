package jr.acens.api.domain.user.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserResetPassDTO(
        @NotNull
        String login,
        @NotNull
        String password,
        @NotNull
        String tokenEmail
) {
}