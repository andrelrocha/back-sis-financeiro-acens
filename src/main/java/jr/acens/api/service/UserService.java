package jr.acens.api.service;

import jr.acens.api.domain.user.DTO.*;
import jr.acens.api.infra.security.TokenJwtDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    //SISTEMA DE LOGIN
    TokenJwtDto performLogin(UserLoginDTO data);
    String forgotPassword(UserReturnLoginDTO data);
    String resetPassword(UserResetPassDTO data);

    //CRUD
    UserReturnDTO createUser(UserDTO data);
    void deleteUser(Long id);
    UserReturnDTO listUserById(Long id);
    Page<UserReturnDTO> listAllUsers(Pageable pageable);


    //ADMIN
    UserReturnDTO createAdmin(UserDTO data);
}
