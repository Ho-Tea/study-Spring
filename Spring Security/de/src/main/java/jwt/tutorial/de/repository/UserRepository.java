package jwt.tutorial.de.repository;

import jwt.tutorial.de.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    // @EntityGraph는 Data JPA에서 fetch 조인을 어노테이션으로 사용할 수 있도록 만들어 준 기능입니다.
    Optional<User> findOneWithAuthoritiesByUserName(String username);
}
