package jr.acens.api.domain.user.UseCase;

import jr.acens.api.domain.user.DTO.UserReturnDTO;
import jr.acens.api.domain.user.User;
import jr.acens.api.domain.user.UserRepository;
import jr.acens.api.infra.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import jr.acens.api.domain.user.DTO.UserDTO;

@Component
public class CreateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserReturnDTO createUser(UserDTO data) {
        boolean userExists = userRepository.existsByLogin(data.login());

        if (userExists) {
            throw new ValidationException("Email ou CPF informado já existe no banco de dados!\n" +
                    "Impossível concluir o processo de criação de conta.\n");
        }

        var newUser = new User(data);

        var encodedPassword = bCryptPasswordEncoder.encode(data.password());
        newUser.setPassword(encodedPassword);

        var userOnDb = userRepository.save(newUser);

        return new UserReturnDTO(userOnDb);
    }
}
