package jr.acens.api.service;

import jr.acens.api.domain.user.DTO.*;
import jr.acens.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserLoginDTO data);
    UserReturnDTO createUser(UserDTO data);
    String forgotPassword(UserReturnLoginDTO data);
    String resetPassword(UserResetPassDTO data);
}
