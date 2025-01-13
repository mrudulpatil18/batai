package msp.batai.batai.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import msp.batai.batai.Model.User;

public interface UserRepository extends CrudRepository<User, Long>{
    
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);
}
