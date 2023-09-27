package jr.acens.api.domain.user.DTO;

import jr.acens.api.domain.user.User;

public record UserReturnDTO(Long id,
                            String login,
                            String name) {

    public UserReturnDTO(User user) {
        this(user.getId(), user.getLogin(), user.getName());
    }
}
