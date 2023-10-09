package jr.acens.api.domain.user.UseCase;

import jr.acens.api.domain.user.DTO.UserReturnDTO;
import jr.acens.api.domain.user.UserRepository;
import jr.acens.api.infra.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListUserByIdUseCase {
    @Autowired
    private UserRepository repository;

    public UserReturnDTO listUserById(Long id) {
        var user = repository.findByIdToHandle(id);

        if (user == null) {
            throw new ValidationException("Não foram encontrados registros de usuário para o id fornecido.");
        }

        return new UserReturnDTO(user);
    }
}