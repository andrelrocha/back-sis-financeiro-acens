package jr.acens.api.infra.security;

import jr.acens.api.domain.user.User;
import jr.acens.api.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserWithValidJwt {
    @Autowired
    private UserRepository userRepository;

    public User findUserAuthenticated(String login) {
        return (User) userRepository.findByLogin(login);
    }

}
