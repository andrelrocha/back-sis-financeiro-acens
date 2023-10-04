package jr.acens.api.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);

    @Query("""
            SELECT u FROM User u WHERE u.login = :login
            """)
    User findByLoginToHandle(String login);

    boolean existsByLogin(String login);

    @Query("""
            SELECT u FROM User u WHERE u.id = :id
            """)
    User findByIdToHandle(Long id);
}
