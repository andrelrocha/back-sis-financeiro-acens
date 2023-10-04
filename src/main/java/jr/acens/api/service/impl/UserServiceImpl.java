package jr.acens.api.service.impl;

import jakarta.transaction.Transactional;
import jr.acens.api.domain.user.DTO.*;
import jr.acens.api.domain.user.UseCase.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserReturnDTO createAdmin(UserDTO data) {
        var admin = createAdminUseCase.createAdmin(data);
        return admin;
    }
}
