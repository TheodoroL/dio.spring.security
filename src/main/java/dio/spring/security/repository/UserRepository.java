package dio.spring.security.repository;

import dio.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    //fazemos uma query que seleciona os username
    @Query("SELECT e FROM User e JOIN e.roles WHERE e.username= (:username)")
    public  User findByUsername(@Param("username") String username);
}
