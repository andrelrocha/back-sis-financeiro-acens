package jr.acens.api.service;

import jr.acens.api.domain.user.DTO.*;
import jr.acens.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserLoginDTO data);
    String forgotPassword(UserReturnLoginDTO data);
    String resetPassword(UserResetPassDTO data);


    UserReturnDTO createUser(UserDTO data);
    void deleteUser(Long id);

}
