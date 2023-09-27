package jr.acens.api.domain.user.UseCase;

import jr.acens.api.domain.user.DTO.UserResetPassDTO;
import jr.acens.api.domain.user.UserRepository;
import jr.acens.api.infra.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResetPasswordUseCase {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void resetPassword(UserResetPassDTO data) {
        try {
            isValidPassword(data.password());

            var userExists = repository.existsByLogin(data.login());

            if (!userExists) {
                throw new ValidationException("Não foi encontrado nenhum registro do login informado no banco de dados");
            }

            var user = repository.findByLoginToHandle(data.login());
            var tokenEmail = user.getTokenEmail();

            if (tokenEmail == null) {
                throw new ValidationException("Não há nenhum token para mudança de senha associado a sua conta\n" +
                        "Por favor, inicie o processo de mudança de senha em /users/forgot_password");
            }

            var tokenExpiration = user.getTokenExpiration();
            var now = LocalDateTime.now();

            var tokenIsValid = tokenEmail.equals(data.tokenEmail()) && now.isBefore(tokenExpiration);

            if (tokenIsValid) {
                String encodedPassword = bCryptPasswordEncoder.encode(data.password());
                user.setPassword(encodedPassword);
                user.setTokenEmail(null);
            } else {
                throw new ValidationException("Token de mudança de senha inválido");
            }
        }
        catch (Exception e) {
            throw new ValidationException("Algo aconteceu no processo de mudança de senha: " + e.getMessage());
        }
    }

    private void isValidPassword(String password) {
        if (password.length() < 8) {
            throw new ValidationException("A nova senha deve ter pelo menos 8 caracteres");
        }

        var containsUppercase = false;
        var containsDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUppercase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            }

            if (containsUppercase && containsDigit) {
                break;
            }
        }

        if (!containsUppercase || !containsDigit) {
            throw new ValidationException("A nova senha deve conter pelo menos uma letra maiúscula e um número");
        }
    }
}