package jr.acens.api.service.impl;

import jakarta.transaction.Transactional;
import jr.acens.api.domain.user.DTO.*;
import jr.acens.api.domain.user.UseCase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jr.acens.api.infra.security.TokenJwtDto;
import jr.acens.api.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private CreateAdminUseCase createAdminUseCase;
    @Autowired
    private DeleteUserUseCase deleteUserUseCase;
    @Autowired
    private ListUserByIdUseCase listUserByIdUseCase;
    @Autowired
    private ListAllUserUseCase listAllUserUseCase;

    @Autowired
    private ForgotPasswordUseCase forgotPasswordUseCase;
    @Autowired
    private PerformLoginUseCase performLoginUseCase;
    @Autowired
    private ResetPasswordUseCase resetPasswordUseCase;

    @Override
    public TokenJwtDto performLogin(UserLoginDTO data) {
        var tokenJwt = performLoginUseCase.performLogin(data);
        return tokenJwt;
    }

    @Override
    public String forgotPassword(UserReturnLoginDTO data) {
        forgotPasswordUseCase.forgotPassword(data);
        return "Email successfully sent!";
    }

    @Override
    public String resetPassword(UserResetPassDTO data) {
        resetPasswordUseCase.resetPassword(data);
        return "Password successfully updated!";
    }

    @Override
    public UserReturnDTO createUser(UserDTO data) {
        var user = createUserUseCase.createUser(data);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        deleteUserUseCase.deleteUser(id);
    }

    @Override
    public UserReturnDTO listUserById(Long id) {
        var user = listUserByIdUseCase.listUserById(id);
        return user;
    }

    @Override
    public Page<UserReturnDTO> listAllUsers(Pageable pageable) {
        var page = listAllUserUseCase.listAllUsers(pageable);
        return page;
    }

    @Override
    public UserReturnDTO createAdmin(UserDTO data) {
        var admin = createAdminUseCase.createAdmin(data);
        return admin;
    }
}
