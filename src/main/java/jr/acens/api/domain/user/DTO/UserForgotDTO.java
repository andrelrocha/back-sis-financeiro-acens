package jr.acens.api.domain.user.DTO;

import java.time.LocalDateTime;

public record UserForgotDTO(
        String tokenEmail,
        LocalDateTime tokenExpiration)
{ }
