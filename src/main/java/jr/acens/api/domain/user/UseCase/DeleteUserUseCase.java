package jr.acens.api.domain.user.UseCase;

import jr.acens.api.domain.user.UserRepository;
import jr.acens.api.infra.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserUseCase {
    @Autowired
    private UserRepository repository;

    public void deleteUser(Long id) {
        var user = repository.findByIdToHandle(id);

        if (user == null) {
            throw new ValidationException("Não foram encontrados registros de usuário para o id fornecido.");
        }

        repository.deleteById(id);
    }
}
