package jr.acens.api.domain.user.UseCase;

import jr.acens.api.domain.user.DTO.UserDTO;
import jr.acens.api.domain.user.DTO.UserReturnDTO;
import jr.acens.api.domain.user.DTO.UserReturnLoginDTO;
import jr.acens.api.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAdminUseCase {
    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private UserRepository repository;

    public UserReturnDTO createAdmin(UserDTO data) {
        var newUser = createUserUseCase.createUser(data);

        var admin = repository.findByIdToHandle(newUser.id());

        admin.setAdmin();

        return new UserReturnDTO(admin);
    }
}
