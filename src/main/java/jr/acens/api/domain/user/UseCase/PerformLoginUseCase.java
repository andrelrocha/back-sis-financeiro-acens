package jr.acens.api.domain.user.UseCase;

import jr.acens.api.domain.user.DTO.UserLoginDTO;
import jr.acens.api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import jr.acens.api.infra.security.TokenJwtDto;
import jr.acens.api.infra.security.TokenService;

@Component
public class PerformLoginUseCase {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    TokenService tokenService;

    public TokenJwtDto performLogin(UserLoginDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var authentication = manager.authenticate(authenticationToken);
        var userAuthenticated = (User) authentication.getPrincipal();

        var tokenJwt = tokenService.generateJwtToken(userAuthenticated);

        return new TokenJwtDto(tokenJwt);
    }
}
